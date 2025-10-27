package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaChassis;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED;
import org.firstinspires.ftc.teamcode.cydogs.components.Feeders;
import org.firstinspires.ftc.teamcode.cydogs.components.Launchers;
import org.firstinspires.ftc.teamcode.cydogs.learning.AprilTagWheelie;

public class Red_Basket_short_side_Indiana {// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.

    //    @Autonomous puts this code in the autonomous category on driver station
    @Autonomous(name = "Red_Basket_short_side_Indiana", group = "Autonomous")
    public class Red_side_short_Basket_Indiana extends LinearOpMode {

        /* declare variables

         */
        private String currentMotif;

        private AprilTagWheelie IndianaTag;

        @Override
        public void runOpMode() {


            // Put code that should run during initialization HERE in this area
            IndianaChassis wheels = new IndianaChassis(this);
            wheels.InitializeAutonomous();
            Launchers shooter = new Launchers(this);
            shooter.initLauncher();
            Feeders pusher = new Feeders(this);
            ColorLED light = new ColorLED(this, "LauncherLED");
            // Wait for the start button to be pressed on the driver station
            waitForStart();

            if (opModeIsActive()) {
                light.SetColor(0.91);
                //    sleep(100);
                //purple (0.91)
                wheels.MoveStraight(1800, 0.55, 100);
                wheels.RotateRight(95, 0.55, 500);
                //This is where we would scan the obelisk
                // currentMotif = wheelieTag.telemetryAprilTag();
                telemetry.addData("Found Motif: ", currentMotif);
                telemetry.update();
                sleep(1000);
                wheels.RotateRight(95, 0.55, 500);
                wheels.MoveStraight(1567, 0.55, 150);
                shooter.runAtPower(0.7);
                sleep(3530);
                pusher.MoveLeftBumper();
                sleep(2300);
                pusher.MoveLeftBumper();
                sleep(2300);
                pusher.MoveRightBumper();
                sleep(2000);
                shooter.turnPowerOff();
                wheels.MoveStraight(-700, 0.55, 100);
                wheels.RotateRight(150, 0.55, 100);
                wheels.MoveStraight(1800, .55, 100);
                //  wheels.StrafeRight(1600,.55,100);
            }

        }

    }

}