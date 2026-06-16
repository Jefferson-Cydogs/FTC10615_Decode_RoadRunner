package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED.ColorOption;
import org.firstinspires.ftc.teamcode.cydogs.core.TargetColor;


public class ParkingSensors {
    public String SquareLeftColor;
    public String SquareBackColor;
    private ColorFinder LeftParkingSensor;
    private ColorFinder BackParkingSensor;
    private ColorLED LeftLED;
    private ColorLED CenterLED;
    private ColorLED RightLED;
    private LinearOpMode myOpMode;


    public ParkingSensors(LinearOpMode opMode)
    {
        this.myOpMode = opMode;

        LeftParkingSensor = new ColorFinder(opMode, "LeftParkingSensor");
        BackParkingSensor = new ColorFinder(opMode, "BackParkingSensor");

        LeftLED = new ColorLED(opMode,"LeftLED");
        CenterLED = new ColorLED(opMode,"LauncherLED");
        RightLED = new ColorLED(opMode,"RightLED");
    }

    public boolean TouchingRearTape()
    {
        if (BackParkingSensor.SeeSquareColor(TargetColor.RED)) {
            SquareBackColor = "Red";
            CenterLED.SetColorName(ColorOption.RED);
        }
        else if (BackParkingSensor.SeeSquareColor(TargetColor.BLUE)) {
            SquareBackColor = "Blue";
            CenterLED.SetColorName(ColorOption.BLUE);
        }
        else {
            SquareBackColor = "Nothing";
            CenterLED.SetColorName(ColorOption.OFF);
        }
        //myOpMode.telemetry.addData("Square Back Color:", SquareBackColor);
        return (SquareBackColor.equals("Red")) || (SquareBackColor.equals("Blue"));
    }

    public boolean TouchingLeftTape()
    {
        if (LeftParkingSensor.SeeSquareColor(TargetColor.RED)) {
            SquareLeftColor = "Red";
            LeftLED.SetColorName(ColorOption.RED);
            RightLED.SetColorName(ColorOption.RED);
        }
        else if (LeftParkingSensor.SeeSquareColor(TargetColor.BLUE)) {
            SquareLeftColor = "Blue";
            LeftLED.SetColorName(ColorOption.BLUE);
            RightLED.SetColorName(ColorOption.BLUE);
        }
        else {
            SquareLeftColor = "Nothing";
            LeftLED.SetColorName(ColorOption.OFF);
            RightLED.SetColorName(ColorOption.OFF);
        }
        //myOpMode.telemetry.addData("Square Left Color:", SquareLeftColor);
        return (SquareLeftColor.equals("Red")) || (SquareLeftColor.equals("Blue"));
    }

}
