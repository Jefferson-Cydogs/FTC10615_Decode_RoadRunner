package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.ColorLight;
import org.firstinspires.ftc.teamcode.cydogs.Feeder;
import org.firstinspires.ftc.teamcode.cydogs.Launcher;
import org.firstinspires.ftc.teamcode.cydogs.chassis.WheelieChassis;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station
@Autonomous(name= "ComboTest", group= "Autonomous")
public class ComboTest extends LinearOpMode {

    /* declare variables

     */
    @Override
    public void runOpMode() {


        // Put code that should run during initialization HERE in this area
        WheelieChassis wheels = new WheelieChassis(this);
        wheels.ResetWheelConfig();
        Launcher shooter= new Launcher(this);
        shooter.initLauncher();
        Feeder pusher = new Feeder(this);
        ColorLight light = new ColorLight(this,"");



        // Wait for the start button to be pressed on the driver station
        waitForStart();

        if (opModeIsActive()) {
            light.SetColor(0.45, 100);
            wheels.MoveStraight(2600,0.5,100);
            wheels.RotateLeft(48.0,0.5,100);
            wheels.MoveStraight(1250,0.5,500);
            shooter.runAtPower(0.6);
            sleep(3530);
            pusher.MoveFeeder();
            sleep(2300);
            pusher.MoveFeeder();
            sleep(2300);
            pusher.MoveFeeder();
            sleep(2000);
            shooter.turnPowerOff();
            wheels.RotateLeft(180,0.5,100);
            wheels.MoveStraight(850,0.5,100);
            wheels.RotateRight(40,0.5,100);
            wheels.MoveStraight(1350,0.5,100);

        }

    }






}