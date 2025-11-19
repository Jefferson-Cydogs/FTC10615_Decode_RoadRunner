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
    private NormalizedRGBA NormalizedColors;
    private int NormalizedColor;
    private float Hue;
    public static final int REQUIRED_CONSECUTIVE_HITS = 3;
    private final double DISTANCE_THRESHOLD_CM = 6.0;
    private final int REQUIRED_MATCHES = 2;
    private long lastArtifactCheckTime = 0;
    private long lastSquareCheckTime = 0;
    private boolean lastArtifactResult = false;
    private boolean lastSquareResult = false;

    private final LinearOpMode opMode;
    private final NormalizedColorSensor colorSensor;
    private final DistanceSensor distanceSensor;

    public ColorFinder(LinearOpMode opMode, String ColorDistanceSensor)
    {
        this.opMode = opMode;
        HardwareMap hw = opMode.hardwareMap;
        this.colorSensor = hw.get(NormalizedColorSensor.class, ColorDistanceSensor);
        this.distanceSensor = hw.get(DistanceSensor.class, ColorDistanceSensor);

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

    public boolean SeeArtifactColor(TargetColor targetColor)
    {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastArtifactCheckTime < 500) {
            return lastArtifactResult; // Return cached result
        }

        int matchCount = 0;

        for (int i = 0; i < 3; i++) {
            if (distanceSensor.getDistance(DistanceUnit.CM) > DISTANCE_THRESHOLD_CM) {
                continue; // Too far, skip this reading
            }

            NormalizedColors = colorSensor.getNormalizedColors();
            NormalizedColor = NormalizedColors.toColor();
            Hue = JavaUtil.colorToHue(NormalizedColor);

            if (targetColor.matches2(Hue)) {
                matchCount++;
            }
        }

        lastArtifactResult = matchCount >= REQUIRED_MATCHES;
        lastArtifactCheckTime = currentTime;
        return lastArtifactResult;
    }

    public boolean SeeSquareColor(TargetColor targetColor)
    {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSquareCheckTime < 500) {
            return lastSquareResult; // Return cached result
        }

        int matchCount = 0;

        for (int i = 0; i < 3; i++) {
            NormalizedColors = colorSensor.getNormalizedColors();
            NormalizedColor = NormalizedColors.toColor();
            Hue = JavaUtil.colorToHue(NormalizedColor);

            if (targetColor.matches2(Hue)) {
                matchCount++;
            }
        }

        lastSquareResult = matchCount >= REQUIRED_MATCHES;
        lastSquareCheckTime = currentTime;
        return lastSquareResult;
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
