package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaAutonRed;
import org.firstinspires.ftc.teamcode.cydogs.core.EventTracker;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station
@Autonomous(name= "Red Near S9", group= "Autonomous", preselectTeleOp = "Cool People TeleOp")
public class RedNearMainS9 extends LinearOpMode {


    private double velocityPercentage = 0.50;

    private IndianaAutonRed indiana;
    private ElapsedTime currentTimer;
    private EventTracker eventTracker;




    @Override
    public void runOpMode() {
        // load 2 purple on left side
        // load 1 green on right side by launcher


         indiana = new IndianaAutonRed(this);
         indiana.InitializeAuton();
        currentTimer = new ElapsedTime();
        eventTracker = new EventTracker();


        indiana.ColorLEDForAlliance("near");


        int startWaitTime = indiana.AskStartWaitTime();

        // Wait for the start button to be pressed on the driver station
        waitForStart();

        if (opModeIsActive()) {
            sleep(startWaitTime*1000);


            indiana.RedCommonStart(velocityPercentage, 150);

            // Go shoot

            // launchers already on but adjust shot velocity
            double lastShotVelocity = 0.48;
            indiana.Launchers.RunAtVelocity(lastShotVelocity);

            // was .6 and .5
            indiana.StrafeLeft(400, .6, 50);

            // reduce pressure on gates before shooting
            indiana.ReverseFeeders(150);

            indiana.RotateLeft(22,.5,100);

            indiana.Gates.OpenBothGates();
            // sleep til gates are open
            indiana.Gates.WaitForGateToOpen();
            indiana.ShootSecondThreeShotsFast(lastShotVelocity, 100);
            sleep(200);
            // begin run for last 3

            indiana.RotateRight(25, .4, 100);
            indiana.Gates.CloseBothGates();
            lastShotVelocity = 0.475;
            indiana.Launchers.RunAtVelocity(lastShotVelocity);

            indiana.StrafeRight(980, .55, 100);

            // we don't care what color the artifacts are at this point
            GetLastThreeArtifacts();

            indiana.MoveStraight(-650, .8, 50);
            indiana.Feeders.DeactivateRightBumper();

            // #Intake OFF
            indiana.Intake.turnIntakeOff();

            indiana.StrafeLeft(1405, .8, 50);

            // reduce pressure on gates before shooting
            indiana.ReverseFeeders(100);

            indiana.RotateLeft(21,.6,0);

            indiana.Gates.OpenBothGates();
            // sleep til gates are open
            indiana.Gates.WaitForGateToOpen();

            indiana.ShootThirdThreeShotsFast(lastShotVelocity, 50);

            // need to sleep to finish shots
            sleep(200);
            indiana.EndAuton();


        }

    }





    private void GetLastThreeArtifacts()
    {
        // Get purple
        // #Intake ON
        indiana.Intake.turnIntakeOn();

        // #LeftFeeder ON
        indiana.Feeders.ActivateRightBumper();

        indiana.MoveStraight(577, .3, 300);
        //  indiana.MoveStraight(425, .4, 100);
        //  indiana.MoveStraight(320, .2, 300);

        // #LeftFeeder OFF
        indiana.Feeders.DeactivateRightBumper();

        // Get Green
        // #RightFeeder ON
        indiana.Feeders.ActivateLeftBumper();

        indiana.StrafeRight(122,.4, 100);

        indiana.MoveStraight(225, .3, 200);

        // #LeftFeeder OFF
        indiana.Feeders.DeactivateLeftBumper();


        // #RightFeeder OFF


    }





}