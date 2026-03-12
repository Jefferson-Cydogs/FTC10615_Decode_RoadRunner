package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED.ColorOption;
import org.firstinspires.ftc.teamcode.cydogs.core.TargetColor;


public class ParkingSensors {
    public String LeftSquareColor;
    public String BackSquareColor;
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

    public void CheckSensors()
    {
        if (LeftParkingSensor.SeeSquareColor(TargetColor.RED)) {
            LeftSquareColor = "Red";
            CenterLED.SetColorName(ColorOption.RED);
        }
        else if (LeftParkingSensor.SeeSquareColor(TargetColor.BLUE)) {
            LeftSquareColor = "Blue";
            CenterLED.SetColorName(ColorOption.BLUE);
        }
        else {
            LeftSquareColor = "Nothing";
            CenterLED.SetColorName(ColorOption.OFF);
        }
        myOpMode.telemetry.addData("Left Square Color:", LeftSquareColor);

        if (BackParkingSensor.SeeSquareColor(TargetColor.RED)) {
            BackSquareColor = "Red";
            LeftLED.SetColorName(ColorOption.RED);
            RightLED.SetColorName(ColorOption.RED);
        }
        else if (BackParkingSensor.SeeSquareColor(TargetColor.BLUE)) {
            BackSquareColor = "Blue";
            LeftLED.SetColorName(ColorOption.BLUE);
            RightLED.SetColorName(ColorOption.BLUE);
        }
        else {
            BackSquareColor = "Nothing";
            LeftLED.SetColorName(ColorOption.OFF);
            RightLED.SetColorName(ColorOption.OFF);
        }
        myOpMode.telemetry.addData("Back Square Color:", BackSquareColor);
    }

}
