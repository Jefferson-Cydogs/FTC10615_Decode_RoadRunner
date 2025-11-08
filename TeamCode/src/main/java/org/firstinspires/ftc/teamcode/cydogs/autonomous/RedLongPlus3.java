package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaAuton;
import org.firstinspires.ftc.teamcode.cydogs.components.LaunchersWithVelocity;
import org.firstinspires.ftc.teamcode.cydogs.core.EventTracker;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station

@Autonomous(name= "Red Long Plus 3", group= "Autonomous", preselectTeleOp = "Cool People Blue")
public class RedLongPlus3 extends LinearOpMode {

    // declare variables
    private double velocityPercentage = 0.519;

    private IndianaAuton indiana;

    private ElapsedTime currentTimer;
    private EventTracker eventTracker;

    @Override
    public void runOpMode() {
        // load 2 purple on left side
        // load 1 green on right side by launcher


        indiana = new IndianaAuton(this, "red");
        indiana.InitializeAuton();

        currentTimer = new ElapsedTime();
        eventTracker = new EventTracker();
        int startWaitTime = indiana.AskStartWaitTime();

        waitForStart();

        if (opModeIsActive()) {
            sleep(startWaitTime*1000);


            // this clears bumper servo bug
            indiana.Feeders.MoveBumpersToFixBug();

            indiana.MoveStraight(200,0.5,100);
            sleep(500);
            indiana.GetMotif();
            indiana.ColorLEDForMotif();

            sleep(200);
            indiana.ReverseFeeders(400);

            indiana.RotateRight(16,0.5,100);

            indiana.Launchers.RunAtVelocity(velocityPercentage);

            indiana.ShootThreeShots(velocityPercentage);

            indiana.Launchers.TurnPowerOff();
            indiana.EjectAllArtifacts(2000);

            indiana.MoveStraight(460,0.5,400);
            LaunchersWithVelocity.LauncherDecelerator.decelerateAsync(indiana.Launchers.Launchers, 0.5,0.02,50);
            indiana.RotateRight(48, .3, 400);

            GetGreenPurplePurple();

            indiana.MoveStraight(-750, .5, 400);
            indiana.Intake.turnIntakeOff();

            indiana.Launchers.RunAtVelocity(-.15);
            indiana.ReverseFeeders(500);
            indiana.Launchers.TurnPowerOff();
            //sleep(500);

            indiana.RotateLeft(46, .3, 400);

            velocityPercentage = 0.52;

            indiana.MoveStraight(-450, .4, 400);
            indiana.Launchers.RunAtVelocity(velocityPercentage);
            sleep(800);
            indiana.ShootThreeShotsSecondTime(velocityPercentage);

            indiana.MoveStraight(500, .6, 400);

            indiana.ColorLEDForAlliance();
            sleep(2000);
        }
    }

    private void GetGreenPurplePurple()
    {
        // Get Green
        // #Intake ON
        indiana.Intake.turnIntakeOn();

        // #LeftFeeder ON
        indiana.Feeders.ActivateRightBumper();
        indiana.MoveStraight(400, .3, 400);
        //indiana.MoveStraight(170, .3, 200);

        // Get Purples
        // #RightFeeder ON
        indiana.Feeders.ActivateLeftBumper();
        indiana.StrafeRight(80,.4, 400);
        indiana.MoveStraight(370, .12, 400);

        // #LeftFeeder OFF
        indiana.Feeders.DeactivateRightBumper();

        // #RightFeeder OFF
        indiana.Feeders.DeactivateLeftBumper();

    }


}