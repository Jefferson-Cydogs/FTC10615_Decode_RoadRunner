package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.core.TargetColor;


public class ParkingSensors {
    public String LeftSquareColor;
    public String BackSquareColor;
    private ColorFinder LeftParkingSensor;
    private ColorFinder BackParkingSensor;
    private LinearOpMode myOpMode;


    public ParkingSensors(LinearOpMode opMode)
    {
        this.myOpMode = opMode;

        LeftParkingSensor = new ColorFinder(opMode, "LeftParkingSensor");
        BackParkingSensor = new ColorFinder(opMode, "BackParkingSensor");
    }

    public void CheckSensors()
    {
        if (LeftParkingSensor.SeeSquareColor(TargetColor.RED)) {
            LeftSquareColor = "RED";
        }
        else if (LeftParkingSensor.SeeSquareColor(TargetColor.BLUE)) {
            LeftSquareColor = "BLUE";
        }
        else {
            LeftSquareColor = "NOTHING";
        }

        if (BackParkingSensor.SeeSquareColor(TargetColor.RED)) {
            BackSquareColor = "RED";
        }
        else if (BackParkingSensor.SeeSquareColor(TargetColor.BLUE)) {
            BackSquareColor = "BLUE";
        }
        else {
            BackSquareColor = "NOTHING";
        }
    }

}
