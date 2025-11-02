package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED.ColorOption;
import org.firstinspires.ftc.teamcode.cydogs.core.TargetColor;

public class ArtifactSensors {
    public String LeftIntakeColor;
    public String RightIntakeColor;
    public String LeftLaunchColor;
    public String RightLaunchColor;
    private ColorFinder LeftIntakeSensor;
    private ColorFinder RightIntakeSensor;
    private ColorFinder LeftLaunchSensor;
    private ColorFinder RightLaunchSensor;
    private TargetColor Green = TargetColor.ARTIFACTGREEN;
    private TargetColor Purple = TargetColor.ARTIFACTPURPLE;
    private ColorLED RightLED;
    private ColorLED LeftLED;
    private LinearOpMode myOpMode;


    public ArtifactSensors(LinearOpMode opMode)
    {
        this.myOpMode = opMode;
        RightLED = new ColorLED(opMode,"RightLED");
        LeftLED = new ColorLED(opMode,"LeftLED");

        LeftIntakeSensor = new ColorFinder(opMode, "LeftIntakeSensor");
        RightIntakeSensor = new ColorFinder(opMode, "RightIntakeSensor");
        LeftLaunchSensor = new ColorFinder(opMode, "LeftLaunchSensor");
        RightLaunchSensor = new ColorFinder(opMode,"RightLaunchSensor");
    }

    public void CheckSensors()
    {
        //myOpMode.telemetry.addLine("Left Intake:");
        //LeftIntakeSensor.WhatDoISee();
        if (LeftIntakeSensor.SeeColor(Green)) {
           // myOpMode.telemetry.addLine("A");
            LeftIntakeColor = "Green";
        }
        else if (LeftIntakeSensor.SeeColor(Purple)) {
            //myOpMode.telemetry.addLine("B");
            LeftIntakeColor = "Purple";
        }
        else {
            //myOpMode.telemetry.addLine("C");
            LeftIntakeColor = "Nothing";
        }

        //myOpMode.telemetry.addLine("Right Intake:");
        //RightIntakeSensor.WhatDoISee();
        if (RightIntakeSensor.SeeColor(Green)) {
            //myOpMode.telemetry.addLine("D");
            RightIntakeColor = "Green";
        }
        else if (RightIntakeSensor.SeeColor(Purple)) {
            //myOpMode.telemetry.addLine("E");
            RightIntakeColor = "Purple";
        }
        else {
            //myOpMode.telemetry.addLine("F");
            RightIntakeColor = "Nothing";
        }

        //myOpMode.telemetry.addLine("Right Launcher:");
        //RightLaunchSensor.WhatDoISee();
        if (RightLaunchSensor.SeeColor(Green)) {
            //myOpMode.telemetry.addLine("G");
            RightLaunchColor = "Green";
            RightLED.SetColorName(ColorOption.GREEN);
        }
        else if (RightLaunchSensor.SeeColor(Purple)) {
            //myOpMode.telemetry.addLine("H");
            RightLaunchColor = "Purple";
            RightLED.SetColorName(ColorOption.PURPLE);
        }
        else {
            //myOpMode.telemetry.addLine("I");
            RightLaunchColor = "Nothing";
            RightLED.SetColorName(ColorOption.OFF);
        }

        //myOpMode.telemetry.addLine("Left Launcher:");
        //LeftLaunchSensor.WhatDoISee();
        if (LeftLaunchSensor.SeeColor(Green)) {
            //myOpMode.telemetry.addLine("J");
            LeftLaunchColor = "Green";
            LeftLED.SetColorName(ColorOption.GREEN);
        }
        else if (LeftIntakeSensor.SeeColor(Purple)) {
            //myOpMode.telemetry.addLine("K");
            LeftLaunchColor = "Purple";
            LeftLED.SetColorName(ColorOption.PURPLE);
        }
        else {
            //myOpMode.telemetry.addLine("L");
            LeftLaunchColor = "Nothing";
            LeftLED.SetColorName(ColorOption.OFF);
        }
    }

}
