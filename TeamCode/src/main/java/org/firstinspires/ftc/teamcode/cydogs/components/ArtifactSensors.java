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

    public void CheckSensors() {
        // myOpMode.telemetry.addLine("Left Intake:");
        // LeftIntakeSensor.WhatDoISee();
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

        //  myOpMode.telemetry.addLine("Right Intake:");
        //  RightIntakeSensor.WhatDoISee();
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

        //   myOpMode.telemetry.addLine("Left Launcher:");
        //   LeftLaunchSensor.WhatDoISee();
        if (LeftLaunchSensor.SeeColor(Green)) {
            //myOpMode.telemetry.addLine("J");
            LeftLaunchColor = "Green";
            LeftLED.SetColorByName("green");
        }
        else if (LeftLaunchSensor.SeeColor(Purple)) {
            //myOpMode.telemetry.addLine("K");
            LeftLaunchColor = "Purple";
            LeftLED.SetColorByName("purple");
        }
        else {
            //myOpMode.telemetry.addLine("L");
            LeftLaunchColor = "Nothing";
            LeftLED.SetColorByName("off");
        }

        //  myOpMode.telemetry.addLine("Right Launcher:");
        //  RightLaunchSensor.WhatDoISee();
        if (RightLaunchSensor.SeeColor(Green)) {
            //myOpMode.telemetry.addLine("G");
            RightLaunchColor = "Green";
            RightLED.SetColorByName("green");
        }
        else if (RightLaunchSensor.SeeColor(Purple)) {
            //myOpMode.telemetry.addLine("H");
            RightLaunchColor = "Purple";
            RightLED.SetColorByName("purple");
        }
        else {
            //myOpMode.telemetry.addLine("I");
            RightLaunchColor = "Nothing";
            RightLED.SetColorByName("off");
        }
    }

    public void CheckSensors2() {
        if (LeftIntakeSensor.SeeArtifactColor(Green)) {
            LeftIntakeColor = "Green";
        }
        else if (LeftIntakeSensor.SeeArtifactColor(Purple)) {
            LeftIntakeColor = "Purple";
        }
        else {
            LeftIntakeColor = "Nothing";
        }

        if (RightIntakeSensor.SeeArtifactColor(Green)) {
            RightIntakeColor = "Green";
        }
        else if (RightIntakeSensor.SeeArtifactColor(Purple)) {
            RightIntakeColor = "Purple";
        }
        else {
            RightIntakeColor = "Nothing";
        }

        if (LeftLaunchSensor.SeeArtifactColor(Green)) {
            LeftLaunchColor = "Green";
            LeftLED.SetColorName(ColorOption.GREEN);
        }
        else if (LeftLaunchSensor.SeeArtifactColor(Purple)) {
            LeftLaunchColor = "Purple";
            LeftLED.SetColorName(ColorOption.PURPLE);
        }
        else {
            LeftLaunchColor = "Nothing";
            LeftLED.SetColorName(ColorOption.OFF);
        }

        if (RightLaunchSensor.SeeArtifactColor(Green)) {
            RightLaunchColor = "Green";
            RightLED.SetColorName(ColorOption.GREEN);
        }
        else if (RightLaunchSensor.SeeArtifactColor(Purple)) {
            RightLaunchColor = "Purple";
            RightLED.SetColorName(ColorOption.PURPLE);
        }
        else {
            RightLaunchColor = "Nothing";
            RightLED.SetColorName(ColorOption.OFF);
        }
    }

}
