package org.firstinspires.ftc.teamcode.cydogs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaChassis;
import org.firstinspires.ftc.teamcode.cydogs.components.AprilTagReaderDuo;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED;
import org.firstinspires.ftc.teamcode.cydogs.components.Feeders;
import org.firstinspires.ftc.teamcode.cydogs.components.Intake;
import org.firstinspires.ftc.teamcode.cydogs.components.LaunchersWithVelocity;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station
@Autonomous(name= "Blue Near 3", group= "Autonomous")
public class BlueNear3 extends LinearOpMode {

    /* declare variables

     */
    private String currentMotif = null;
    private double velocityPercentage = 0.39;
    private AprilTagReaderDuo tagReader;
    private Intake intake;
    private Feeders pusher;
    private int Waitbetweenshots = 1200;
    public int allienceTurnModifier = 1;
    private LaunchersWithVelocity shooter;

    @Override
    public void runOpMode() {
                // load 2 purple on left side
                // load 1 green on right side by launcher

                // Put code that should run during initialization HERE in this area
                IndianaChassis wheels = new IndianaChassis(this);
                wheels.InitializeAutonomous();
                shooter = new LaunchersWithVelocity(this);
                shooter.initLauncher();
                pusher = new Feeders(this);
                ColorLED launcherLED = new ColorLED(this,"LauncherLED");
                intake = new Intake(this);
                tagReader = new AprilTagReaderDuo(this,"Blue");
                tagReader.initAprilTag();

                // Wait for the start button to be pressed on the driver station
                waitForStart();

                if (opModeIsActive()) {
                    launcherLED.SetColor(1);

                    //ClearBugAndFixPlayerSetup();
                    pusher.MoveBumpers();
                    
                    wheels.MoveStraight(900, 0.55, 100);
                    wheels.RotateLeft(80*allienceTurnModifier, 0.55, 1000);

                    GetMotif();

                    //sleep(2000);
                    shooter.RunAtVelocity(velocityPercentage);
                    wheels.RotateLeft(50*allienceTurnModifier, 0.55, 500);
                    //wheels.MoveStraight(1567, 0.55, 150);
                    //sleep(1530);

                    ShootThreeShots();

                    shooter.TurnPowerOff();

                    wheels.RotateLeft(32,.3,200);
                    wheels.StrafeLeft(305, .3, 200);
                    intake.turnLeftIntakeon();
                    pusher.ActivateLeftBumper();
                    wheels.MoveStraight(250, .25, 1200);
                    wheels.MoveStraight(339, .08, 300);
                    pusher.DeactivateLeftBumper();
                    intake.turnleftintakeoff();

                    intake.turnRightIntakeon();
                    pusher.ActivateRightBumper();
                    wheels.StrafeLeft(127,.3, 200);
                    wheels.MoveStraight(200, .15, 200);
                    pusher.DeactivateRightBumper();
                    intake.turnrightintakeoff();

                    wheels.MoveStraight(-500, .4, 200);
                    wheels.RotateRight(32, .3, 200);
                    wheels.StrafeRight(420, .4, 200);
                    ShootThreeShots();
                    wheels.StrafeRight(400, .6, 200);
                    sleep(2000);


                    //wheels.MoveStraight(700,.5,100);
                    ///wheels.StrafeRight(400*allienceTurnModifier,.5,100);



                }


    }



    private void GetMotif()
    {
        int counter = 0;
        while(currentMotif == null && counter<20) {
            currentMotif = tagReader.getObelisk();
            counter++;
            telemetry.addData("Found Motif: ", currentMotif);
            telemetry.update();
        }

    }


    public void ShootThreeShots()
    {
        if (currentMotif == "PPG")
        {
            ShootPurple(300,velocityPercentage,300);
            ShootPurple(300, velocityPercentage, 2000);
            ShootGreen(300, velocityPercentage, 500);

        } else if(currentMotif=="GPP") {
            ShootGreen(300, velocityPercentage, 500);
            ShootPurple(300,velocityPercentage,300);
            ShootPurple(300, velocityPercentage, 2000);
        }
        else  // PGP
        {
            ShootPurple(300,velocityPercentage,300);
            ShootGreen(300, velocityPercentage, 500);
            ShootPurple(300, velocityPercentage, 2000);

        }
    }

    public void ShootPurple(int sleepFirst, double velocityPercentage, int bumperRunTime)
    {
        sleep(sleepFirst);
        while(!shooter.IsMotorAtSpeed(velocityPercentage)){}
        RunLeftIntakeAndBumper(bumperRunTime);
    }
    public void ShootGreen(int sleepFirst, double velocityPercentage, int bumperRunTime)
    {
        sleep(sleepFirst);
        while(!shooter.IsMotorAtSpeed(velocityPercentage)){}
        RunRightIntakeAndBumper(bumperRunTime);
    }


    private void RunLeftIntakeAndBumper(int ForHowLong)
    {
        intake.turnLeftIntakeon();
        pusher.MoveLeftBumper(ForHowLong);
        intake.turnleftintakeoff();
    }

    private void RunRightIntakeAndBumper(int ForHowLong)
    {
        intake.turnRightIntakeon();
        pusher.MoveRightBumper(ForHowLong);
        intake.turnrightintakeoff();
    }
}