package org.firstinspires.ftc.teamcode.cydogs.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaChassis;
import org.firstinspires.ftc.teamcode.cydogs.components.Bumper;
import org.firstinspires.ftc.teamcode.cydogs.components.Intake;
import org.firstinspires.ftc.teamcode.cydogs.components.Turret;


@TeleOp
public class CoolPeopleMadeThisTeleop extends LinearOpMode {

    // declare variables here

    private Turret RocketLauncher3000;
    private Bumper BumperCars;
    private Intake ArtifactEater;

    private IndianaChassis wheels;

    @Override
    public void runOpMode() {

        // Execute initialization actions here
        wheels = new IndianaChassis(this);
        wheels.InitializeTeleop(.7,.3,.5);

        initializeDevices();
        initializePositions();

        waitForStart();
        while (opModeIsActive()) {
            // Execute OpMode actions here
            wheels.TraditionalTeleopDrive();
            manageDriverControls();
            manageManipulatorControls();
        }
    }

    private void manageDriverControls()
    {
        if(gamepad1.triangle)
        {
            // do something if triangle is pushed
        }
        else if(gamepad1.square)
        {
            // do something if square is pushed
        }

    }
    private void manageManipulatorControls()
    {
        if(gamepad2.a)
        {
            RocketLauncher3000.turnPowerOff();
        }

        if(gamepad2.y)
        {
           RocketLauncher3000.runAtPower(0.59);
        }
        if(gamepad2.right_bumper) {
            BumperCars.MoveRightBumper();
        }

        if(gamepad2.left_bumper) {
            BumperCars.MoveLeftBumper();
        }

        if(gamepad2.left_trigger>.4) {
            ArtifactEater.reverseintake();
        }
        else if(gamepad2.right_trigger>.4){
            ArtifactEater.turnIntakeon();
        }

        else {
            ArtifactEater.turnintakeoff();
        }
    }



    private void initializeDevices()
    {
        RocketLauncher3000=new Turret(this);
        ArtifactEater= new Intake(this);
        BumperCars= new Bumper(this);

    }

    private void initializePositions()
    {

    }

}
