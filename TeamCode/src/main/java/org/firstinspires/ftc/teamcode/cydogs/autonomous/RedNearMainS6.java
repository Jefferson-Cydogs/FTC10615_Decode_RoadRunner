package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaAutonRed;
import org.firstinspires.ftc.teamcode.cydogs.core.EventTracker;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station
@Autonomous(name= "Red Near S6", group= "Autonomous", preselectTeleOp = "Cool People TeleOp")
public class RedNearMainS6 extends LinearOpMode {


    private double velocityPercentage = 0.525;

    private IndianaAutonRed indiana;




    @Override
    public void runOpMode() {
        // load 2 purple on left side
        // load 1 green on right side by launcher


         indiana = new IndianaAutonRed(this);
         indiana.InitializeAuton();


        indiana.ColorLEDForAlliance("near");


        // this has to be last line of code before wait for start
        int startWaitTime = indiana.AskStartWaitTime();

        // Wait for the start button to be pressed on the driver station
        waitForStart();

        if (opModeIsActive()) {
            sleep(startWaitTime*1000);

            indiana.RedCommonStart(velocityPercentage, 400);

            // launchers already on but adjust shot velocity
            double lastShotVelocity = 0.494;
            indiana.Launchers.RunAtVelocity(lastShotVelocity);

            // was .6 and .5
            indiana.StrafeLeft(1000, .6, 50);

            // reduce pressure on gates before shooting
            indiana.ReverseFeeders(150);

            indiana.RotateLeft(11,.6,100);


            indiana.Gates.OpenBothGates();
            // sleep til gates are open
            indiana.Gates.WaitForGateToOpen();
            indiana.ShootSecondThreeShotsFast(lastShotVelocity, 400);

            indiana.EndAuton();

        }

    }











}