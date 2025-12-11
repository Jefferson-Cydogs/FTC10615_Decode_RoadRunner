package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaAuton;
import org.firstinspires.ftc.teamcode.cydogs.components.LaunchersWithVelocity;
import org.firstinspires.ftc.teamcode.cydogs.core.EventTracker;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station
@Autonomous(name= "Blue Near S9", group= "Autonomous", preselectTeleOp = "Cool People TeleOp")
public class BlueNearMainS9 extends LinearOpMode {


    private double velocityPercentage = 0.50;

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


        indiana.ColorLEDForAlliance("near");


        int startWaitTime = indiana.AskStartWaitTime();

        // Wait for the start button to be pressed on the driver station
        waitForStart();

        if (opModeIsActive()) {
            sleep(startWaitTime*1000);


            indiana.BlueCommonStart(velocityPercentage, 100);

            // Go shoot

            // launchers already on but adjust shot velocity
            double lastShotVelocity = 0.495;
            indiana.Launchers.RunAtVelocity(lastShotVelocity);

            // was .6 and .5
            indiana.StrafeRight(400, .7, 50);

            // reduce pressure on gates before shooting
            indiana.ReverseFeeders(150);

            indiana.RotateRight(27,.5,100);

            indiana.Gates.OpenBothGates();
            // sleep til gates are open
            indiana.Gates.WaitForGateToOpen();
            indiana.ShootSecondThreeShotsFast(lastShotVelocity, 100);
            sleep(200);
            // begin run for last 3

            indiana.RotateLeft(27, .5, 100);
            indiana.Gates.CloseBothGates();
            lastShotVelocity = 0.475;
            indiana.Launchers.RunAtVelocity(lastShotVelocity);

            // was 820
            indiana.StrafeLeft(790, .7, 100);

            // we don't care what color the artifacts are at this point
            GetLastThreeArtifacts();

            indiana.MoveStraight(-770, .7, 50);
            indiana.Feeders.DeactivateRightBumper();

            // #Intake OFF
            indiana.Intake.turnIntakeOff();

            indiana.StrafeRight(1500, .8, 50);

            // reduce pressure on gates before shooting
            indiana.ReverseFeeders(150);

            indiana.RotateRight(18,.5,100);

            indiana.Gates.OpenBothGates();
            // sleep til gates are open
            indiana.Gates.WaitForGateToOpen();

            indiana.ShootThirdThreeShotsFast(lastShotVelocity, 100);

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
        indiana.Feeders.ActivateLeftBumper();

        indiana.MoveStraight(690, .3, 300);
        //  indiana.MoveStraight(425, .4, 100);
        //  indiana.MoveStraight(320, .2, 300);

        // #LeftFeeder OFF
        indiana.Feeders.DeactivateLeftBumper();

        // Get Green
        // #RightFeeder ON
        indiana.Feeders.ActivateRightBumper();

        indiana.StrafeLeft(122,.4, 100);

        indiana.MoveStraight(260, .3, 200);

        // #LeftFeeder OFF
        indiana.Feeders.DeactivateRightBumper();


        // #RightFeeder OFF


    }





}