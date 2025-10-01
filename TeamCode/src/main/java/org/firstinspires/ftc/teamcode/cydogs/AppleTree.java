package org.firstinspires.ftc.teamcode.cydogs;

public class AppleTree {
    private double treeHeightMeters = 6;
    public int appleCount = 0;

    public int GrowApples(int ApplesToGrow)
    {
        appleCount = appleCount + ApplesToGrow;
        return appleCount;
    }
}
