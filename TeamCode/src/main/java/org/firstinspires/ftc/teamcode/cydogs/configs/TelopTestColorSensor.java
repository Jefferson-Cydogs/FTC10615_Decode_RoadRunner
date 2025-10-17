package org.firstinspires.ftc.teamcode.cydogs.configs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaChassis;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorFinder;
import org.firstinspires.ftc.teamcode.cydogs.components.Feeders;
import org.firstinspires.ftc.teamcode.cydogs.components.Intake;
import org.firstinspires.ftc.teamcode.cydogs.components.Launchers;


@TeleOp
public class TelopTestColorSensor extends LinearOpMode {

    // declare variables here

    private ColorFinder leftArtifact;
    private ColorFinder rightArtifact;

    private IndianaChassis wheels;

    @Override
    public void runOpMode() {

        // Execute initialization actions here
        wheels = new IndianaChassis(this);
        wheels.InitializeTeleop(.7,.3,.5);

        initializeDevices();
        initializePositions();
        leftArtifact = new ColorFinder(this,"LeftIntakeSensor");
        rightArtifact = new ColorFinder(this, "RightIntakeSensor");

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
        if(gamepad1.x)
        {
            leftArtifact.WhatDoISee();
            telemetry.update();
        }
        else if(gamepad1.b)
        {
            rightArtifact.WhatDoISee();
            telemetry.update(); // do something if square is pushed
        }

    }
    private void manageManipulatorControls()
    {

    }



    private void initializeDevices()
    {


    }

    private void initializePositions()
    {

    }

}
