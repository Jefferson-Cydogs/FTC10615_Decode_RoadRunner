package org.firstinspires.ftc.teamcode.cydogs.configs;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@Config
@Autonomous(name = "Scripted PIDF Drive Test", group = "Tuning")
public class ScriptedPIDFDriveTest extends LinearOpMode {

    public static double P = 7.0;
    public static double I = 0.0;
    public static double D = 0.0;
    public static double F = 12.5;

    public static double maxVelocity = ((double)312 / 60) * 537.7; // ticks/sec for 312 RPM motor
    public static int segmentDurationMs = 3000; // time per movement segment
    public static double recoveryThreshold = 50; // ticks/sec margin for recovery

    DcMotorEx leftFront, rightFront, leftRear, rightRear;

    @Override
    public void runOpMode() {
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFrontWheel");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFrontWheel");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftBackWheel");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightBackWheel");

        DcMotorEx[] motors = {leftFront, rightFront, leftRear, rightRear};

        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotor.Direction.REVERSE);

        //FtcDashboard dashboard = FtcDashboard.getInstance();

        waitForStart();

        // Apply PIDF coefficients
        PIDFCoefficients pidf = new PIDFCoefficients(P, I, D, F);
        for (DcMotorEx motor : motors) {
            motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);
        }

        // Run scripted pattern: forward → turn → stop
        runSegment("Forward", maxVelocity, maxVelocity);
        runSegment("Turn", maxVelocity, -maxVelocity);
        runSegment("Stop", 0, 0);

        telemetry.addLine("Scripted PIDF test complete.");
        telemetry.update();
        sleep(3000);
    }

    private void runSegment(String label, double leftVel, double rightVel) {
        long startTime = System.currentTimeMillis();
        boolean recovering = false;
        long recoveryStart = 0;
        long recoveryEnd = 0;

        FtcDashboard dashboard = FtcDashboard.getInstance();

        while (opModeIsActive() && System.currentTimeMillis() - startTime < segmentDurationMs) {
            leftFront.setVelocity(leftVel);
            leftRear.setVelocity(leftVel);
            rightFront.setVelocity(rightVel);
            rightRear.setVelocity(rightVel);

            double avgLeft = (leftFront.getVelocity() + leftRear.getVelocity()) / 2;
            double avgRight = (rightFront.getVelocity() + rightRear.getVelocity()) / 2;

            // Detect recovery after stop
            if (!recovering && leftVel == 0 && rightVel == 0 &&
                    (Math.abs(avgLeft) > recoveryThreshold || Math.abs(avgRight) > recoveryThreshold)) {
                recoveryStart = System.currentTimeMillis();
                recovering = true;
            }

            if (recovering && Math.abs(avgLeft) < recoveryThreshold && Math.abs(avgRight) < recoveryThreshold) {
                recoveryEnd = System.currentTimeMillis();
                long recoveryTime = recoveryEnd - recoveryStart;
                telemetry.addData("Recovery Time (" + label + ")", recoveryTime + " ms");
                recovering = false;
            }

            // Dashboard telemetry
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Segment", label);
            packet.put("Left Velocity", avgLeft);
            packet.put("Right Velocity", avgRight);
            dashboard.sendTelemetryPacket(packet);

            // Driver Station telemetry
            telemetry.addData("Segment", label);
            telemetry.addData("Left Velocity", avgLeft);
            telemetry.addData("Right Velocity", avgRight);
            telemetry.update();
        }
    }
}
