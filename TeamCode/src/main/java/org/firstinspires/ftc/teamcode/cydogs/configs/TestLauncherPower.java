package org.firstinspires.ftc.teamcode.cydogs.configs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaChassis;
import org.firstinspires.ftc.teamcode.cydogs.components.AprilTagReaderDuo;
import org.firstinspires.ftc.teamcode.cydogs.components.Launchers;


@TeleOp
public class TestLauncherPower extends LinearOpMode {

    // declare variables here

    private Launchers bigGuns;

    private IndianaChassis wheels;
    private double currentPower = 0;

    @Override
    public void runOpMode() {

        // Execute initialization actions here
        wheels = new IndianaChassis(this);
        wheels.InitializeTeleop(.7,.3,.5);

        initializeDevices();
        initializePositions();
        bigGuns = new Launchers(this);

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
        if(gamepad1.a)
        {
            bigGuns.turnPowerOff();
        }

        if(gamepad1.y)
        {
            bigGuns.runAtPower(currentPower);
        }

        if(gamepad1.dpad_up)
        {
            currentPower += .05;
            bigGuns.runAtPower(currentPower);
            telemetry.addData("New Power:",currentPower);
        }
        if(gamepad1.dpad_down)
        {
            currentPower -= .05;
            bigGuns.runAtPower(currentPower);
            telemetry.addData("New Power:",currentPower);
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
