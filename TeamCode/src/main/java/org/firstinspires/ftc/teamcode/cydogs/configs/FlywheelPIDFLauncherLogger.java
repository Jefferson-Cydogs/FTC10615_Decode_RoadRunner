package org.firstinspires.ftc.teamcode.cydogs.configs;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.CRServo;

import java.util.ArrayList;

@Config
@Disabled
@Autonomous
public class FlywheelPIDFLauncherLogger extends LinearOpMode {

    public static double P = 7.0;
    public static double I = 0.0;
    public static double D = 0.0;
    public static double F = 12.5;

    public static double targetVelocity = 1380; // ticks per second
    public static int shotIntervalMs = 3000;     // time between shots
    public static int totalShots = 8;            // number of launches
    public static double launchServoRest = 0.0;
    public static double launchServoFire = 1.0;
    public static int servoFireDurationMs = 200;

    DcMotorEx flywheel;
    CRServo leftLauncherServo;
    CRServo rightLauncherServo;

    @Override
    public void runOpMode() {
        flywheel = hardwareMap.get(DcMotorEx.class, "Launchers");
        leftLauncherServo = hardwareMap.get(CRServo.class, "LeftFeeder");
        rightLauncherServo = hardwareMap.get(CRServo.class, "RightFeeder");

        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        flywheel.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FtcDashboard dashboard = FtcDashboard.getInstance();

        rightLauncherServo.setDirection(CRServo.Direction.REVERSE);
        leftLauncherServo.setPower(launchServoRest);
        rightLauncherServo.setPower(launchServoRest);

        waitForStart();

        ArrayList<Long> recoveryTimes = new ArrayList<>();
        int shotsFired = 0;
        long lastShotTime = System.currentTimeMillis();
        boolean recovering = false;
        long recoveryStart = 0;

        while (opModeIsActive() && shotsFired < totalShots) {
            // Apply PIDF coefficients
            PIDFCoefficients pidf = new PIDFCoefficients(P, I, D, F);
            flywheel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);

            // Maintain target velocity
            flywheel.setVelocity(targetVelocity);
            double actualVelocity = flywheel.getVelocity();
            long currentTime = System.currentTimeMillis();

            // Fire shot at interval
            if (currentTime - lastShotTime >= shotIntervalMs) {
                leftLauncherServo.setPower(launchServoFire);
                rightLauncherServo.setPower(launchServoFire);
                sleep(servoFireDurationMs);
                leftLauncherServo.setPower(launchServoRest);
                rightLauncherServo.setPower(launchServoRest);

                recoveryStart = System.currentTimeMillis();
                recovering = true;
                lastShotTime = currentTime;
                shotsFired++;
            }

            // Detect recovery
            if (recovering && actualVelocity >= targetVelocity - 10) {
                long recoveryTime = System.currentTimeMillis() - recoveryStart;
                recoveryTimes.add(recoveryTime);
                recovering = false;
            }

            // Dashboard graph
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Target Velocity", targetVelocity);
            packet.put("Actual Velocity", actualVelocity);
            packet.put("Shots Fired", shotsFired);
            dashboard.sendTelemetryPacket(packet);

            // Driver Station telemetry
            telemetry.addData("Target Velocity", targetVelocity);
            telemetry.addData("Actual Velocity", actualVelocity);
            telemetry.addData("Shots Fired", shotsFired);
            telemetry.update();
        }

        // After all shots, calculate stats
        if (!recoveryTimes.isEmpty()) {
            long sum = 0;
            long min = Long.MAX_VALUE;
            long max = Long.MIN_VALUE;

            for (long time : recoveryTimes) {
                sum += time;
                if (time < min) min = time;
                if (time > max) max = time;
            }

            long avg = sum / recoveryTimes.size();

            telemetry.addLine("Recovery Time Stats (ms):");
            telemetry.addData("Min", min);
            telemetry.addData("Max", max);
            telemetry.addData("Avg", avg);
            telemetry.update();
            sleep(5000); // Pause to view results
        }
    }
}
