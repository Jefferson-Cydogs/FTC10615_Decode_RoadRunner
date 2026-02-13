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
    private int ArtifactAtLeftIntake = 0;
    private int ArtifactAtRightIntake = 0;
    private int ArtifactAtLeftLaunch = 0;
    private int ArtifactAtRightLaunch = 0;
    private ColorLED RightLED;
    private ColorLED LeftLED;
    private LinearOpMode myOpMode;

    public ArtifactSensors(LinearOpMode opMode)
    {
        this.myOpMode = opMode;
        LeftIntakeSensor = new ColorFinder(opMode, "LeftIntakeSensor");
        RightIntakeSensor = new ColorFinder(opMode, "RightIntakeSensor");
        LeftLaunchSensor = new ColorFinder(opMode, "LeftLaunchSensor");
        RightLaunchSensor = new ColorFinder(opMode,"RightLaunchSensor");

        RightLED = new ColorLED(opMode,"RightLED");
        LeftLED = new ColorLED(opMode,"LeftLED");
    }

    public void CheckSensors() { CheckSensors(true); }
    public void CheckSensors(boolean setLEDs)
    {
        if (LeftIntakeSensor.SeeArtifactColor(Green)) {
            LeftIntakeColor = "Green";
        }
        else if (LeftIntakeSensor.SeeArtifactColor(Purple)) {
            LeftIntakeColor = "Purple";
        }
        else {
            LeftIntakeColor = "Nothing";
        }
        myOpMode.telemetry.addData("Left Intake Color:", LeftIntakeColor);

        if (RightIntakeSensor.SeeArtifactColor(Green)) {
            RightIntakeColor = "Green";
        }
        else if (RightIntakeSensor.SeeArtifactColor(Purple)) {
            RightIntakeColor = "Purple";
        }
        else {
            RightIntakeColor = "Nothing";
        }
        myOpMode.telemetry.addData("Right Intake Color:", RightIntakeColor);

        if (LeftLaunchSensor.SeeArtifactColor(Green)) {
            LeftLaunchColor = "Green";
            if(setLEDs) {LeftLED.SetColorName(ColorOption.GREEN);}
        }
        else if (LeftLaunchSensor.SeeArtifactColor(Purple)) {
            LeftLaunchColor = "Purple";
            if(setLEDs) {LeftLED.SetColorName(ColorOption.PURPLE);}
        }
        else {
            LeftLaunchColor = "Nothing";
            if(setLEDs) {LeftLED.SetColorName(ColorOption.OFF);}
        }
        myOpMode.telemetry.addData("Left Launch Color:", LeftLaunchColor);

        if (RightLaunchSensor.SeeArtifactColor(Green)) {
            RightLaunchColor = "Green";
            if(setLEDs) {RightLED.SetColorName(ColorOption.GREEN);}
        }
        else if (RightLaunchSensor.SeeArtifactColor(Purple)) {
            RightLaunchColor = "Purple";
            if(setLEDs) {RightLED.SetColorName(ColorOption.PURPLE);}
        }
        else {
            RightLaunchColor = "Nothing";
            if(setLEDs) {RightLED.SetColorName(ColorOption.OFF);}
        }
        myOpMode.telemetry.addData("Right Launch Color:", RightLaunchColor);
    }

    public int CheckArtifactsColorAndCount(double MatchTimerInSeconds)
    {
        boolean UseLeftRightColorLEDs = (75 <= MatchTimerInSeconds);

        if (LeftIntakeSensor.SeeArtifactColor(Green) || LeftIntakeSensor.SeeArtifactColor(Purple)) {
            ArtifactAtLeftIntake = 1;
        } else {
            ArtifactAtLeftIntake = 0;
        }
        myOpMode.telemetry.addData("Artifact at Left Intake:", ArtifactAtLeftIntake);

        if (RightIntakeSensor.SeeArtifactColor(Green) || RightIntakeSensor.SeeArtifactColor(Purple)) {
            ArtifactAtRightIntake = 1;
        } else {
            ArtifactAtRightIntake = 0;
        }
        myOpMode.telemetry.addData("Artifact at Right Intake:", ArtifactAtRightIntake);

        if (LeftLaunchSensor.SeeArtifactColor(Green)) {
            if (UseLeftRightColorLEDs) {
                LeftLED.SetColorName(ColorOption.GREEN);
            }
            ArtifactAtLeftLaunch = 1;
        } else if (LeftLaunchSensor.SeeArtifactColor(Purple)) {
            if (UseLeftRightColorLEDs) {
                LeftLED.SetColorName(ColorOption.PURPLE);
            }
            ArtifactAtLeftLaunch = 1;
        } else {
            if (UseLeftRightColorLEDs) {
                LeftLED.SetColorName(ColorOption.OFF);
            }
            ArtifactAtLeftLaunch = 0;
        }
        myOpMode.telemetry.addData("Artifact at Left Launch:", ArtifactAtLeftLaunch);

        if (RightLaunchSensor.SeeArtifactColor(Green)) {
            if (UseLeftRightColorLEDs) {
                RightLED.SetColorName(ColorOption.GREEN);
            }
            ArtifactAtRightLaunch = 1;
        } else if (RightLaunchSensor.SeeArtifactColor(Purple)) {
            if (UseLeftRightColorLEDs) {
                RightLED.SetColorName(ColorOption.PURPLE);
            }
            ArtifactAtRightLaunch = 1;
        } else {
            if (UseLeftRightColorLEDs) {
                RightLED.SetColorName(ColorOption.OFF);
            }
            ArtifactAtRightLaunch = 0;
        }
        myOpMode.telemetry.addData("Artifact at Right Launch:", ArtifactAtRightLaunch);

        return (ArtifactAtLeftIntake + ArtifactAtRightIntake + ArtifactAtLeftLaunch + ArtifactAtRightLaunch);
    }

}
