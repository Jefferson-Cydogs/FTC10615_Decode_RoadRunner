package org.firstinspires.ftc.teamcode.cydogs.configs;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@Config
@TeleOp
public class FlywheelPIDFRecoveryLogger extends LinearOpMode {

    public static double P = 7.5;
    public static double I = 0.0;
    public static double D = 0.0;
    public static double F = 12.4;

    public static double targetVelocity = 1384; // ticks per second
    public static int shotIntervalMs = 3000;     // time between shots
    public static double velocityDrop = 400;     // simulated drop during shot

    @Override
    public void runOpMode() {
        DcMotorEx flywheel = hardwareMap.get(DcMotorEx.class, "launcher");
        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FtcDashboard dashboard = FtcDashboard.getInstance();

        waitForStart();

        long lastShotTime = System.currentTimeMillis();
        boolean recovering = false;
        long recoveryStart = 0;
        long recoveryEnd = 0;

        while (opModeIsActive()) {
            // Apply PIDF coefficients
            PIDFCoefficients pidf = new PIDFCoefficients(P, I, D, F);
            flywheel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);

            // Set target velocity
            flywheel.setVelocity(targetVelocity);

            double actualVelocity = flywheel.getVelocity();
            long currentTime = System.currentTimeMillis();

            // Simulate a shot every few seconds
            if (currentTime - lastShotTime >= shotIntervalMs) {
                flywheel.setVelocity(targetVelocity - velocityDrop); // simulate dip
                recoveryStart = System.currentTimeMillis();
                recovering = true;
                lastShotTime = currentTime;
            }

            // Detect recovery
            if (recovering && actualVelocity >= targetVelocity - 10) {
                recoveryEnd = System.currentTimeMillis();
                long recoveryTime = recoveryEnd - recoveryStart;
                telemetry.addData("Recovery Time (ms)", recoveryTime);
                recovering = false;
            }

            // Send data to dashboard
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Target Velocity", targetVelocity);
            packet.put("Actual Velocity", actualVelocity);
            packet.put("P", P);
            packet.put("F", F);
            dashboard.sendTelemetryPacket(packet);

            // Driver Station telemetry
            telemetry.addData("Target Velocity", targetVelocity);
            telemetry.addData("Actual Velocity", actualVelocity);
            telemetry.addData("P", P);
            telemetry.addData("F", F);
            telemetry.update();
        }
    }
}
