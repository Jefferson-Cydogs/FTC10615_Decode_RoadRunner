package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaAuton;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station

@Autonomous(name= "Blue Long Simple", group= "Autonomous", preselectTeleOp = "Cool People Blue")
public class BlueLongSimple extends LinearOpMode {

    // declare variables
    private double velocityPercentage = 0.39;

    private IndianaAuton indiana;

    @Override
    public void runOpMode() {
        // load 2 purple on left side
        // load 1 green on right side by launcher


        indiana = new IndianaAuton(this, "blue");
        indiana.InitializeAuton();



        waitForStart();

        if (opModeIsActive()) {

            indiana.LauncherLED.SetColor(1);
            // Put code that should run during the active mode HERE in this area
            //I'm assuming that I am starting facing the obelisk with the motif pattern on the blue side*//
            indiana.MoveStraight(2600,0.5,100);
            indiana.GetMotif();

            sleep(2000);
            indiana.RotateLeft(45.0,0.5,100);
            indiana.MoveStraight(1800,0.5,500);

            indiana.Launchers.RunAtVelocity(0.6);

            indiana.ShootThreeShots(velocityPercentage);

            indiana.Launchers.TurnPowerOff();
            indiana.MoveStraight(-850,0.5,100);
            indiana.RotateLeft(135,0.5,100);
            indiana.MoveStraight(1600,0.5,100);
        }
    }


}



