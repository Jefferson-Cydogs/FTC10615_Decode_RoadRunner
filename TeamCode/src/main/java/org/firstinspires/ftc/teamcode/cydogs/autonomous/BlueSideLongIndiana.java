package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaChassis;
import org.firstinspires.ftc.teamcode.cydogs.chassis.WheelieChassis;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED;
import org.firstinspires.ftc.teamcode.cydogs.components.Feeders;
import org.firstinspires.ftc.teamcode.cydogs.components.Launchers;
import org.firstinspires.ftc.teamcode.cydogs.learning.AprilTagWheelie;
import org.firstinspires.ftc.teamcode.cydogs.learning.WheelieFeeder;
import org.firstinspires.ftc.teamcode.cydogs.learning.WheelieLauncher;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station

@Autonomous
public class BlueSideLongIndiana extends LinearOpMode {

    // declare variables
     private String currentMotif;
    private AprilTagWheelie IndianaTag;

    @Override
    public void runOpMode() {


        // Put code that should run during initialization HERE in this area
        IndianaChassis Chassis = new IndianaChassis(this);
        Feeders BothFeeders = new Feeders(this);
        Launchers launcher = new Launchers(this);
        ColorLED light = new ColorLED(this,"");
     //   wheelieTag = new AprilTagWheelie(this);
        IndianaTag.initAprilTag();
        // Wait for the start button to be pressed on the driver station
        waitForStart();

        if (opModeIsActive()) {
            light.SetColor(0.8);
            // Put code that should run during the active mode HERE in this area
            //I'm assuming that I am starting facing the obelisk with the motif pattern on the blue side*//
            Chassis.MoveStraight(2600,0.5,100);
            currentMotif = IndianaTag.telemetryAprilTag();
            telemetry.addData("Found Motif: ", currentMotif);
            telemetry.update();
            sleep(2000);
            Chassis.RotateLeft(45.0,0.5,100);
            Chassis.MoveStraight(1800,0.5,500);
            launcher.runAtPower(0.6);
            sleep(3530);
            BothFeeders.MoveBumper();
            sleep(2300);
            BothFeeders.MoveBumper();
            sleep(2300);
            BothFeeders.MoveBumper();
            sleep(2000);
            launcher.turnPowerOff();
            Chassis.MoveStraight(-850,0.5,100);
            Chassis.RotateLeft(135,0.5,100);
            Chassis.MoveStraight(1600,0.5,100);
        }
    }


}



