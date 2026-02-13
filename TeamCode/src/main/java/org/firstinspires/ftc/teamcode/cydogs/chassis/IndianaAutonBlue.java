package org.firstinspires.ftc.teamcode.cydogs.chassis;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class IndianaAutonBlue extends IndianaAuton {

    public IndianaAutonBlue(LinearOpMode currentOp) {
        this(currentOp,false);;
    }
    public IndianaAutonBlue(LinearOpMode currentOp, boolean setDebugMode)  {
        super(currentOp, "blue", setDebugMode);
    }

    public void BlueNearOpeningFlourish()
    {
        // was .55
        MoveStraight(1100, 0.6, 100);
        RotateLeft(83, 0.6, 100);

        // was 150
        myOpMode.sleep(100);
        GetMotif();
        ColorLEDForMotif();
    }


    public void BlueCommonStart(double velocityPercentage, int preShotWait)
    {

        // this clears bumper servo bug
        Feeders.MoveBumpersToFixBug();

        //moved earlier
        // #LauncherON
        Launchers.RunAtVelocity(velocityPercentage);

        BlueNearOpeningFlourish();

        // reduce pressure on gates before shooting
        ReverseFeeders(150);

        // was .55.  was 47 degrees
        RotateLeft(48, 0.55, 100);

        // need to open gates
        Gates.OpenBothGates();
        // #GatesOpen
        // sleep til Gates are open
        Gates.WaitForGateToOpen();


        ShootFirstThreeShotsFast(velocityPercentage, preShotWait);

        // need to sleep so it doesn't move while shooting
        myOpMode.sleep(200);
        // TO ADD
        // Check to see if color sensors see anything, if so, run
        // ejection code


        // was .5
        RotateLeft(24,.55,100);



        // #GatesClosed
        // was .45
        StrafeLeft(190, .5, 100);
        Gates.CloseBothGates();

        BlueAllianceNearPurplePurpleGreen();
        // #Intake ON

        // was .5
        MoveStraight(-800, .6, 100);
        Feeders.DeactivateRightBumper();

        // #Intake OFF
        Intake.turnIntakeOff();
    }



    private void BlueAllianceNearPurplePurpleGreen()
    {
        // Get purple
        // #Intake ON
        Intake.turnIntakeOn();

        // #LeftFeeder ON
        Feeders.ActivateLeftBumper();

        MoveStraight(700, .3, 100);
        //  indiana.MoveStraight(425, .4, 100);
        //  indiana.MoveStraight(320, .2, 300);

        // #LeftFeeder OFF
        Feeders.DeactivateLeftBumper();

        // Get Green
        // #RightFeeder ON
        Feeders.ActivateRightBumper();

        StrafeLeft(117,.4, 100);

        MoveStraight(230, .4, 100);

        // #LeftFeeder OFF
        Feeders.DeactivateLeftBumper();


        // #RightFeeder OFF


    }
}
