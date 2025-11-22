package org.firstinspires.ftc.teamcode.cydogs.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaChassis;
import org.firstinspires.ftc.teamcode.cydogs.components.ArtifactSensors;
//import org.firstinspires.ftc.teamcode.cydogs.components.ParkingSensors;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED.ColorOption;
import org.firstinspires.ftc.teamcode.cydogs.components.Feeders;
import org.firstinspires.ftc.teamcode.cydogs.components.IntakeV2;
import org.firstinspires.ftc.teamcode.cydogs.components.Gates;
import org.firstinspires.ftc.teamcode.cydogs.components.LaunchersWithVelocity;
import org.firstinspires.ftc.teamcode.cydogs.core.EventTracker;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;


@TeleOp(name="Cool People TeleOp", group= "TeleOp")
public class CoolPeopleBlueV2 extends LinearOpMode {

    /** declare variables here */
    private IndianaChassis Wheels;

    private IntakeV2 ArtifactEater;
    private Feeders BumperCars;
    private Gates Gates;
    private LaunchersWithVelocity RocketLauncher3000;
    //50% launcher from top of short distance
    //67% launcher velocity from long distance
    private final double NearLauncherVelocity = 0.5;
    private final double FarLauncherVelocity = 0.67;
    private double TargetLauncherVelocity = NearLauncherVelocity;

    private ColorLED LauncherLED;
    private ColorLED LeftChannelLED;
    private ColorLED RightChannelLED;
    private ArtifactSensors artifactSensors;
    //private ParkingSensors parkingSensors

    //private AprilTagReaderDuo tagReader;
    private AprilTagDetection currentDetection;

    private ElapsedTime currentTimer;
    private EventTracker eventTracker;

    public String Team = "blue";

    @Override
    public void runOpMode()
    {
        //double voltage;

        /** Execute initialization actions here */
        Wheels = new IndianaChassis(this);
        Wheels.InitializeChassisTeleop(.8,.3,.7);
        initializeDevices();
        initializePositions();
        currentTimer = new ElapsedTime();
        eventTracker = new EventTracker();
        //VoltageSensor voltageSensor = hardwareMap.voltageSensor.iterator().next();

        waitForStart();
        //tagReader.initAprilTag();

        while (opModeIsActive()) {
            /** Execute OpMode actions here */
            //tagReader.displayDetections(tagReader.GetDetections());
            Wheels.OptimizedTeleopDrive();
            manageDriverControls();
            manageManipulatorControls();

            if(eventTracker.doEvent("CheckLauncher",currentTimer.seconds(), 0.5)) {
                checkRocketLauncherVelocity();
            }

            if (eventTracker.doEvent("ArtifactSensors",currentTimer.seconds(),0.5)) {
                artifactSensors.CheckSensors();
            }

            //voltage = voltageSensor.getVoltage();

            if(eventTracker.doEvent("Telemetry",currentTimer.seconds(),0.5)) {
                telemetry.addData("Target Launcher Velocity %:", TargetLauncherVelocity);
                telemetry.addData("Current Launcher Velocity (ticks/s):", RocketLauncher3000.GetCurrentVelocityPercent());
                telemetry.update();
            }
        }
    }


    private void manageDriverControls()
    {
        if (gamepad1.triangleWasPressed()) {
            TargetLauncherVelocity += 0.01;
            RocketLauncher3000.RunAtVelocity(TargetLauncherVelocity);
        } else if (gamepad1.crossWasPressed()) {
            TargetLauncherVelocity -= 0.01;
            RocketLauncher3000.RunAtVelocity(TargetLauncherVelocity);
        } else if (gamepad1.squareWasPressed()) {
            TargetLauncherVelocity = FarLauncherVelocity;
            RocketLauncher3000.RunAtVelocity(TargetLauncherVelocity);
        } else if (gamepad1.circleWasPressed()) {
            TargetLauncherVelocity = NearLauncherVelocity;
            RocketLauncher3000.RunAtVelocity(TargetLauncherVelocity);
        }
        //else if (gamepad1.dpad_down) {
            //RocketLauncher3000.RunAtVelocity(TargetLauncherVelocity);
            //currentDetection = tagReader.GetScoringTag("Red");
            // if (eventTracker.doEvent("TurnToTag", currentTimer.seconds(), 0.5)) {
                //tagReader.displayDetections(tagReader.GetDetections());
                //Wheels.InitializeAutonomous();
               //tagReader.turnToFaceAprilTagTeleop(Wheels, Team, .15, 3, currentTimer, eventTracker);
                //Wheels.InitializeTeleop(.7, .3, .5);
           // }
        //}
    }

    private void manageManipulatorControls()
    {
        if (gamepad2.triangle) {
            RocketLauncher3000.RunAtVelocity(TargetLauncherVelocity);
        }
        else if (gamepad2.square) {
            if (RocketLauncher3000.GetCurrentVelocity() <= 0) {
                RocketLauncher3000.RunAtVelocity(-0.2);
            }
        }
        else if (gamepad2.cross) {
            LaunchersWithVelocity.LauncherDecelerator.decelerateAsync(RocketLauncher3000.Launchers, 0.5,0.02,50);
        }
        else if (gamepad2.dpadUpWasPressed()) {
            Gates.OpenLeftGate();
            Gates.OpenRightGate();
        }
        else if (gamepad2.dpadDownWasPressed()) {
            Gates.CloseRightGate();
            Gates.CloseLeftGate();
        }

        if (gamepad2.left_trigger > 0.4) {
            ArtifactEater.reverseIntake();
        }
        else if (gamepad2.left_bumper) {
            ArtifactEater.turnIntakeOn();
            BumperCars.ActivateLeftBumper();
        }
        else if (gamepad2.dpad_left) {
            BumperCars.ReverseLeftBumper();
        }
        else {
            ArtifactEater.turnIntakeOff();
            BumperCars.DeactivateLeftBumper();
        }

        if (gamepad2.right_trigger > 0.4) {
            ArtifactEater.turnIntakeOn();
        }
        else if (gamepad2.right_bumper) {
            ArtifactEater.turnIntakeOn();
            BumperCars.ActivateRightBumper();
        }
        else if (gamepad2.dpad_right) {
            BumperCars.ReverseRightBumper();
        }
        else {
            ArtifactEater.turnIntakeOff();
            BumperCars.DeactivateRightBumper();
        }
    }

    private void initializeDevices()
    {
        RocketLauncher3000 = new LaunchersWithVelocity(this);
        ArtifactEater = new IntakeV2(this);
        BumperCars = new Feeders(this);
        Gates = new Gates(this);
        LauncherLED = new ColorLED(this,"LauncherLED");
        LeftChannelLED = new ColorLED(this,"LeftLED");
        RightChannelLED = new ColorLED(this,"RightLED");
        artifactSensors = new ArtifactSensors(this);
        //parkingSensors = new ParkingSensors (this);
        //tagReader = new AprilTagReaderDuo(this, "Red");
    }

    private void initializePositions()
    {
        Gates.CloseLeftGate();
        Gates.CloseRightGate();
        LauncherLED.SetColorName(ColorOption.OFF);
        LeftChannelLED.SetColorName(ColorOption.OFF);
        RightChannelLED.SetColorName(ColorOption.OFF);
    }

    private void checkRocketLauncherVelocity()
    {
        if (RocketLauncher3000.IsMotorTooStrong(TargetLauncherVelocity)) {
            LauncherLED.SetColorName(ColorOption.RED);
        }
        else if (RocketLauncher3000.IsMotorAtSpeed(TargetLauncherVelocity)) {
            LauncherLED.SetColorName(ColorOption.WHITE);
        }
        else {
            LauncherLED.SetColorName(ColorOption.OFF);
        }
    }

}
