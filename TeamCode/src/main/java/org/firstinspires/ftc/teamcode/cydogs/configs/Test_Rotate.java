package org.firstinspires.ftc.teamcode.cydogs.configs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaChassis;
import org.firstinspires.ftc.teamcode.cydogs.chassis.WheelieChassis;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station
@Autonomous
public class Test_Rotate extends LinearOpMode {

    /* declare variables

     */
    @Override
    public void runOpMode() {

        IndianaChassis wheels = new IndianaChassis(this);
        wheels.InitializeAuton();
        // Wait for the start button to be pressed on the driver station
        waitForStart();

        if (opModeIsActive()) {

            wheels.RotateLeft(90, 0.55, 500);
           // wheels.MoveStraight(1000, 0.5, 50);
            sleep(5000);
            //This is where we would scan the obelisk
        }

    }






}