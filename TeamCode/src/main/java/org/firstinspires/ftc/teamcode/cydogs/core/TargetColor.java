package org.firstinspires.ftc.teamcode.cydogs.core;

import androidx.annotation.NonNull;

public enum TargetColor {
    RED(0, 20, 340, 360), // RED wraps around the hue circle
    BLUE(190, 260),
    GREEN(100, 140),
    PURPLE(260, 290);

    private final float low1, high1;
    private final float low2, high2;

    // Constructor for 1 hue range
    TargetColor(float low1, float high1) {
        this(low1, high1, -1, -1);
    }

    // Constructor for 2 hue ranges (for RED)
    TargetColor(float low1, float high1, float low2, float high2) {
        this.low1 = low1;
        this.high1 = high1;
        this.low2 = low2;
        this.high2 = high2;
    }

    public boolean matches(float[] hsv) {
        float h = hsv[0];
        float s = hsv[1];
        float v = hsv[2];

        // Optional filters: ignore dark or grayish colors
        if (s < 0.2 || v < 0.2) return false;

        boolean inPrimaryRange = h >= low1 && h <= high1;
        boolean inSecondaryRange = (low2 >= 0 && h >= low2 && h <= high2); // Only check if second range exists

        return inPrimaryRange || inSecondaryRange;
    }

    @Override
    @NonNull
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase(); // "Red", "Blue", etc.
    }
}
