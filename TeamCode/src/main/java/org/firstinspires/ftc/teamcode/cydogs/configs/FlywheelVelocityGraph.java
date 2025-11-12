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
public class FlywheelVelocityGraph extends LinearOpMode {

    public static double P = 0.0;
    public static double I = 0.0;
    public static double D = 0.0;
    public static double F = 13.0;

    public static double targetVelocity = 1380; // ticks per second

    @Override
    public void runOpMode() {
        DcMotorEx flywheel = hardwareMap.get(DcMotorEx.class, "RightLauncher");
        flywheel.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        flywheel.setDirection(DcMotorEx.Direction.REVERSE);
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FtcDashboard dashboard = FtcDashboard.getInstance();

        waitForStart();

        while (opModeIsActive()) {
            // Update PIDF coefficients live
            PIDFCoefficients pidf = new PIDFCoefficients(P, I, D, F);
            flywheel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);

            // Set target velocity
            flywheel.setVelocity(targetVelocity);

            // Create telemetry packet for graphing
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Target Velocity", targetVelocity);
            packet.put("Actual Velocity", flywheel.getVelocity());

            dashboard.sendTelemetryPacket(packet);

            // Also show on Driver Station
            telemetry.addData("Target Velocity", targetVelocity);
            telemetry.addData("Actual Velocity", flywheel.getVelocity());
            telemetry.update();
        }
    }
}
