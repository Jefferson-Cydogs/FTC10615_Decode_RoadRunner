package org.firstinspires.ftc.teamcode.cydogs.components;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import org.firstinspires.ftc.teamcode.cydogs.core.TargetColor;


public class ColorFinder {

    //public static final float MIN_SATURATION = 0.5f;
    //public static final float MIN_VALUE = 0.3f;
    public static final int REQUIRED_CONSECUTIVE_HITS = 3;
    final double DISTANCE_THRESHOLD_CM = 6.0;
    final int REQUIRED_MATCHES = 2;
    private long lastCheckTime = 0;
    private boolean lastResult = false;

    private final LinearOpMode opMode;
    private final NormalizedColorSensor colorSensor;
    private final DistanceSensor distanceSensor;

    public ColorFinder(LinearOpMode opMode, String artifactColorSensor)
    {
        this.opMode = opMode;
        HardwareMap hw = opMode.hardwareMap;
        this.colorSensor = hw.get(NormalizedColorSensor.class, artifactColorSensor);
        this.distanceSensor = hw.get(DistanceSensor.class, artifactColorSensor);

        // Adjust the gain, as the sensor may default to an unpredictable value, leading to inconsistent color readings.
        // Recommendation based on FTC documentation: 10.0 - 20.0 for bright lighting or LEDs, 20.0 - 40.0 for normal indoor lighting.
        // 30.0 is a safe default for FTC
        colorSensor.setGain(30);
    }

    public boolean SeeColor(TargetColor targetColor)
    {
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
                return true;
            }
        }

        return false;
    }

    public boolean SeeColor2(TargetColor targetColor)
    {
        NormalizedRGBA NormalizedColors;
        int Color;
        float Hue;
        //float Saturation;

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCheckTime < 500) {
            return lastResult; // Return cached result
        }

        int matchCount = 0;

        for (int i = 0; i < 3; i++) {
            if (distanceSensor.getDistance(DistanceUnit.CM) > DISTANCE_THRESHOLD_CM) {
                continue; // Too far, skip this reading
            }

            NormalizedColors = colorSensor.getNormalizedColors();
            Color = NormalizedColors.toColor();
            Hue = JavaUtil.colorToHue(Color);
            //Saturation = JavaUtil.colorToSaturation(Color);

            if (targetColor.matches2(Hue)) {
                matchCount++;
            }
        }

        lastResult = matchCount >= REQUIRED_MATCHES;
        lastCheckTime = currentTime;
        return lastResult;
    }

    public void WhatDoISee()
    {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        float[] hsv = new float[3];
        int r = (int) (colors.red * 255);
        int g = (int) (colors.green * 255);
        int b = (int) (colors.blue * 255);
        Color.RGBToHSV(r, g, b, hsv);

        opMode.telemetry.addData("Normalized Red", "%.3f", colors.red);
        opMode.telemetry.addData("Normalized Green", "%.3f", colors.green);
        opMode.telemetry.addData("Normalized Blue", "%.3f", colors.blue);
        opMode.telemetry.addData("Normalized Alpha", "%.3f", colors.alpha);
        opMode.telemetry.addData("Distance:",distanceSensor.getDistance(DistanceUnit.MM));
        opMode.telemetry.addData("RGB", "(%d, %d, %d)", r, g, b);

        opMode.telemetry.addData("HSV", "H: %.1f  S: %.3f  V: %.3f", hsv[0], hsv[1], hsv[2]);
    }

}
