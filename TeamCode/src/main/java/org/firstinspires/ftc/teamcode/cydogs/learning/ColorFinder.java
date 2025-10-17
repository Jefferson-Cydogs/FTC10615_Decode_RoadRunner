package org.firstinspires.ftc.teamcode.cydogs.learning;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.teamcode.cydogs.core.TargetColor;

public class ColorFinder {

    public static final float MIN_SATURATION = 0.5f;
    public static final float MIN_VALUE = 0.3f;
    public static final int REQUIRED_CONSECUTIVE_HITS = 3;

    public String ColorFound;

    private final LinearOpMode opMode;
    private final NormalizedColorSensor colorSensor;

    public ColorFinder(LinearOpMode opMode, String artifactColorSensor) {
        this.opMode = opMode;
        HardwareMap hw = opMode.hardwareMap;
        this.colorSensor = hw.get(NormalizedColorSensor.class, artifactColorSensor);
        if (colorSensor instanceof SwitchableLight) {
            try {
                ((SwitchableLight) colorSensor).enableLight(true);
            } catch (Exception ignored) {
            }
        }
    }



    public String SeeColor(TargetColor targetColor) {
        int consecutiveHits = 0;
        int escapeCounter = 0;

        while (consecutiveHits < REQUIRED_CONSECUTIVE_HITS && escapeCounter < 10) {
            NormalizedRGBA colors = colorSensor.getNormalizedColors();
            float[] hsv = new float[3];
            int r = (int) (colors.red * 255);
            int g = (int) (colors.green * 255);
            int b = (int) (colors.blue * 255);
            Color.RGBToHSV(r, g, b, hsv);

            if (targetColor.matches(hsv)) {
                consecutiveHits++;
                escapeCounter = 0;
            } else {
                escapeCounter++;
            }

            if (consecutiveHits >= REQUIRED_CONSECUTIVE_HITS) {
                return targetColor.toString();
            }
        }

        return "Nothing";
    }

}
