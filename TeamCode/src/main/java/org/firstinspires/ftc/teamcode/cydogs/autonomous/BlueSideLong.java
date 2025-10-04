package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.Feeder;
import org.firstinspires.ftc.teamcode.cydogs.Launcher;
import org.firstinspires.ftc.teamcode.cydogs.chassis.WheelieChassis;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station
@Disabled
@Autonomous
public class BlueSideLong extends LinearOpMode {

    /* declare variables

     */
    @Override
    public void runOpMode() {

        // Put code that should run during initialization HERE in this area
WheelieChassis Chassis = new WheelieChassis(this);
Chassis.ResetWheelConfig();
        Feeder BothFeeders = new Feeder(this);
        Launcher launcher = new Launcher(this);

        // Wait for the start button to be pressed on the driver station
        waitForStart();

        if (opModeIsActive()) {
            // Put code that should run during the active mode HERE in this area
// I'm assuming that I am starting facing the obelisk with the motif pattern
            Chassis.MoveStraight(1820,0.5,100);
            Chassis.RotateLeft(47.0,0.5,100);
            Chassis.MoveStraight(610,0.5,100);
            launcher.runAtPower(0.5);
            BothFeeders.MoveFeeder();
            launcher.turnPowerOff();
            Chassis.RotateLeft(130,0.5,100);
            Chassis.MoveStraight(2370,0.5,100);
            Chassis.StrafeRight(770,0.5,100);
        }
    }


}



