package org.firstinspires.ftc.teamcode.cydogs.configs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaChassis;
import org.firstinspires.ftc.teamcode.cydogs.components.AprilTagReader;
import org.firstinspires.ftc.teamcode.cydogs.components.AprilTagReaderDuo;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorFinder;


@TeleOp
public class TelopTestAprilTags extends LinearOpMode {

    // declare variables here

    private AprilTagReaderDuo tagReader;

    private IndianaChassis wheels;

    @Override
    public void runOpMode() {

        // Execute initialization actions here
        wheels = new IndianaChassis(this);
        wheels.InitializeTeleop(.7,.3,.5);

        initializeDevices();
        initializePositions();
        tagReader.initAprilTag();

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
        if(gamepad1.y)
        {
            tagReader.displayDetections(tagReader.GetDetections());
            telemetry.update();
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
