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


    public enum TargetColor {RED, BLUE}

    public String SeeColor(TargetColor targetColor) {
        int consecutiveHits = 0;
        while (consecutiveHits<REQUIRED_CONSECUTIVE_HITS) {

            NormalizedRGBA colors = colorSensor.getNormalizedColors();
            float[] hsv = new float[3];
            int r = (int) (colors.red * 255);
            int g = (int) (colors.green * 255);
            int b = (int) (colors.blue * 255);
            Color.RGBToHSV(r, g, b, hsv);
            boolean isHit = false;
            if (targetColor == TargetColor.RED) {
                isHit = isRed(hsv);
            } else if (targetColor == TargetColor.BLUE) {
                isHit = isBlue(hsv);
            }
            if (isHit) {
                consecutiveHits++;
            }
            if (consecutiveHits >= REQUIRED_CONSECUTIVE_HITS && targetColor== TargetColor.RED){
                ColorFound= "Red";
            }
            else if (consecutiveHits >= REQUIRED_CONSECUTIVE_HITS && targetColor== TargetColor.BLUE){
                ColorFound= "Blue";
            }
            else {
                ColorFound="Nothing";
            }

        }
        return ColorFound;
    }
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
        }
