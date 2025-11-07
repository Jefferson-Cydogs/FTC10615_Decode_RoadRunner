package org.firstinspires.ftc.teamcode.cydogs.learning;

import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class REVColorDistanceSensor extends LinearOpMode {

    private ColorSensor LeftIntakeSensor;
    private ColorSensor RightIntakeSensor;
    private ColorSensor LeftLaunchSensor;
    private ColorSensor RightLaunchSensor;

    /**
     * This OpMode tests the color and distance features of the REV sensor.
     */
    @Override
    public void runOpMode() {
        int gain;
        NormalizedRGBA LINormalizedColors;
        NormalizedRGBA RINormalizedColors;
        NormalizedRGBA LLNormalizedColors;
        NormalizedRGBA RLNormalizedColors;
        int LIColor;
        int RIColor;
        int LLColor;
        int RLColor;
        float LIHue;
        float RIHue;
        float LLHue;
        float RLHue;

        LeftIntakeSensor = hardwareMap.get(ColorSensor.class, "LeftIntakeSensor");
        RightIntakeSensor = hardwareMap.get(ColorSensor.class, "RightIntakeSensor");
        LeftLaunchSensor = hardwareMap.get(ColorSensor.class, "LeftLaunchSensor");
        RightLaunchSensor = hardwareMap.get(ColorSensor.class, "RightLaunchSensor");

        gain = 20;
        telemetry.addData("Color Distance Measurement", "Press start to continue...");
        telemetry.update();
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Display distance info.
                telemetry.addData("LeftIntake Dist to tgt (cm)", ((DistanceSensor) LeftIntakeSensor).getDistance(DistanceUnit.CM));
                telemetry.addData("RightIntake Dist to tgt (cm)", ((DistanceSensor) RightIntakeSensor).getDistance(DistanceUnit.CM));
                telemetry.addData("LeftLaunch Dist to tgt (cm)", ((DistanceSensor) LeftLaunchSensor).getDistance(DistanceUnit.CM));
                telemetry.addData("RightLaunch Dist to tgt (cm)", ((DistanceSensor) RightLaunchSensor).getDistance(DistanceUnit.CM));
                // Display reflected light.
                //telemetry.addData("Light detected", ((OpticalDistanceSensor) REVColorRangeSensor).getLightDetected());
                // Adjust the gain.
                if (gamepad1.yWasPressed()) {
                    gain += 2;
                } else if (gamepad1.aWasPressed() && gain >= 4) {
                    gain += -2;
                }
                ((NormalizedColorSensor) LeftIntakeSensor).setGain(gain);
                ((NormalizedColorSensor) RightIntakeSensor).setGain(gain);
                ((NormalizedColorSensor) LeftLaunchSensor).setGain(gain);
                ((NormalizedColorSensor) RightLaunchSensor).setGain(gain);
                telemetry.addData("LeftIntake Gain", ((NormalizedColorSensor) LeftIntakeSensor).getGain());
                telemetry.addData("RightIntake Gain", ((NormalizedColorSensor) RightIntakeSensor).getGain());
                telemetry.addData("LeftLaunch Gain", ((NormalizedColorSensor) LeftLaunchSensor).getGain());
                telemetry.addData("RightLaunch Gain", ((NormalizedColorSensor) RightLaunchSensor).getGain());
                // Read color from the sensors.
                LINormalizedColors = ((NormalizedColorSensor) LeftIntakeSensor).getNormalizedColors();
                RINormalizedColors = ((NormalizedColorSensor) RightIntakeSensor).getNormalizedColors();
                LLNormalizedColors = ((NormalizedColorSensor) LeftLaunchSensor).getNormalizedColors();
                RLNormalizedColors = ((NormalizedColorSensor) RightLaunchSensor).getNormalizedColors();
                //telemetry.addData("Red", Double.parseDouble(JavaUtil.formatNumber(normalizedColors.red, 3)));
                //telemetry.addData("Green", Double.parseDouble(JavaUtil.formatNumber(normalizedColors.green, 3)));
                //telemetry.addData("Blue", Double.parseDouble(JavaUtil.formatNumber(normalizedColors.blue, 3)));
                // Convert RGB values to Hue, Saturation, and Value.
                // See https://en.wikipedia.org/wiki/HSL_and_HSV for details on HSV color model.
                LIColor = LINormalizedColors.toColor();
                RIColor = RINormalizedColors.toColor();
                LLColor = LLNormalizedColors.toColor();
                RLColor = RLNormalizedColors.toColor();
                LIHue = JavaUtil.colorToHue(LIColor);
                RIHue = JavaUtil.colorToHue(RIColor);
                LLHue = JavaUtil.colorToHue(LLColor);
                RLHue = JavaUtil.colorToHue(RLColor);
                //saturation = JavaUtil.colorToSaturation(color);
                //value = JavaUtil.colorToValue(color);
                telemetry.addData("LeftIntake Hue", Double.parseDouble(JavaUtil.formatNumber(LIHue, 0)));
                telemetry.addData("RightIntake Hue", Double.parseDouble(JavaUtil.formatNumber(RIHue, 0)));
                telemetry.addData("LeftLaunch Hue", Double.parseDouble(JavaUtil.formatNumber(LLHue, 0)));
                telemetry.addData("RightLaunch Hue", Double.parseDouble(JavaUtil.formatNumber(RLHue, 0)));
                //telemetry.addData("Saturation", Double.parseDouble(JavaUtil.formatNumber(saturation, 3)));
                //telemetry.addData("Value", Double.parseDouble(JavaUtil.formatNumber(value, 3)));
                //telemetry.addData("Alpha", Double.parseDouble(JavaUtil.formatNumber(normalizedColors.alpha, 3)));
                // Show the color on the Robot Controller screen.
                JavaUtil.showColor(hardwareMap.appContext, LIColor);
                JavaUtil.showColor(hardwareMap.appContext, RIColor);
                JavaUtil.showColor(hardwareMap.appContext, LLColor);
                JavaUtil.showColor(hardwareMap.appContext, RLColor);
                // Use hue to determine if it's red, green, blue, etc..
                if (LIHue < 30) {
                    telemetry.addData("LefIntake Color", "Red");
                } else if (LIHue < 60) {
                    telemetry.addData("LefIntake Color", "Orange");
                } else if (LIHue < 90) {
                    telemetry.addData("LefIntake Color", "Yellow");
                } else if (LIHue < 150) {
                    telemetry.addData("LefIntake Color", "Green");
                } else if (LIHue < 225) {
                    telemetry.addData("LefIntake Color", "Blue");
                } else if (LIHue < 350) {
                    telemetry.addData("LefIntake Color", "purple");
                } else {
                    telemetry.addData("LefIntake Color", "Red");
                }
                if (RIHue < 30) {
                    telemetry.addData("RightIntake Color", "Red");
                } else if (RIHue < 60) {
                    telemetry.addData("RightIntake Color", "Orange");
                } else if (RIHue < 90) {
                    telemetry.addData("RightIntake Color", "Yellow");
                } else if (RIHue < 150) {
                    telemetry.addData("RightIntake Color", "Green");
                } else if (RIHue < 225) {
                    telemetry.addData("RightIntake Color", "Blue");
                } else if (RIHue < 350) {
                    telemetry.addData("RightIntake Color", "purple");
                } else {
                    telemetry.addData("RightIntake Color", "Red");
                }
                if (LLHue < 30) {
                    telemetry.addData("LeftLauncher Color", "Red");
                } else if (LLHue < 60) {
                    telemetry.addData("LeftLauncher Color", "Orange");
                } else if (LLHue < 90) {
                    telemetry.addData("LeftLauncher Color", "Yellow");
                } else if (LLHue < 150) {
                    telemetry.addData("LeftLauncher Color", "Green");
                } else if (LLHue < 225) {
                    telemetry.addData("LeftLauncher Color", "Blue");
                } else if (LLHue < 350) {
                    telemetry.addData("LeftLauncher Color", "purple");
                } else {
                    telemetry.addData("LeftLauncher Color", "Red");
                }
                if (RLHue < 30) {
                    telemetry.addData("RightLauncher Color", "Red");
                } else if (RLHue < 60) {
                    telemetry.addData("RightLauncher Color", "Orange");
                } else if (RLHue < 90) {
                    telemetry.addData("RightLauncher Color", "Yellow");
                } else if (RLHue < 150) {
                    telemetry.addData("RightLauncher Color", "Green");
                } else if (RLHue < 225) {
                    telemetry.addData("RightLauncher Color", "Blue");
                } else if (RLHue < 350) {
                    telemetry.addData("RightLauncher Color", "purple");
                } else {
                    telemetry.addData("RightLauncher Color", "Red");
                }
                // Check to see if it might be black or white.
                //if (saturation < 0.2) {
                //    telemetry.addData("Check Sat", "Is surface white?");
                //}
                telemetry.update();
                //if (value < 0.16) {
                //    telemetry.addData("Check Val", "Is surface black?");
                //}
            }
            // Show white on the Robot Controller screen.
            //JavaUtil.showColor(hardwareMap.appContext, Color.parseColor("white"));
        }
    }
}
