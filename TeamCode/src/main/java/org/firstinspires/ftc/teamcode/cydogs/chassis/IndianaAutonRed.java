package org.firstinspires.ftc.teamcode.cydogs.chassis;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class IndianaAutonRed extends IndianaAuton {

    public IndianaAutonRed(LinearOpMode currentOp)
    {
        super(currentOp, "red");
    }

    public void RedCommonStart(double velocityPercentage, int preShotWait)
    {

        // this clears bumper servo bug
        Feeders.MoveBumpersToFixBug();

        //moved earlier
        // #LauncherON
        Launchers.RunAtVelocity(velocityPercentage);

        RedNearOpeningFlourish();

        // reduce pressure on gates before shooting
        ReverseFeeders(150);

        // was .55.  was 47 degrees
        RotateRight(61, 0.55, 100);

        // need to open gates
        Gates.OpenBothGates();
        // #GatesOpen
        // sleep til Gates are open
        Gates.WaitForGateToOpen();


        ShootFirstThreeShotsFast(velocityPercentage, preShotWait);
        myOpMode.sleep(200);

        // was .5
        RotateRight(27,.5,100);

        // #GatesClosed
        // was .45
        StrafeRight(274, .5, 100);
        Gates.CloseBothGates();

        RedAllianceNearPurplePurpleGreen();
        // #Intake ON

        // was .5
        MoveStraight(-800, .6, 100);
        Feeders.DeactivateRightBumper();

        // #Intake OFF
        Intake.turnIntakeOff();
    }



    public void RedNearOpeningFlourish()
    {
        MoveStraight(1100, 0.6, 100);
        RotateRight(68, 0.6, 100);
        myOpMode.sleep(100);
        GetMotif();
        ColorLEDForMotif();
    }
}
