package org.firstinspires.ftc.teamcode.cydogs.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaChassis;
import org.firstinspires.ftc.teamcode.cydogs.components.AprilTagReaderDuo;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED;
import org.firstinspires.ftc.teamcode.cydogs.components.Feeders;
import org.firstinspires.ftc.teamcode.cydogs.components.Intake;
import org.firstinspires.ftc.teamcode.cydogs.components.LaunchersWithVelocity;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;


@TeleOp
public class CoolPeopleMadeThisTeleop extends LinearOpMode {

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

    private double currentLauncherPower = 0.63;

    //75% launcher velocity from long distance
    //65% launcher from top of short distance


    @Override
    public void runOpMode()
    {
        double voltage;

        // Execute initialization actions here
        Wheels = new IndianaChassis(this);
        Wheels.InitializeTeleop(.7,.3,.5);

        initializeDevices();
        initializePositions();
        //VoltageSensor voltageSensor = hardwareMap.voltageSensor.iterator().next();

        waitForStart();
        while (opModeIsActive())
        {
            // Execute OpMode actions here
            Wheels.TraditionalTeleopDrive();
            manageDriverControls();
            manageManipulatorControls();

            if (RocketLauncher3000.IsMotorTooStrong(currentLauncherPower)) {
                LauncherLED.SetColorByName("red");
                //LauncherLED.SetColor(.29);
            }
            else if (RocketLauncher3000.IsMotorAtSpeed(currentLauncherPower)) {
                LauncherLED.SetColorByName("white");
                //LauncherLED.SetColor(1);
            }
            else {
                LauncherLED.SetColorByName("off");
                //LauncherLED.SetColor(0);
            }

            //voltage = voltageSensor.getVoltage();
            //telemetry.addData("Battery Voltage", voltage);

            telemetry.addData("LauncherPower:",currentLauncherPower);
            telemetry.update();
        }
    }

    private void manageDriverControls()
    {
        if (gamepad1.y) {
            //currentDetection = tagReader.GetScoringTag("Red");
            currentLauncherPower += 0.05;
            RocketLauncher3000.RunAtVelocity(currentLauncherPower);
            sleep(300);
        }
        else if (gamepad1.a) {
            currentLauncherPower -= 0.05;
            RocketLauncher3000.RunAtVelocity(currentLauncherPower);
            sleep(300);
        }
        else if (gamepad1.b) {
            tagReader.turnToFaceAprilTag(.4,5, Wheels,"blue");
        }
    }

    private void manageManipulatorControls()
    {
        if (gamepad2.a) {
            RocketLauncher3000.TurnPowerOff();
        }
        else if (gamepad2.x) {
            RocketLauncher3000.RunAtVelocity(-0.2);
        }
        else if (gamepad2.y) {
            RocketLauncher3000.RunAtVelocity(currentLauncherPower);
        }
        else if (gamepad2.dpad_left) {
            ArtifactEater.reverseleftintake();
        }
        else if (gamepad2.dpad_right) {
            ArtifactEater.reverserightintake();
        }

        if (gamepad2.right_bumper) {
            BumperCars.ActivateRightBumper();
            ArtifactEater.turnRightIntakeon();
        } else {
            BumperCars.DeactivateRightBumper();
            ArtifactEater.turnrightintakeoff();
        }

        if (gamepad2.left_bumper) {
            BumperCars.ActivateLeftBumper();
            ArtifactEater.turnLeftIntakeon();
        }
        else {
            BumperCars.DeactivateLeftBumper();
            ArtifactEater.turnleftintakeoff();
        }

        if (gamepad2.left_trigger > 0.4) {
            ArtifactEater.turnLeftIntakeon();
        }
        else {
            ArtifactEater.turnleftintakeoff();
        }

        if (gamepad2.right_trigger > 0.4) {
            ArtifactEater.turnRightIntakeon();
        }
        else {
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
    }

    private void initializePositions()
    {
        //LauncherLED.SetColor(0);
        LauncherLED.SetColorByName("off");
        //Testing RightLED and LeftLED
        //RightLED.SetColorByName("green");
        //LeftLED.SetColorByName("purple");
    }

}
