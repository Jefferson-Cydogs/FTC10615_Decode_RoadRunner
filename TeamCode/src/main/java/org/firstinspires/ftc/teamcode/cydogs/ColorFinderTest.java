package org.firstinspires.ftc.teamcode.cydogs;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp
public class ColorFinderTest extends LinearOpMode {

    // declare variables here
    private float gamepad1_RightStickYValue;
    private float gamepad1_RightStickXValue;
    private float gamepad1_LeftStickYValue;
    private float gamepad1_LeftStickXValue;
    private float gamepad1_TriggersValue;
    private String FindingColor="";


    @Override
    public void runOpMode() {

        // Execute initialization actions here

        ColorFinderGP myColorFinder = new ColorFinderGP(this,"artifactColorSensor");

        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                // Execute OpMode actions here

                if(gamepad1.y){
                    FindingColor = myColorFinder.SeeColorGP(ColorFinderGP.TargetColor.GREEN);
                    telemetry.addData("The color found", FindingColor);
                    telemetry.update();
                    sleep(500);
                }
            }
        }
    }

    private void manageDriverControls()
    {
        if(gamepad1.triangle)
        {

        }
        else if(gamepad1.square)
        {
            // do something if square is pushed
        }

    }
}



