package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED.ColorOption;
import org.firstinspires.ftc.teamcode.cydogs.core.TargetColor;


public class ArtifactSensors {
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
        LeftIntakeSensor = new ColorFinder(opMode, "LeftIntakeSensor");
        RightIntakeSensor = new ColorFinder(opMode, "RightIntakeSensor");
        LeftLaunchSensor = new ColorFinder(opMode, "LeftLaunchSensor");
        RightLaunchSensor = new ColorFinder(opMode,"RightLaunchSensor");

        LeftLED = new ColorLED(opMode,"LeftLED");
        RightLED = new ColorLED(opMode,"RightLED");
    }

    public int CheckArtifactsColorAndCount(double MatchTimerInSeconds)
    {
        int ArtifactAtLeftIntake;
        int ArtifactAtRightIntake;
        int ArtifactAtLeftLaunch;
        int ArtifactAtRightLaunch;
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
            if (UseLeftRightColorLEDs) { //Reflect green Artifact color on LED only after 75s of TeleOp
                LeftLED.SetColorName(ColorOption.GREEN);
            }
            ArtifactAtLeftLaunch = 1;
        } else if (LeftLaunchSensor.SeeArtifactColor(Purple)) {
            if (UseLeftRightColorLEDs) { //Reflect purple Artifact color on LED only after 75s of TeleOp
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
            if (UseLeftRightColorLEDs) { //Reflect green Artifact color on LED only after 75s of TeleOp
                RightLED.SetColorName(ColorOption.GREEN);
            }
            ArtifactAtRightLaunch = 1;
        } else if (RightLaunchSensor.SeeArtifactColor(Purple)) {
            if (UseLeftRightColorLEDs) { //Reflect purple Artifact color on LED only after 75s of TeleOp
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
