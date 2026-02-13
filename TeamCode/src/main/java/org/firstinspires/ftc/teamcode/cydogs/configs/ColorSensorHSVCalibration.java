package org.firstinspires.ftc.teamcode.cydogs.configs;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

@TeleOp(name="Color Sensor HSV Calibration", group="Configs")
public class ColorSensorHSVCalibration extends LinearOpMode {

    RevColorSensorV3 colorSensor;

    // Calibration parameters
    private int gain = 2;          // adjustable in real time
    private float hueThreshold = 180; // example threshold for distinguishing red vs blue

    @Override
    public void runOpMode() {
        colorSensor = hardwareMap.get(RevColorSensorV3.class, "LeftLaunchSensor");
        colorSensor.setGain(gain);

        telemetry.addLine("Press PLAY to start HSV calibration");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Read normalized RGBA
            NormalizedRGBA colors = colorSensor.getNormalizedColors();

            // Convert to HSV
            float[] hsv = new float[3];
            android.graphics.Color.RGBToHSV(
                    (int)(colors.red * 255),
                    (int)(colors.green * 255),
                    (int)(colors.blue * 255),
                    hsv
            );

            // Show HSV values
            telemetry.addData("Hue", "%.1f", hsv[0]);
            telemetry.addData("Saturation", "%.2f", hsv[1]);
            telemetry.addData("Value", "%.2f", hsv[2]);

            // Show proximity (RawLightDetected)
            telemetry.addData("Proximity", "%.2f", colorSensor.getRawLightDetected());

            // Show current gain
            telemetry.addData("Gain", gain);

            // Example classification using hue threshold
            String detectedColor = (hsv[0] < hueThreshold) ? "Red-ish" : "Blue-ish";
            telemetry.addData("Detected", detectedColor);

            telemetry.update();

            // --- Real-time calibration controls ---
            if (gamepad1.dpadUpWasPressed()) {
                gain++;
                colorSensor.setGain(gain);
            }
            if (gamepad1.dpadDownWasPressed() && gain > 1) {
                gain--;
                colorSensor.setGain(gain);
            }
            if (gamepad1.dpadRightWasPressed()) {
                hueThreshold += 5;
            }
            if (gamepad1.dpadLeftWasPressed()) {
                hueThreshold -= 5;
            }

            sleep(150); // debounce
        }
    }
}
