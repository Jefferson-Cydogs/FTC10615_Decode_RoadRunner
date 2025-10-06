package org.firstinspires.ftc.teamcode.cydogs;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

public class ColorFinderGP {
    public static final float MIN_SATURATION = 0.5f;
    public static final float MIN_VALUE = 0.3f;
    public static final int REQUIRED_CONSECUTIVE_HITS = 3;
    public static final float PURPLE_HUE_LOW = 270f;
    public static final float PURPLE_HUE_HIGH = 330f;
    public static final float GREEN_HUE_LOW = 90f;
    public static final float GREEN_HUE_HIGH = 150f;

    private final LinearOpMode opMode;
    private final NormalizedColorSensor colorSensor;

    public ColorFinderGP(LinearOpMode opMode, String FunColor) {
        this.opMode = opMode;
        HardwareMap hw = opMode.hardwareMap;
        this.colorSensor = hw.get(NormalizedColorSensor.class, FunColor);
        if (colorSensor instanceof SwitchableLight) {
            try {
                ((SwitchableLight) colorSensor).enableLight(true);
            } catch (Exception ignored) {
            }
        }
    }


    public enum TargetColor {PURPLE, GREEN}
    boolean isHit = false;

    public String SeeColor(TargetColor targetColor) {
        int consecutiveHits = 0;
        while (consecutiveHits<REQUIRED_CONSECUTIVE_HITS) {


            NormalizedRGBA colors = colorSensor.getNormalizedColors();
            float[] hsv = new float[3];
            int r = (int) (colors.red * 255);
            int g = (int) (colors.green * 255);
            int b = (int) (colors.blue * 255);
            Color.RGBToHSV(r, g, b, hsv);
            if (targetColor == TargetColor.PURPLE) {
                isHit = isPurple(hsv);
            } else if (targetColor == TargetColor.GREEN) {
                isHit = isGreen(hsv);
            }
            if (isHit) {
                consecutiveHits++;
            }
            if ((consecutiveHits >= REQUIRED_CONSECUTIVE_HITS) && (targetColor==TargetColor.PURPLE)) {
                return "Purple";
            } else if ((consecutiveHits >= REQUIRED_CONSECUTIVE_HITS) && (targetColor==TargetColor.GREEN)) {
                return "Green";
            }
            else return "Nothing";

        }
        return "bob";

    }

    private boolean isPurple(float[] hsv) {
        float h = hsv[0], s = hsv[1], v = hsv[2];
        boolean huelsPurple =
                (h >= PURPLE_HUE_LOW && h <= PURPLE_HUE_HIGH);
        return huelsPurple && s >= MIN_SATURATION && v >= MIN_VALUE;
    }

    private boolean isGreen(float[] hsv) {
        float h = hsv[0], s = hsv[1], v = hsv[2];
        boolean huelsGreen =
                (h >= GREEN_HUE_LOW && h <= GREEN_HUE_HIGH);
        return huelsGreen && s >= MIN_SATURATION && v >= MIN_VALUE;

    }


}
