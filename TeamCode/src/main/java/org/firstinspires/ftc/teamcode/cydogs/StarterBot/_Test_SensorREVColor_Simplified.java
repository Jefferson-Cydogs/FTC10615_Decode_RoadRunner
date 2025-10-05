package org.firstinspires.ftc.teamcode.cydogs.StarterBot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "_Test_SensorREVColor_Simplified")
public class _Test_SensorREVColor_Simplified extends LinearOpMode {

    private ColorSensor artifactColorSensor_REV_ColorRangeSensor;

    /**
     * This OpMode demonstrates the color and distance features of the REV sensor.
     */
    @Override
    public void runOpMode() {
        NormalizedRGBA normalizedColors;
        int color;
        float hue;

        artifactColorSensor_REV_ColorRangeSensor = hardwareMap.get(ColorSensor.class, "artifactColorSensor");

        // Adjust the gain.
        ((NormalizedColorSensor) artifactColorSensor_REV_ColorRangeSensor).setGain(2);
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Display distance info.
                telemetry.addData("Dist to tgt (cm)", ((DistanceSensor) artifactColorSensor_REV_ColorRangeSensor).getDistance(DistanceUnit.CM));
                // Read color from the sensor.
                normalizedColors = ((NormalizedColorSensor) artifactColorSensor_REV_ColorRangeSensor).getNormalizedColors();
                // Convert RGB values to Hue.
                color = normalizedColors.toColor();
                hue = JavaUtil.colorToHue(color);
                telemetry.addData("Hue", Double.parseDouble(JavaUtil.formatNumber(hue, 0)));
                // Show the color on the Robot Controller screen.
                JavaUtil.showColor(hardwareMap.appContext, color);
                // Use hue to determine if it's red, green, blue, etc..
                if (hue >= 90 && hue < 150) {
                    telemetry.addData("Color", "Green");
                } else if (hue >= 180 && hue < 350) {
                    telemetry.addData("Color", "purple");
                } else {
                    telemetry.addData("Color", "other");
                }
                telemetry.update();
            }
        }
    }
}
