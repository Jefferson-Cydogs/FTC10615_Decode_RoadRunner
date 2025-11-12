package org.firstinspires.ftc.teamcode.cydogs.configs;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@Config
@TeleOp
public class WheelsPIDFTuner extends LinearOpMode {

    public static double P = 0.0;
    public static double I = 0.0;
    public static double D = 0.0;
    public static double F = 13.0;

    public static double targetVelocity = 1000; // ticks per second

    private DcMotorEx frontLeft, frontRight, backLeft, backRight;

    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotorEx.class, "FrontLeftWheel");
        frontRight = hardwareMap.get(DcMotorEx.class, "FrontRightWheel");
        backLeft = hardwareMap.get(DcMotorEx.class, "BackLeftWheel");
        backRight = hardwareMap.get(DcMotorEx.class, "BackRightWheel");

        DcMotorEx[] motors = {frontLeft, frontRight, backLeft, backRight};

        for (DcMotorEx motor : motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

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
            packet.put("LF Velocity", frontLeft.getVelocity());
            packet.put("RF Velocity", frontRight.getVelocity());
            packet.put("LR Velocity", backLeft.getVelocity());
            packet.put("RR Velocity", backRight.getVelocity());
            dashboard.sendTelemetryPacket(packet);

            telemetry.addData("Target Velocity", targetVelocity);
            telemetry.addData("LF", frontLeft.getVelocity());
            telemetry.addData("RF", frontRight.getVelocity());
            telemetry.addData("LR", backLeft.getVelocity());
            telemetry.addData("RR", backRight.getVelocity());
            telemetry.addData("P", P);
            telemetry.addData("F", F);
            telemetry.update();
        }
    }
}
