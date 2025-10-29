package org.firstinspires.ftc.teamcode.cydogs.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaChassis;
import org.firstinspires.ftc.teamcode.cydogs.components.AprilTagReaderDuo;
import org.firstinspires.ftc.teamcode.cydogs.components.ArtifactSensors;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED;
import org.firstinspires.ftc.teamcode.cydogs.components.Feeders;
import org.firstinspires.ftc.teamcode.cydogs.components.Intake;
import org.firstinspires.ftc.teamcode.cydogs.components.LaunchersWithVelocity;
import org.firstinspires.ftc.teamcode.cydogs.core.EventTracker;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;


@TeleOp
public class CoolPeopleBlue extends LinearOpMode {

    // declare variables here
    private IndianaChassis Wheels;

    private Intake ArtifactEater;
    private Feeders BumperCars;
    private LaunchersWithVelocity RocketLauncher3000;

    private ColorLED LauncherLED;
    private ArtifactSensors artifactSensors;

    private AprilTagReaderDuo tagReader;
    private AprilTagDetection currentDetection;

    public String Team = "Blue";

    //46% launcher from top of short distance
    //53% launcher velocity from long distance
    private double TargetLauncherVelocity = 0.46;

    private ElapsedTime currentTimer;
    private EventTracker eventTracker;
    @Override
    public void runOpMode()
    {
        //double voltage;

        // Execute initialization actions here
        Wheels = new IndianaChassis(this);
        Wheels.InitializeTeleop(.7,.3,.5);
        initializeDevices();
        initializePositions();
        currentTimer = new ElapsedTime();
        eventTracker = new EventTracker();
        //VoltageSensor voltageSensor = hardwareMap.voltageSensor.iterator().next();

        waitForStart();
        tagReader.initAprilTag();

        while (opModeIsActive())
        {
            //tagReader.displayDetections(tagReader.GetDetections());
            // Execute OpMode actions here
            Wheels.OptimizedTeleopDrive();
            //Wheels.TraditionalTeleopDrive();
            manageDriverControls();
            manageManipulatorControls();
            checkRocketLauncherVelocity();

            if(eventTracker.doEvent("ArtifactSensors",currentTimer.seconds(),0.5)) {
                artifactSensors.CheckSensors();
                telemetry.addLine("Checking Artifact Sensors");
            }

            //voltage = voltageSensor.getVoltage();
            //telemetry.addData("Battery Voltage", voltage);

            if(eventTracker.doEvent("Telemetry",currentTimer.seconds(),0.5)) {
                telemetry.addData("Target Launcher Power:", TargetLauncherVelocity);
                telemetry.addData("Current Launcher Power:", RocketLauncher3000.GetCurrentVelocity());
                telemetry.update();
            }
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

            if(eventTracker.doEvent("TurnToTag",currentTimer.seconds(),0.5)) {

                tagReader.displayDetections(tagReader.GetDetections());
                //Wheels.InitializeAutonomous();
                tagReader.turnToFaceAprilTag(Wheels, Team, .15, 3, currentTimer, eventTracker);
                //Wheels.InitializeTeleop(.7, .3, .5);
            }
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
        tagReader = new AprilTagReaderDuo(this, "Red");

        artifactSensors = new ArtifactSensors(this);
    }

    private void initializePositions()
    {
        LauncherLED.SetColorByName("off");
        // Testing RightLED and LeftLED
        //RightLED.SetColorByName("green");
        //LeftLED.SetColorByName("purple");
    }

    private void checkRocketLauncherVelocity()
    {
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
    }

}
