package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaAuton;
import org.firstinspires.ftc.teamcode.cydogs.core.EventTracker;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station
@Autonomous(name= "Blue Near S6", group= "Autonomous", preselectTeleOp = "Cool People Blue")
public class BlueNearMainS6 extends LinearOpMode {


    private double velocityPercentage = 0.534;

    private IndianaAuton indiana;
    private ElapsedTime currentTimer;
    private EventTracker eventTracker;




    @Override
    public void runOpMode() {
        // load 2 purple on left side
        // load 1 green on right side by launcher


         indiana = new IndianaAuton(this, "blue");
         indiana.InitializeAuton();
        currentTimer = new ElapsedTime();
        eventTracker = new EventTracker();
        int startWaitTime = indiana.AskStartWaitTime();

        // Wait for the start button to be pressed on the driver station
        waitForStart();

        if (opModeIsActive()) {
            sleep(startWaitTime*1000);



            // this clears bumper servo bug
            indiana.Feeders.MoveBumpersToFixBug();

            //moved earlier
    // #LauncherON
            indiana.Launchers.RunAtVelocity(velocityPercentage);

            indiana.BlueNearOpeningFlourish();

            // reduce pressure on gates before shooting
            indiana.ReverseFeeders(150);

                    // was .55.  was 47 degrees
            indiana.RotateLeft(46, 0.55, 100);

            // need to open gates
            indiana.Gates.OpenBothGates();
    // #GatesOpen
            // sleep til Gates are open
            indiana.Gates.WaitForGateToOpen();


            indiana.ShootFirstThreeShotsFast(velocityPercentage);


            // TO ADD
                // Check to see if color sensors see anything, if so, run
                // ejection code


                    // was .5
            indiana.RotateLeft(26,.55,100);



    // #GatesClosed
                            // was .45
            indiana.StrafeLeft(210, .5, 100);
            indiana.Gates.CloseBothGates();

            GetPurplePurpleGreen();
    // #Intake ON

                    // was .5
            indiana.MoveStraight(-900, .6, 100);
            indiana.Feeders.DeactivateRightBumper();

    // #Intake OFF
            indiana.Intake.turnIntakeOff();

            // Go shoot

            // launchers already on but adjust shot velocity
            double lastShotVelocity = 0.49;
            indiana.Launchers.RunAtVelocity(lastShotVelocity);

            // was .6 and .5
            indiana.StrafeRight(1100, .6, 50);

            // reduce pressure on gates before shooting
            indiana.ReverseFeeders(150);

            indiana.RotateRight(13,.6,100);

            // Would be nice to correct to aprilTag
            //indiana.TagReader.turnToFaceAprilTagAuton(indiana,indiana.Alliance, -8,.3, 3, currentTimer, eventTracker);

            indiana.Gates.OpenBothGates();
            // sleep til gates are open
            indiana.Gates.WaitForGateToOpen();
            indiana.ShootSecondThreeShotsFast(lastShotVelocity);

    // #Launchers OFF
            indiana.Launchers.TurnPowerOff();

            // Close both gates to get ready for telop

            indiana.Gates.CloseBothGates();

            indiana.ColorLEDForAlliance();
            sleep(2000);


        }

    }


    private void GetPurplePurpleGreen()
    {
        // Get purple
        // #Intake ON
        indiana.Intake.turnIntakeOn();

        // #LeftFeeder ON
        indiana.Feeders.ActivateLeftBumper();

        indiana.MoveStraight(705, .3, 300);
      //  indiana.MoveStraight(425, .4, 100);
      //  indiana.MoveStraight(320, .2, 300);

        // #LeftFeeder OFF
        indiana.Feeders.DeactivateLeftBumper();

        // Get Green
        // #RightFeeder ON
        indiana.Feeders.ActivateRightBumper();

        indiana.StrafeLeft(117,.4, 100);

        indiana.MoveStraight(260, .3, 200);

        // #LeftFeeder OFF
        indiana.Feeders.DeactivateLeftBumper();


        // #RightFeeder OFF


    }









}