package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaAuton;
import org.firstinspires.ftc.teamcode.cydogs.core.EventTracker;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station
@Autonomous(name= "Blue Near Simple", group= "Autonomous", preselectTeleOp = "Cool People Blue")
public class BlueNearSimple extends LinearOpMode {


    private double velocityPercentage = .402;

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

            // #LauncherON
            indiana.Launchers.RunAtVelocity(velocityPercentage);
            indiana.RotateLeft(47, 0.55, 200);


            indiana.ShootThreeShots(velocityPercentage);
            sleep(500);
            indiana.MoveStraight(400, .6, 100);
            indiana.StrafeRight(500,.6,100);

            indiana.ColorLEDForAlliance();
            sleep(2000);
        }

    }


}