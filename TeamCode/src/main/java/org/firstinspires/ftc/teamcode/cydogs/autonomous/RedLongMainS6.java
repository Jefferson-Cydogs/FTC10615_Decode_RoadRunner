package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaAuton;
import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaAutonRed;
import org.firstinspires.ftc.teamcode.cydogs.components.LaunchersWithVelocity;
import org.firstinspires.ftc.teamcode.cydogs.core.EventTracker;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station

@Autonomous(name= "Red Long S6", group= "Autonomous", preselectTeleOp = "Cool People TeleOp")
public class RedLongMainS6 extends LinearOpMode {

    // declare variables
    private double velocityPercentage = 0.66;

    private IndianaAutonRed indiana;

    private ElapsedTime currentTimer;
    private EventTracker eventTracker;

    private boolean debugCode = false;

    @Override
    public void runOpMode() {
        // load 2 purple on left side
        // load 1 green on right side by launcher


        indiana = new IndianaAutonRed(this);
        indiana.InitializeAuton();

        currentTimer = new ElapsedTime();
        eventTracker = new EventTracker();

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

            indiana.ShootFirstThreeShotsFast(velocityPercentage, 600);

            // needs this sleep or robot starts moving while taking last shot
            sleep(300);

            indiana.MoveStraight(525,0.5,200);

            indiana.Gates.CloseBothGates();

            indiana.RotateRight(47.2, .4, 200);

            GetGreenPurplePurple();

            indiana.MoveStraight(-750, .4, 200);

            indiana.Intake.turnIntakeOff();


            indiana.ReverseFeeders(150);

            indiana.RotateLeft(45.5, 4, 200);

            velocityPercentage = 0.66;

            indiana.Launchers.RunAtVelocity(velocityPercentage);

            indiana.MoveStraight(-490, .5, 200);

            // need to open gates
            indiana.Gates.OpenBothGates();
            // #GatesOpen
            // sleep til Gates are open
            indiana.Gates.WaitForGateToOpen();

            indiana.ShootSecondThreeShotsFast(velocityPercentage, 600);


            indiana.MoveStraight(100, .6, 200);
            indiana.StrafeRight(350, .6, 200);

            indiana.EndAuton();
        }
    }

    private void GetGreenPurplePurple()
    {
        // Get Green
        // #Intake ON
        indiana.Intake.turnIntakeOn();

        // #LeftFeeder ON
        indiana.Feeders.ActivateRightBumper();

        indiana.MoveStraight(350, .25, 400);
        //indiana.MoveStraight(170, .3, 200);

        // Get Purples
        // #RightFeeder ON
        indiana.Feeders.ActivateLeftBumper();

        indiana.StrafeRight(120,.42, 300);

        indiana.MoveStraight(390, .42, 200);

        // #LeftFeeder OFF
        indiana.Feeders.DeactivateRightBumper();

        // #RightFeeder OFF
        indiana.Feeders.DeactivateLeftBumper();

    }


}