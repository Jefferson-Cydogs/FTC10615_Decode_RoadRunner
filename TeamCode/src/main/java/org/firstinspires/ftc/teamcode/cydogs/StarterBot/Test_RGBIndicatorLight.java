package org.firstinspires.ftc.teamcode.cydogs.StarterBot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Test_RGBIndicatorLight")
public class Test_RGBIndicatorLight extends LinearOpMode {

    private Servo artifactIndicatorLight;

    /**
     * This code...
     */
    @Override
    public void runOpMode() {
        artifactIndicatorLight = hardwareMap.get(Servo.class, "artifactIndicatorLight");

        // Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                if (gamepad1.a) {
                    artifactIndicatorLight.setPosition(0.5);
                } else if (gamepad1.b) {
                    artifactIndicatorLight.setPosition(0.7);
                } else if (gamepad1.y) {
                    artifactIndicatorLight.setPosition(1);
                } else if (gamepad1.x) {
                    artifactIndicatorLight.setPosition(0);
                }
                telemetry.addData("Color number", artifactIndicatorLight.getPosition());
                telemetry.update();
            }
        }
    }
}
