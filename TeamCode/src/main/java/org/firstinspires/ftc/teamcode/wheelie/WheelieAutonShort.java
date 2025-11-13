package org.firstinspires.ftc.teamcode.wheelie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.chassis.WheelieChassis;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED;
import org.firstinspires.ftc.teamcode.cydogs.learning.AprilTagWheelie;
import org.firstinspires.ftc.teamcode.cydogs.learning.WheelieFeeder;
import org.firstinspires.ftc.teamcode.cydogs.learning.WheelieLauncher;



@Autonomous(name= "WheelieAutonShort", group= "Autonomous")

public class WheelieAutonShort extends LinearOpMode {


    private String currentMotif;

    private AprilTagWheelie wheelieTag;
    @Override
    public void runOpMode() {




        WheelieChassis wheels = new WheelieChassis(this);
        wheels.ResetWheelConfig();
        WheelieLauncher shooter = new WheelieLauncher(this);
        shooter.initLauncher();
        WheelieFeeder pusher = new WheelieFeeder(this);
        ColorLED light = new ColorLED(this,"");

        wheelieTag.initAprilTag();

        waitForStart();

        if (opModeIsActive()) {
            light.SetColor(0.67);
        }
          wheels.MoveStraight(1700, 0.5, 100);
          wheels.RotateRight(60,0.5, 500);
        currentMotif = wheelieTag.telemetryAprilTag();
        telemetry.addData("Found Motif: ", currentMotif);
        telemetry.update();
        sleep(1000);
        wheels.RotateLeft(40, 0.5, 500);
        wheels.MoveStraight(1075, 0.5, 100);
        shooter.runAtPower(0.6);
        sleep(3530);
        pusher.MoveFeeder();
        sleep(2300);
        pusher.MoveFeeder();
        sleep(2300);
        pusher.MoveFeeder();
        sleep(2000);
        shooter.turnPowerOff();
        wheels.MoveStraight(700,0.5,100);
        wheels.RotateLeft(125, 0.5, 500);
        wheels.MoveStraight(1000, 0.5, 100);

    }}