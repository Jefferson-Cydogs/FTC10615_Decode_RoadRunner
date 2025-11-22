package org.firstinspires.ftc.teamcode.cydogs.z_archive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaAuton;
import org.firstinspires.ftc.teamcode.cydogs.core.EventTracker;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station

@Autonomous(name= "Red Long Simple", group= "Autonomous", preselectTeleOp = "Cool People Blue")
@Disabled
public class RedLongSimple extends LinearOpMode {

    // declare variables
    private double velocityPercentage = 0.52;

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

            indiana.RotateRight(16,0.5,100);

            indiana.Launchers.RunAtVelocity(velocityPercentage);

            indiana.ShootThreeShots(velocityPercentage);

            indiana.Launchers.TurnPowerOff();

            indiana.MoveStraight(400,0.5,100);
            indiana.ColorLEDForAlliance();
            sleep(2000);
        }
    }


}