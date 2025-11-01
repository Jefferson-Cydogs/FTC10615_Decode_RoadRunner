package org.firstinspires.ftc.teamcode.cydogs.configs;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@Config
@TeleOp(name = "Wheel PIDF Joystick Logger", group = "Tuning")
public class WheelPIDFJoystickLogger extends LinearOpMode {

    public static double P = 7.0;
    public static double I = 0.0;
    public static double D = 0.0;
    public static double F = 11.754;

    public static double maxVelocity = ((312.0 / 60.0) * 537.7);// ticks/sec for 312 RPM motor
    public static double deadband = 0.05;     // joystick dead zone
    public static double recoveryThreshold = 50; // ticks/sec margin for recovery

    private DcMotorEx leftFront, rightFront, leftRear, rightRear;

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

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftRear.setDirection(DcMotor.Direction.REVERSE);

        FtcDashboard dashboard = FtcDashboard.getInstance();

        waitForStart();

        double previousTarget = 0;
        boolean recovering = false;
        long recoveryStart = 0;
        long recoveryEnd = 0;

        while (opModeIsActive()) {
            // Apply PIDF coefficients
            PIDFCoefficients pidf = new PIDFCoefficients(P, I, D, F);
            for (DcMotorEx motor : motors) {
                motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);
            }

            // Joystick control: left stick Y axis
            double input = -gamepad1.left_stick_y;
            double targetVelocity = Math.abs(input) > deadband ? input * maxVelocity : 0;

            // Detect sudden stop and start recovery timer
            if (!recovering && previousTarget > 100 && targetVelocity == 0) {
                recoveryStart = System.currentTimeMillis();
                recovering = true;
            }

            // Set velocity to all motors
            for (DcMotorEx motor : motors) {
                motor.setVelocity(targetVelocity);
            }

            // Check if all motors have recovered to 0
            if (recovering && allMotorsNearZero(motors)) {
                recoveryEnd = System.currentTimeMillis();
                long recoveryTime = recoveryEnd - recoveryStart;
                telemetry.addData("Recovery Time (ms)", recoveryTime);
                recovering = false;
            }

            // Dashboard telemetry
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Target Velocity", targetVelocity);
            packet.put("LF Velocity", leftFront.getVelocity());
            packet.put("RF Velocity", rightFront.getVelocity());
            packet.put("LR Velocity", leftRear.getVelocity());
            packet.put("RR Velocity", rightRear.getVelocity());
            dashboard.sendTelemetryPacket(packet);

            // Driver Station telemetry
            telemetry.addData("Target Velocity", targetVelocity);
            telemetry.addData("LF", leftFront.getVelocity());
            telemetry.addData("RF", rightFront.getVelocity());
            telemetry.addData("LR", leftRear.getVelocity());
            telemetry.addData("RR", rightRear.getVelocity());
            telemetry.addData("P", P);
            telemetry.addData("F", F);
            telemetry.update();

            previousTarget = targetVelocity;
        }
    }

    private boolean allMotorsNearZero(DcMotorEx[] motors) {
        for (DcMotorEx motor : motors) {
            if (Math.abs(motor.getVelocity()) > recoveryThreshold) {
                return false;
            }
        }
        return true;
    }
}
