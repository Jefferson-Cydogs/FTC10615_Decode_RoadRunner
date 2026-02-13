package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaAuton;
import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaAutonRed;
import org.firstinspires.ftc.teamcode.cydogs.components.LaunchersWithVelocity;
import org.firstinspires.ftc.teamcode.cydogs.core.EventTracker;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station

@Autonomous(name= "Red Long S5", group= "Autonomous", preselectTeleOp = "Cool People TeleOp")
public class RedLongMainS5 extends LinearOpMode {

    // declare variables
    private double velocityPercentage = 0.655;

    private IndianaAutonRed indiana;

    private ElapsedTime currentTimer;
    private EventTracker eventTracker;

    private boolean debugCode = true;

    @Override
    public void runOpMode() {
        // load 2 purple on left side
        // load 1 green on right side by launcher


        indiana = new IndianaAutonRed(this);
        indiana.InitializeAuton();

        currentTimer = new ElapsedTime();
        eventTracker = new EventTracker();

        indiana.ColorLEDForAlliance("far");


        int startWaitTime = indiana.AskStartWaitTime();

        waitForStart();

        if (opModeIsActive()) {
            sleep(startWaitTime*1000);


            // this clears bumper servo bug
            indiana.Feeders.MoveBumpersToFixBug();

            indiana.Launchers.RunAtVelocity(velocityPercentage);

            indiana.MoveStraight(205,0.5,100);
            sleep(200);
            indiana.GetMotif();
            indiana.ColorLEDForMotif();

            indiana.ReverseFeeders(150);


            indiana.RotateRight(16,0.4,100);

            // need to open gates
            indiana.Gates.OpenBothGates();
            // #GatesOpen
            // sleep til Gates are open
            indiana.Gates.WaitForGateToOpen();

            indiana.ShootFirstThreeShotsFast(velocityPercentage, 800);

            // needs this sleep or robot starts moving while taking last shot
            sleep(300);
            LaunchersWithVelocity.LauncherDecelerator.decelerateAsync(indiana.Launchers.Launchers, 0.5,0.02,50);
            indiana.Gates.CloseBothGates();

            // go get wall side
            indiana.RotateRight(50, .5, 100);
            indiana.StrafeRight(150, .5, 100);
            indiana.Intake.turnIntakeOn();
            indiana.Feeders.ActivateLeftBumper();
            indiana.Feeders.ActivateRightBumper();
            CodeDebugger("a");
            indiana.MoveStraight(1180, .7, 500);
            CodeDebugger("b");
            CodeDebugger("d");
            sleep(2000);
            indiana.Feeders.DeactivateLeftBumper();
            CodeDebugger("e");
            indiana.Feeders.DeactivateRightBumper();
            CodeDebugger("f");
            indiana.Intake.turnIntakeOff();
            CodeDebugger("g");
            indiana.Launchers.RunAtVelocity(velocityPercentage);
            CodeDebugger("h");
            indiana.RotateLeft(10, .5, 100);
            indiana.MoveStraight(-1100, .5, 100);
            CodeDebugger("i");
            indiana.RotateLeft(63, .5, 100);
            CodeDebugger("j");
            indiana.MoveStraight(120,0.5,100);
            CodeDebugger("k");
            indiana.ReverseFeeders(150);
            indiana.RotateRight(13,0.5,100);
            // need to open gates
            indiana.Gates.OpenBothGates();
            // #GatesOpen
            // sleep til Gates are open
            indiana.Gates.WaitForGateToOpen();
            indiana.ShootAllFourSlots(velocityPercentage,800);



            indiana.RotateLeft(17,0.4,100);

            indiana.MoveStraight(-180, .6, 200);
            indiana.Launchers.TurnPowerOff();

            //sleep(Math.max(4000-startWaitTime*1000,0));
            indiana.ColorLEDsKITTSequence(Math.max(2-startWaitTime,0),"red");
                    
            indiana.StrafeRight(600, .6, 200);

            indiana.EndAuton();
        }
    }

    private void GetGreenPurplePurple()
    {
        // Get Green
        // #Intake ON
        indiana.Intake.turnIntakeOn();

        // #LeftFeeder ON
        indiana.Feeders.ActivateRightBumper();

        indiana.MoveStraight(390, .3, 400);
        //indiana.MoveStraight(170, .3, 200);

        // Get Purples
        // #RightFeeder ON
        indiana.Feeders.ActivateLeftBumper();

        indiana.StrafeRight(100, .4, 300);

        indiana.MoveStraight(370, .4, 200);

        // #LeftFeeder OFF
        indiana.Feeders.DeactivateRightBumper();

        // #RightFeeder OFF
        indiana.Feeders.DeactivateLeftBumper();

    }


}