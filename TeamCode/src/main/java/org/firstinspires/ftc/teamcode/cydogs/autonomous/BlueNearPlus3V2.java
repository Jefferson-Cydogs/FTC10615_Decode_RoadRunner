package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaAuton;
import org.firstinspires.ftc.teamcode.cydogs.core.EventTracker;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station
@Autonomous(name= "Blue Near Plus 3 V2", group= "Autonomous", preselectTeleOp = "Cool People Blue")
public class BlueNearPlus3V2 extends LinearOpMode {


    private double velocityPercentage = 0.41;

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

            indiana.BlueNearOpeningFlourish();

            indiana.Launchers.RunAtVelocity(velocityPercentage);
            indiana.RotateLeft(48, 0.55, 200);


            indiana.ShootThreeShots(velocityPercentage);

            //indiana.Launchers.TurnPowerOff();
            indiana.Launchers.RunAtVelocity(0.2);
            sleep(150);
            //indiana.Launchers.RunAtVelocity(0.2);
            //sleep(100);
            indiana.Launchers.RunAtVelocity(0.09);
            indiana.Launchers.StopLaunchersSafely();
            //sleep(50);


            indiana.RotateLeft(29,.5,100);
            indiana.StrafeLeft(203, .45, 100);

            // Get purple
            indiana.Intakes.turnLeftIntakeon();
            indiana.Feeders.ActivateLeftBumper();
            indiana.MoveStraight(425, .4, 100);
            indiana.MoveStraight(340, .08, 300);
            indiana.Feeders.DeactivateLeftBumper();
            indiana.Feeders.ReverseLeftBumper();

            // Get Green
            indiana.Intakes.turnRightIntakeon();
            indiana.Feeders.ActivateRightBumper();
            indiana.StrafeLeft(127,.4, 100);
            indiana.MoveStraight(275, .15, 200);
            indiana.Feeders.DeactivateLeftBumper();
            indiana.Intakes.turnleftintakeoff();
            indiana.Feeders.DeactivateRightBumper();


            // Got green, back up
            indiana.Launchers.ResetLaunchersToFloat();

            indiana.Launchers.RunAtVelocity(-.15);
            //indiana.Feeders.ReverseRightBumper();



            indiana.MoveStraight(-700, .5, 100);
            //indiana.Feeders.DeactivateRightBumper();
            indiana.Intakes.turnrightintakeoff();
            indiana.Launchers.TurnPowerOff();
            sleep(50);

            // Go shoot
            indiana.Launchers.RunAtVelocity(.4);
            //indiana.RotateRight(32, .5, 100);
            indiana.StrafeRight(820, .6, 100);

            //indiana.MoveStraight(350, .8, 0);
            //indiana.StrafeRight(400, .8, 200);

            indiana.TagReader.turnToFaceAprilTagAuton(indiana,indiana.Alliance, -8,.3, 3, currentTimer, eventTracker);
            indiana.ShootThreeShotsSecondTime(velocityPercentage);
            indiana.Launchers.TurnPowerOff();

            indiana.ColorLEDForAlliance();
            sleep(2000);




        }

    }












}