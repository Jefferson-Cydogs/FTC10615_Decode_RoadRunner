package org.firstinspires.ftc.teamcode.cydogs.configs;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@Autonomous(name = "PIDF Sweep Tester", group = "Tuning")
public class PIDFSweepTester extends LinearOpMode {

    // Sweep ranges
    public static double[] P_VALUES = {10, 15, 20, 25, 30};
    public static double[] F_VALUES = {10, 12, 14, 16, 18};

    public static double I = 0.0;
    public static double D = 0.0;

    public static double maxVelocity = ((double)312 / 60) * 537.7; // ticks/sec
    public static int segmentDurationMs = 3000;
    public static double recoveryThreshold = 50;

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

        FtcDashboard dashboard = FtcDashboard.getInstance();

        waitForStart();

        for (double p : P_VALUES) {
            for (double f : F_VALUES) {
                PIDFCoefficients pidf = new PIDFCoefficients(p, I, D, f);
                for (DcMotorEx motor : motors) {
                    motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);
                }

                telemetry.addLine("Testing P=" + p + " F=" + f);
                telemetry.update();

                runTestSegment(p, f, dashboard);
                sleep(1000); // pause between tests
            }
        }

        telemetry.addLine("PIDF sweep complete.");
        telemetry.update();
        sleep(3000);
    }

    private void runTestSegment(double p, double f, FtcDashboard dashboard) {
        long startTime = System.currentTimeMillis();
        boolean recovering = false;
        long recoveryStart = 0;
        long recoveryEnd = 0;
        long recoveryTime = 0;
        double velocitySum = 0;
        int samples = 0;

        // Start with forward motion
        double leftVel = maxVelocity;
        double rightVel = maxVelocity;

        while (opModeIsActive() && System.currentTimeMillis() - startTime < segmentDurationMs) {
            leftFront.setVelocity(leftVel);
            leftRear.setVelocity(leftVel);
            rightFront.setVelocity(rightVel);
            rightRear.setVelocity(rightVel);

            double avgLeft = (leftFront.getVelocity() + leftRear.getVelocity()) / 2;
            double avgRight = (rightFront.getVelocity() + rightRear.getVelocity()) / 2;
            double avgVelocity = (avgLeft + avgRight) / 2;
            velocitySum += avgVelocity;
            samples++;

            TelemetryPacket packet = new TelemetryPacket();
            packet.put("P", p);
            packet.put("F", f);
            packet.put("Avg Velocity", avgVelocity);
            dashboard.sendTelemetryPacket(packet);

            telemetry.addData("P", p);
            telemetry.addData("F", f);
            telemetry.addData("Avg Velocity", avgVelocity);
            telemetry.update();
        }

        // Stop motors
        for (DcMotorEx motor : new DcMotorEx[]{leftFront, leftRear, rightFront, rightRear}) {
            motor.setVelocity(0);
        }

        recoveryStart = System.currentTimeMillis();
        while (opModeIsActive()) {
            double avgLeft = (leftFront.getVelocity() + leftRear.getVelocity()) / 2;
            double avgRight = (rightFront.getVelocity() + rightRear.getVelocity()) / 2;

            if (Math.abs(avgLeft) < recoveryThreshold && Math.abs(avgRight) < recoveryThreshold) {
                recoveryEnd = System.currentTimeMillis();
                recoveryTime = recoveryEnd - recoveryStart;
                break;
            }
        }

        double avgVelocity = velocitySum / samples;
        telemetry.addLine("Results for P=" + p + " F=" + f);
        telemetry.addData("Avg Velocity", avgVelocity);
        telemetry.addData("Recovery Time (ms)", recoveryTime);
        telemetry.update();
        sleep(1500);
    }
}
