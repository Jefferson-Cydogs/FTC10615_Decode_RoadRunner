package org.firstinspires.ftc.teamcode.cydogs.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaChassis;
import org.firstinspires.ftc.teamcode.cydogs.components.AprilTagReader;
import org.firstinspires.ftc.teamcode.cydogs.components.AprilTagReaderDuo;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED;
import org.firstinspires.ftc.teamcode.cydogs.components.Feeders;
import org.firstinspires.ftc.teamcode.cydogs.components.Intake;
import org.firstinspires.ftc.teamcode.cydogs.components.Launchers;
import org.firstinspires.ftc.teamcode.cydogs.components.LaunchersWithVelocity;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;


@TeleOp
public class CoolPeopleMadeThisTeleop extends LinearOpMode {

    // declare variables here

    private LaunchersWithVelocity RocketLauncher3000;
    private Feeders BumperCars;
    private Intake ArtifactEater;

    private IndianaChassis wheels;
    private ColorLED LauncherLED;
    private ColorLED RightLED;
    private ColorLED LeftLED;

    private AprilTagReaderDuo tagReader;

    private AprilTagDetection currentDetection;

    private double currentLauncherPower = 0.63;

    //75% launcher velocity from long distance
    //65% launcher from top of short distance


    @Override
    public void runOpMode() {
        double voltage;
        // Execute initialization actions here
        wheels = new IndianaChassis(this);
        wheels.InitializeTeleop(.7,.3,.5);

        initializeDevices();
        initializePositions();
        VoltageSensor voltageSensor = hardwareMap.voltageSensor.iterator().next();

        waitForStart();
        while (opModeIsActive()) {
            // Execute OpMode actions here
            wheels.TraditionalTeleopDrive();
            manageDriverControls();
            manageManipulatorControls();

             voltage = voltageSensor.getVoltage();

            telemetry.addData("Battery Voltage", voltage);
            telemetry.addData("newPower:",currentLauncherPower);

            if(RocketLauncher3000.CheckIfMotorIsTooStrong(currentLauncherPower)){
                LauncherLED.SetColor(.29);
            }
            else if(RocketLauncher3000.CheckMotor(currentLauncherPower)){
                LauncherLED.SetColor(1);
            }
            else {
                LauncherLED.SetColor(0);
            }
            telemetry.update();
        }
    }

    private void manageDriverControls()
    {
        if(gamepad1.y)
        {
            //currentDetection = tagReader.GetScoringTag("Red");
            currentLauncherPower += 0.05;
            RocketLauncher3000.runAtPower(currentLauncherPower);
            sleep(300);

        }
        else if(gamepad1.a)
        {
            currentLauncherPower -= 0.05;
            RocketLauncher3000.runAtPower(currentLauncherPower);
            sleep(300);
        }
        if(gamepad1.b)
        {
            tagReader.turnToFaceAprilTag(.4,5,wheels,"blue");

        }
    }
    private void manageManipulatorControls() {
        if (gamepad2.a) {
            RocketLauncher3000.turnPowerOff();
        }

        if (gamepad2.y) {
            RocketLauncher3000.runAtPower(currentLauncherPower);
        }
        if (gamepad2.right_bumper) {
            BumperCars.ActivateRightBumper();
            ArtifactEater.turnRightIntakeon();
        } else {
            BumperCars.DeactivateRightBumper();
            ArtifactEater.turnrightintakeoff();

        }
        if (gamepad2.x) {
            RocketLauncher3000.runAtPower(-0.2);
        }

        if(gamepad2.left_bumper) {
            BumperCars.ActivateLeftBumper();
            ArtifactEater.turnLeftIntakeon();
        }
        else {
             BumperCars.DeactivateLeftBumper();
             ArtifactEater.turnleftintakeoff();
        }

        if(gamepad2.left_trigger>.4) {
            ArtifactEater.turnLeftIntakeon();
        }
        else if(gamepad2.dpad_left){
            ArtifactEater.reverseleftintake();
        }
        else {
            ArtifactEater.turnleftintakeoff();
        }

        if(gamepad2.dpad_right){
            ArtifactEater.reverserightintake();
        }
        else if(gamepad2.right_trigger>.4){
            ArtifactEater.turnRightIntakeon();
        }
        else {
            ArtifactEater.turnrightintakeoff();
        }

    }



    private void initializeDevices()
    {
        RocketLauncher3000=new LaunchersWithVelocity(this);
        ArtifactEater= new Intake(this);
        BumperCars= new Feeders(this);
        LauncherLED= new ColorLED(this,"LauncherLED");
        RightLED = new ColorLED(this,"RightLED");
        LeftLED = new ColorLED(this,"LeftLED");
        tagReader = new AprilTagReaderDuo(this, "Red");

    }

    private void initializePositions()
    {
        LauncherLED.SetColor(0);
        RightLED.SetColor(.4);
        LeftLED.SetColor(.7);
    }

}
