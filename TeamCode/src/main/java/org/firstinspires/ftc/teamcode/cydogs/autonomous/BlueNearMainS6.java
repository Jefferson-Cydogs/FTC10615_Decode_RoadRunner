package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaAuton;
import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaAutonBlue;
import org.firstinspires.ftc.teamcode.cydogs.components.LaunchersWithVelocity;
import org.firstinspires.ftc.teamcode.cydogs.core.EventTracker;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station
@Autonomous(name= "Blue Near S6", group= "Autonomous", preselectTeleOp = "Cool People TeleOp")
public class BlueNearMainS6 extends LinearOpMode {


    private double velocityPercentage = 0.528;

    private IndianaAutonBlue indiana;
    private ElapsedTime currentTimer;
    private EventTracker eventTracker;




    @Override
    public void runOpMode() {
        // load 2 purple on left side
        // load 1 green on right side by launcher


         indiana = new IndianaAutonBlue(this);
         indiana.InitializeAuton();
        currentTimer = new ElapsedTime();
        eventTracker = new EventTracker();


        indiana.ColorLEDForAlliance("near");


        int startWaitTime = indiana.AskStartWaitTime();

        // Wait for the start button to be pressed on the driver station
        waitForStart();

        if (opModeIsActive()) {
            sleep(startWaitTime*1000);


            indiana.BlueCommonStart(velocityPercentage, 400);

            // Go shoot

            // launchers already on but adjust shot velocity
            double lastShotVelocity = 0.49;
            indiana.Launchers.RunAtVelocity(lastShotVelocity);

            // was .6 and .5
            indiana.StrafeRight(1100, .6, 50);

            // reduce pressure on gates before shooting
            indiana.ReverseFeeders(150);

            indiana.RotateRight(11,.6,100);

            // Would be nice to correct to aprilTag
            //indiana.TagReader.turnToFaceAprilTagAuton(indiana,indiana.Alliance, -8,.3, 3, currentTimer, eventTracker);

            indiana.Gates.OpenBothGates();
            // sleep til gates are open
            indiana.Gates.WaitForGateToOpen();
            indiana.ShootSecondThreeShotsFast(lastShotVelocity, 400);
            // must wait to finish shots
            sleep(200);

            indiana.EndAuton();


        }

    }












}