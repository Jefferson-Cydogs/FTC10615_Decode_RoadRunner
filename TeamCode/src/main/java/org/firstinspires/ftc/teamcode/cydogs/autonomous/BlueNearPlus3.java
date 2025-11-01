package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaAuton;
import org.firstinspires.ftc.teamcode.cydogs.core.EventTracker;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station
@Autonomous(name= "Blue Near Plus 3", group= "Autonomous", preselectTeleOp = "Cool People Blue")
public class BlueNearPlus3 extends LinearOpMode {


    private double velocityPercentage = 0.39;

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

            indiana.LauncherLED.SetColor(1);

            // this clears bumper servo bug
            indiana.Feeders.MoveBumpers();

            indiana.MoveStraight(900, 0.55, 100);
            indiana.RotateLeft(80, 0.55, 1000);

            indiana.GetMotif();

            indiana.Launchers.RunAtVelocity(velocityPercentage);
            indiana.RotateLeft(50, 0.55, 500);


            indiana.ShootThreeShots(velocityPercentage);

            indiana.Launchers.TurnPowerOff();

            indiana.RotateLeft(32,.3,200);
            indiana.StrafeLeft(305, .3, 200);
            indiana.Intakes.turnLeftIntakeon();
            indiana.Feeders.ActivateLeftBumper();
            indiana.MoveStraight(250, .25, 1200);
            indiana.MoveStraight(339, .08, 300);
            indiana.Feeders.DeactivateLeftBumper();
            indiana.Intakes.turnleftintakeoff();

            indiana.Intakes.turnRightIntakeon();
            indiana.Feeders.ActivateRightBumper();
            indiana.StrafeLeft(127,.3, 200);
            indiana.MoveStraight(200, .15, 200);
            indiana.Feeders.DeactivateRightBumper();
            indiana.Intakes.turnrightintakeoff();

            indiana.MoveStraight(-500, .4, 200);
            indiana.Launchers.RunAtVelocity(velocityPercentage);
            indiana.RotateRight(32, .3, 200);
            indiana.StrafeRight(420, .4, 200);
            indiana.TagReader.turnToFaceAprilTagAuton(indiana,indiana.Alliance, .3, 3, currentTimer, eventTracker);
            indiana.ShootThreeShots(velocityPercentage);
            indiana.Launchers.TurnPowerOff();
            indiana.StrafeRight(400, .6, 200);
            sleep(2000);




        }

    }












}