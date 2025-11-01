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
@TeleOp(name = "Wheel PIDF Tuner", group = "Tuning")
public class WheelPIDFTuner extends LinearOpMode {

    public static double P = 7.0;
    public static double I = 0.0;
    public static double D = 0.0;
    public static double F = 11.754;

    public static double targetVelocity = 1000; // ticks per second

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

        while (opModeIsActive()) {
            PIDFCoefficients pidf = new PIDFCoefficients(P, I, D, F);
            for (DcMotorEx motor : motors) {
                motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);
                motor.setVelocity(targetVelocity);
            }

            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Target Velocity", targetVelocity);
            packet.put("LF Velocity", leftFront.getVelocity());
            packet.put("RF Velocity", rightFront.getVelocity());
            packet.put("LR Velocity", leftRear.getVelocity());
            packet.put("RR Velocity", rightRear.getVelocity());
            dashboard.sendTelemetryPacket(packet);

            telemetry.addData("Target Velocity", targetVelocity);
            telemetry.addData("LF", leftFront.getVelocity());
            telemetry.addData("RF", rightFront.getVelocity());
            telemetry.addData("LR", leftRear.getVelocity());
            telemetry.addData("RR", rightRear.getVelocity());
            telemetry.addData("P", P);
            telemetry.addData("F", F);
            telemetry.update();
        }
    }
}
