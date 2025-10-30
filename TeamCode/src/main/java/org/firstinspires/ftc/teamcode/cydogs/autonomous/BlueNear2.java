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
@Autonomous(name= "Blue Near 2", group= "Autonomous")
public class BlueNear2 extends LinearOpMode {

    /* declare variables

     */
    private String currentMotif;
    private double velocityPercentage = 0.39;
    private AprilTagReaderDuo tagReader;
    private Intake intake;
    private Feeders pusher;
    private int Waitbetweenshots = 1200;
    public int allienceTurnModifier = 1;
    @Override
    public void runOpMode() {
                // load 2 purple on left side
                // load 1 green on right side by launcher

                // Put code that should run during initialization HERE in this area
                IndianaChassis wheels = new IndianaChassis(this);
                wheels.InitializeAutonomous();
                LaunchersWithVelocity shooter = new LaunchersWithVelocity(this);
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
                    //    sleep(100);
                    //purple (0.91)
                    pusher.MoveBumpers();
                    wheels.MoveStraight(800, 0.55, 100);
                    wheels.RotateLeft(80*allienceTurnModifier, 0.55, 1000);

                    //This is where we would scan the obelisk
                    currentMotif = tagReader.getObelisk();
                    telemetry.addData("Found Motif: ", currentMotif);
                    telemetry.update();
                    sleep(2000);
                    shooter.RunAtVelocity(velocityPercentage);
                    wheels.RotateLeft(50*allienceTurnModifier, 0.55, 500);
                    //wheels.MoveStraight(1567, 0.55, 150);
                    //sleep(1530);
                    if (currentMotif == "PPG")
                    {
                        sleep(300);
                        while(!shooter.IsMotorAtSpeed(velocityPercentage)){}
                        RunLeftIntakeAndBumper(300);
                        sleep(300);
                        while(!shooter.IsMotorAtSpeed(velocityPercentage)){}
                        RunLeftIntakeAndBumper(2000);
                        sleep(300);
                        while(!shooter.IsMotorAtSpeed(velocityPercentage)){}
                        RunRightIntakeAndBumper(500);

                    } else if(currentMotif=="GPP") {
                        sleep(300);
                        while(!shooter.IsMotorAtSpeed(velocityPercentage)){}
                        RunRightIntakeAndBumper(500);
                        sleep(300);
                        while(!shooter.IsMotorAtSpeed(velocityPercentage)){}
                        RunLeftIntakeAndBumper(300);
                        sleep(300);
                        while(!shooter.IsMotorAtSpeed(velocityPercentage)){}
                        RunLeftIntakeAndBumper(2000);

                    }
                    else  // PGP
                    {
                        sleep(300);
                        while(!shooter.IsMotorAtSpeed(velocityPercentage)){}
                        RunLeftIntakeAndBumper(300);
                        sleep(300);
                        while(!shooter.IsMotorAtSpeed(velocityPercentage)){}
                        RunRightIntakeAndBumper(500);
                        sleep(300);
                        while(!shooter.IsMotorAtSpeed(velocityPercentage)){}
                        RunLeftIntakeAndBumper(2000);

                    }

                    shooter.TurnPowerOff();
                    wheels.MoveStraight(700,.5,100);
                    wheels.StrafeRight(400*allienceTurnModifier,.5,100);



                }


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