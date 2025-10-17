package org.firstinspires.ftc.teamcode.cydogs.learning;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.chassis.WheelieChassis;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station
@Autonomous(name= "Blue_Basket_sample", group= "Autonomous")
public class Blue_Basket_sample extends LinearOpMode {

    /* declare variables

     */
    private String currentMotif;

    private AprilTagWheelie wheelieTag;
    @Override
    public void runOpMode() {



                // Put code that should run during initialization HERE in this area
                WheelieChassis wheels = new WheelieChassis(this);
                wheels.ResetWheelConfig();
                WheelieLauncher shooter = new WheelieLauncher(this);
                shooter.initLauncher();
                WheelieFeeder pusher = new WheelieFeeder(this);
                ColorLED light = new ColorLED(this,"");
        //wheelieTag = new AprilTagWheelie(this);
        wheelieTag.initAprilTag();
                // Wait for the start button to be pressed on the driver station
                waitForStart();

                if (opModeIsActive()) {
                    light.SetColor(0.91);
                    //    sleep(100);
                    //purple (0.91)
                    wheels.MoveStraight(1800, 0.55, 100);
                    wheels.RotateRight(50, 0.55, 500);
                    //This is where we would scan the obelisk
                    currentMotif = wheelieTag.telemetryAprilTag();
                    telemetry.addData("Found Motif: ", currentMotif);
                    telemetry.update();
                    sleep(1000);
                    wheels.RotateLeft(50, 0.55, 500);
                    wheels.MoveStraight(-1567, 0.55, 150);
                    shooter.runAtPower(0.6);
                    sleep(3530);
                    pusher.MoveFeeder();
                    sleep(2300);
                    pusher.MoveFeeder();
                    sleep(2300);
                    pusher.MoveFeeder();
                    sleep(2000);
                    shooter.turnPowerOff();
                    wheels.MoveStraight(700, 0.55, 100);
                    wheels.RotateLeft(125, 0.55, 100);
                    wheels.MoveStraight(-1800, .55, 100);
                    //  wheels.StrafeRight(1600,.55,100);


                }


    }

}