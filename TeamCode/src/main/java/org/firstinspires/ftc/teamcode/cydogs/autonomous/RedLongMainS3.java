package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaAutonRed;
import org.firstinspires.ftc.teamcode.cydogs.core.EventTracker;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station

@Autonomous(name= "Red Long S3", group= "Autonomous", preselectTeleOp = "Cool People TeleOp")
public class RedLongMainS3 extends LinearOpMode {

    // declare variables
    private double velocityPercentage = 0.66;

    private IndianaAutonRed indiana;



    @Override
    public void runOpMode() {
        // load 2 purple on left side
        // load 1 green on right side by launcher


        indiana = new IndianaAutonRed(this);
        indiana.InitializeAuton();



        indiana.ColorLEDForAlliance("far");


        int startWaitTime = indiana.AskStartWaitTime();

        waitForStart();

        if (opModeIsActive()) {
            sleep(startWaitTime*1000);


            // this clears bumper servo bug
            indiana.Feeders.MoveBumpersToFixBug();

            indiana.Launchers.RunAtVelocity(velocityPercentage);

            indiana.MoveStraight(205,0.5,100);
            sleep(200);
            indiana.GetMotif();
            indiana.ColorLEDForMotif();

            indiana.ReverseFeeders(150);


            indiana.RotateRight(17.5,0.4,100);

            // need to open gates
            indiana.Gates.OpenBothGates();
            // #GatesOpen
            // sleep til Gates are open
            indiana.Gates.WaitForGateToOpen();

            indiana.ShootFirstThreeShotsFast(velocityPercentage, 1200);

            // needs this sleep or robot starts moving while taking last shot
            sleep(300);

            indiana.RotateLeft(17.5,0.4,100);

            indiana.MoveStraight(-180, .6, 200);
            indiana.Launchers.TurnPowerOff();

            sleep(18000-startWaitTime*1000);

            indiana.StrafeRight(500, .6, 200);

            indiana.EndAuton();
        }
    }


}