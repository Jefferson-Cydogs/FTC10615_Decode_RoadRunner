package org.firstinspires.ftc.teamcode.cydogs.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaChassis;
import org.firstinspires.ftc.teamcode.cydogs.components.AprilTagReaderDuo;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorFinder;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED;
import org.firstinspires.ftc.teamcode.cydogs.components.Feeders;
import org.firstinspires.ftc.teamcode.cydogs.components.Intake;
import org.firstinspires.ftc.teamcode.cydogs.components.LaunchersWithVelocity;
import org.firstinspires.ftc.teamcode.cydogs.core.TargetColor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;


@TeleOp
@Disabled
public class CoolPeopleBlueWithColorSensors extends LinearOpMode {

    // declare variables here
    private IndianaChassis Wheels;

    private Intake ArtifactEater;
    private Feeders BumperCars;
    private LaunchersWithVelocity RocketLauncher3000;

    private ColorLED LauncherLED;
    private ColorLED RightLED;
    private ColorLED LeftLED;

    private AprilTagReaderDuo tagReader;
    private AprilTagDetection currentDetection;
    private String LeftIntakeColor;
    private String RightIntakeColor;
    private String LeftLaunchColor;
    private String RightLaunchColor;
    private ColorFinder LeftIntakeSensor;
    private ColorFinder RightIntakeSensor;
    private ColorFinder LeftLaunchSensor;
    private ColorFinder RightLaunchSensor;
    private TargetColor Green = TargetColor.ARTIFACTGREEN;
    private TargetColor Purple = TargetColor.ARTIFACTPURPLE;

    public String Team = "Blue";

    //46% launcher from top of short distance
    //53% launcher velocity from long distance
    private double TargetLauncherVelocity = 0.46;

    @Override
    public void runOpMode()
    {
        //double voltage;

        // Execute initialization actions here
        Wheels = new IndianaChassis(this);
        Wheels.InitializeChassisTeleop(.7,.3,.5);
        initializeDevices();
        initializePositions();
        //VoltageSensor voltageSensor = hardwareMap.voltageSensor.iterator().next();

        waitForStart();
        while (opModeIsActive())
        {
            if (LeftIntakeSensor.SeeColor(Green))
            {
             LeftIntakeColor = "Green";
            } else if (LeftIntakeSensor.SeeColor(Purple)) {
                LeftIntakeColor = "Purple";
            }
            else LeftIntakeColor = "Nothing";

            if (RightIntakeSensor.SeeColor(Green))
            {
                RightIntakeColor = "Green";
            } else if (RightIntakeSensor.SeeColor(Purple)) {
                RightIntakeColor = "Purple";
            }
            else RightIntakeColor = "Nothing";

            if (RightLaunchSensor.SeeColor(Green))
            {
                RightLaunchColor = "Green";
                RightLED.SetColorByName("Green");
            } else if (RightLaunchSensor.SeeColor(Purple)) {
                RightLaunchColor = "Purple";
                RightLED.SetColorByName("Purple");
            }
            else RightLaunchColor = "Nothing";
            RightLED.SetColorByName("Off");
            if (LeftLaunchSensor.SeeColor(Green))
            {
                LeftLaunchColor = "Green";
                LeftLED.SetColorByName("Green");
            } else if (LeftIntakeSensor.SeeColor(Purple)) {
                LeftLaunchColor = "Purple";
                LeftLED.SetColorByName("Purple");
            }
            else LeftLaunchColor = "Nothing";
            LeftLED.SetColorByName("Off");
            // Execute OpMode actions here

            Wheels.OptimizedTeleopDrive();
            manageDriverControls();
            manageManipulatorControls();

            if (RocketLauncher3000.IsMotorTooStrong(TargetLauncherVelocity)) {
                LauncherLED.SetColorByName("red");
            }
            else if (RocketLauncher3000.IsMotorAtSpeed(TargetLauncherVelocity)) {
                LauncherLED.SetColorByName("white");
            }
            else {
                if (!LauncherLED.IsAlreadyOff()) {
                    LauncherLED.SetColorByName("off");
                }
            }

            //voltage = voltageSensor.getVoltage();
            //telemetry.addData("Battery Voltage", voltage);

            telemetry.addData("Target Launcher Power:", TargetLauncherVelocity);
            telemetry.addData("Current Launcher Power:", RocketLauncher3000.GetCurrentVelocity());
            telemetry.update();
        }
    }

    private void manageDriverControls()
    {
        if (gamepad1.y) {
            TargetLauncherVelocity += 0.01;
            RocketLauncher3000.RunAtVelocity(TargetLauncherVelocity);
            sleep(300);
        }
        else if (gamepad1.a) {
            TargetLauncherVelocity -= 0.01;
            RocketLauncher3000.RunAtVelocity(TargetLauncherVelocity);
            sleep(300);
        }
        else if (gamepad1.x) {
            TargetLauncherVelocity = 0.53;
            RocketLauncher3000.RunAtVelocity(TargetLauncherVelocity);
            sleep(300);
        }
        else if (gamepad1.b) {
            //currentDetection = tagReader.GetScoringTag("Red");
            //tagReader.turnToFaceAprilTagTeleop(.4,5, Wheels,"blue");
        }
    }

    private void manageManipulatorControls()
    {
        if (gamepad2.a) {
            RocketLauncher3000.TurnPowerOff();
        }
        else if (gamepad2.x) {
            if (RocketLauncher3000.GetCurrentVelocity() <= 0) {
                RocketLauncher3000.RunAtVelocity(-0.2);
            }
        }
        else if (gamepad2.y) {
            RocketLauncher3000.RunAtVelocity(TargetLauncherVelocity);
        }
        else if (gamepad2.dpad_left) {
            ArtifactEater.reverseleftintake();
        }
        else if (gamepad2.dpad_right) {
            ArtifactEater.reverserightintake();
        }
        else if (gamepad2.left_bumper) {
            ArtifactEater.turnLeftIntakeon();
            BumperCars.ActivateLeftBumper();
        }
        else if (gamepad2.right_bumper) {
            ArtifactEater.turnRightIntakeon();
            BumperCars.ActivateRightBumper();
        }
        else if (gamepad2.left_trigger > 0.4) {
            ArtifactEater.turnLeftIntakeon();
        }
        else if (gamepad2.right_trigger > 0.4) {
            ArtifactEater.turnRightIntakeon();
        }
        else {
            BumperCars.DeactivateLeftBumper();
            BumperCars.DeactivateRightBumper();
            ArtifactEater.turnleftintakeoff();
            ArtifactEater.turnrightintakeoff();
        }
    }

    private void initializeDevices()
    {
        RocketLauncher3000 = new LaunchersWithVelocity(this);
        ArtifactEater = new Intake(this);
        BumperCars = new Feeders(this);
        LauncherLED = new ColorLED(this,"LauncherLED");
        RightLED = new ColorLED(this,"RightLED");
        LeftLED = new ColorLED(this,"LeftLED");
        tagReader = new AprilTagReaderDuo(this, "Red");
        LeftIntakeSensor = new ColorFinder(this, "LeftIntakeSensor");
        RightIntakeSensor = new ColorFinder(this, "RightIntakeSensor");
        LeftLaunchSensor = new ColorFinder(this, "LeftLaunchSensor");
        RightLaunchSensor = new ColorFinder(this,"RightLaunchSensor");
        //BackParkingSensor (Name of parking sensor)
        //LeftParkingSensor (Name of parking sensor
    }

    private void initializePositions()
    {
        LauncherLED.SetColorByName("off");
        // Testing RightLED and LeftLED
        //RightLED.SetColorByName("green");
        //LeftLED.SetColorByName("purple");
    }

}
