package org.firstinspires.ftc.teamcode.cydogs.configs;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp(name = "Gamepad Dashboard Visualizer", group = "Debug")
public class GamepadDashboardVisualizer extends LinearOpMode {

    @Override
    public void runOpMode() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry.addLine("Start to visualize gamepad input...");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();

            // Face buttons
            packet.put("A (×)", gamepad1.a);
            packet.put("B (○)", gamepad1.b);
            packet.put("X (□)", gamepad1.x);
            packet.put("Y (△)", gamepad1.y);

            // D-pad
            packet.put("D-pad Up", gamepad1.dpad_up);
            packet.put("D-pad Down", gamepad1.dpad_down);
            packet.put("D-pad Left", gamepad1.dpad_left);
            packet.put("D-pad Right", gamepad1.dpad_right);

            // Triggers and bumpers
            packet.put("Left Trigger (L2)", gamepad1.left_trigger);
            packet.put("Right Trigger (R2)", gamepad1.right_trigger);
            packet.put("Left Bumper (L1)", gamepad1.left_bumper);
            packet.put("Right Bumper (R1)", gamepad1.right_bumper);

            // Joysticks
            packet.put("Left Stick X", gamepad1.left_stick_x);
            packet.put("Left Stick Y", gamepad1.left_stick_y);
            packet.put("Right Stick X", gamepad1.right_stick_x);
            packet.put("Right Stick Y", gamepad1.right_stick_y);
            packet.put("Left Stick Button (L3)", gamepad1.left_stick_button);
            packet.put("Right Stick Button (R3)", gamepad1.right_stick_button);

            // Start/Back
            packet.put("Start", gamepad1.start);
            packet.put("Back (Share)", gamepad1.back);

            dashboard.sendTelemetryPacket(packet);
        }
    }
}
