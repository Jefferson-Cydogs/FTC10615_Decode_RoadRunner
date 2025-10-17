package org.firstinspires.ftc.teamcode.cydogs.learning;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;
public class ColorFinder {

    public static final float MIN_SATURATION = 0.5f;
    public static final float MIN_VALUE = 0.3f;
    public static final int REQUIRED_CONSECUTIVE_HITS = 3;

    public static final float RED_HUE_LOW_1 = 0f;
    public static final float RED_HUE_HIGH_1 = 20f;
    public static final float RED_HUE_HIGH_2 = 360f;
    public static final float RED_HUE_LOW_2 = 340f;
    public String ColorFound;
    public static final float BLUE_HUE_LOW = 190f;
    public static final float BLUE_HUE_HIGH = 260f;
    public static final float PURPLE_HUE_LOW = 190f;
    public static final float PURPLE_HUE_HIGH = 260f;
    public static final float GREEN_HUE_LOW = 190f;
    public static final float GREEN_HUE_HIGH = 260f;

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


    public enum TargetColor {RED, BLUE, GREEN, PURPLE}

    public String SeeColor(TargetColor targetColor) {
        int consecutiveHits = 0;
        while (consecutiveHits < REQUIRED_CONSECUTIVE_HITS) {

            NormalizedRGBA colors = colorSensor.getNormalizedColors();
            float[] hsv = new float[3];
            int r = (int) (colors.red * 255);
            int g = (int) (colors.green * 255);
            int b = (int) (colors.blue * 255);
            Color.RGBToHSV(r, g, b, hsv);
            boolean isHit = false;
            switch (targetColor) {
                case RED:
                    isHit = isColor(hsv, RED_HUE_LOW_1, RED_HUE_HIGH_1, RED_HUE_LOW_2, RED_HUE_HIGH_2);
                    break;
                case BLUE:
                    isHit = isColor(hsv, BLUE_HUE_LOW, BLUE_HUE_HIGH);
                    break;
                case GREEN:
                    isHit = isColor(hsv, GREEN_HUE_LOW, GREEN_HUE_HIGH);
                    break;
                case PURPLE:
                    isHit = isColor(hsv, PURPLE_HUE_LOW, PURPLE_HUE_HIGH);
                    break;
            }
            if (isHit) {
                consecutiveHits++;
            }
            if (consecutiveHits >= REQUIRED_CONSECUTIVE_HITS) {
                switch (targetColor) {
                    case RED:
                        return "Red";
                    break;
                    case BLUE:
                        return "Blue";
                    break;
                    case GREEN:
                        return "Green";
                    break;
                    case PURPLE:
                        return "Purple";
                    break;
                    default:
                        return "Nothing";
                }
            }
        }
        return "Nothing";
    }

    private boolean isColor(float[] hsv, float lowHue, float highHue)
    {
        return isColor(hsv, lowHue, highHue, -1, -1);
    }
    private boolean isColor(float[] hsv, float lowHue1, float highHue1, float lowHue2, float highHue2)
    {
        float h = hsv[0], s = hsv[1], v = hsv[2];
        boolean isColor  =
                (h >= lowHue1 && h <= highHue1) ||
                        (h >= lowHue2 && h <= highHue2);
        return isColor;
    }


    /*
            private boolean isRed(float[] hsv){
                float h = hsv[0], s = hsv[1], v = hsv[2];
                boolean huelsRed =
                        (h >= RED_HUE_LOW_1 && h <= RED_HUE_HIGH_1) ||
                                (h >= RED_HUE_LOW_2 && h <= RED_HUE_HIGH_2);
                return huelsRed && s >= MIN_SATURATION && v >= MIN_VALUE;
            }
            private boolean isBlue ( float[] hsv){
                float h = hsv[0], s = hsv[1], v = hsv[2];
                boolean huelsBlue =
                        (h >= BLUE_HUE_LOW && h <= BLUE_HUE_HIGH);
                return huelsBlue && s >= MIN_SATURATION && v >= MIN_VALUE;

            }

*/

}
