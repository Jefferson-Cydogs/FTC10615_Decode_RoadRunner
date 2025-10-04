package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.acmerobotics.dashboard.message.redux.StopOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.LED;

import org.firstinspires.ftc.teamcode.cydogs.ColorLight;
import org.firstinspires.ftc.teamcode.cydogs.Feeder;
import org.firstinspires.ftc.teamcode.cydogs.Launcher;
import org.firstinspires.ftc.teamcode.cydogs.chassis.WheelieChassis;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station
@Disabled
@Autonomous
public class BlueBasket extends LinearOpMode {

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
        ColorLight light = new ColorLight(this);
        // Wait for the start button to be pressed on the driver station
        waitForStart();

        if (opModeIsActive()) {
            light.SetColor(0.9167, 100);
            wheels.MoveStraight(2070, 0.55, 100);
            wheels.RotateLeft(135, 0.55, 500);
            //This is where we would scan the obelisk
            wheels.RotateLeft(40,0.55,500);
            wheels.MoveStraight(1075, 0.55, 100);
            shooter.runAtPower(0.4);
            pusher.MoveFeeder();
            shooter.turnPowerOff();
            wheels.MoveStraight(-1075,0.55,100);
            wheels.RotateRight(45, 0.55, 100);
            wheels.MoveStraight(-1640, .55,100);
            wheels.StrafeLeft(1600,.55,100);
            // Put code that should run during the active mode HERE in this area

        }
    }

    }






