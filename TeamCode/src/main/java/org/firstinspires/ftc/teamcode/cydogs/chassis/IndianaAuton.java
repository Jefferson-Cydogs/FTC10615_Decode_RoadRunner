package org.firstinspires.ftc.teamcode.cydogs.chassis;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.components.AprilTagReaderDuo;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED;
import org.firstinspires.ftc.teamcode.cydogs.components.Feeders;
import org.firstinspires.ftc.teamcode.cydogs.components.Intake;
import org.firstinspires.ftc.teamcode.cydogs.components.LaunchersWithVelocity;

public class IndianaAuton extends IndianaChassis {
    // declare devices
    public AprilTagReaderDuo TagReader;
    public Intake Intakes;
    public Feeders Feeders;
    public LaunchersWithVelocity Launchers;
    public ColorLED LauncherLED;

    // declare public properties
    public String CurrentMotif = "None";
    public String Alliance;


    public IndianaAuton(LinearOpMode currentOp, String alliance)
    {
        super(currentOp);
        Alliance = alliance.toLowerCase();
        TagReader = new AprilTagReaderDuo(currentOp,Alliance);
        Feeders = new Feeders(currentOp);
        Intakes = new Intake(currentOp);
        LauncherLED = new ColorLED(currentOp,"LauncherLED");
        Launchers = new LaunchersWithVelocity(this);

    }


    public void InitializeAuton()
    {
        InitializeChassisAutonomous();
        TagReader.initAprilTag();
        Launchers.initLauncher();
    }

    public void GetMotif()
    {
        int counter = 0;

        while(CurrentMotif == "None" && counter<20) {
            CurrentMotif = TagReader.getObelisk();
            counter++;
            myOpMode.telemetry.addData("Found Motif: ", CurrentMotif);
            myOpMode.telemetry.update();
        }

    }

    public void ShootThreeShots(double velocityPercentage)
    {
        if (CurrentMotif == "PPG")
        {
            ShootPurple(300,velocityPercentage,300);
            ShootPurple(300, velocityPercentage, 2000);
            ShootGreen(300, velocityPercentage, 500);

        } else if(CurrentMotif=="GPP") {
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
        myOpMode.sleep(sleepFirst);
        while(!Launchers.IsMotorAtSpeed(velocityPercentage)){}
        RunLeftIntakeAndBumper(bumperRunTime);
    }
    public void ShootGreen(int sleepFirst, double velocityPercentage, int bumperRunTime)
    {
        myOpMode.sleep(sleepFirst);
        while(!Launchers.IsMotorAtSpeed(velocityPercentage)){}
        RunRightIntakeAndBumper(bumperRunTime);
    }

    private void RunLeftIntakeAndBumper(int ForHowLong)
    {
        Intakes.turnLeftIntakeon();
        Feeders.MoveLeftBumper(ForHowLong);
        Intakes.turnleftintakeoff();
    }

    private void RunRightIntakeAndBumper(int ForHowLong)
    {
        Intakes.turnRightIntakeon();
        Feeders.MoveRightBumper(ForHowLong);
        Intakes.turnrightintakeoff();
    }


    public int AskStartWaitTime() {
        int delaySeconds = 0;

        if (myOpMode.opModeInInit()) {
            myOpMode.telemetry.addLine("Driver,");
            myOpMode.telemetry.addLine("To INCREASE starting wait time, press DPAD UP");
            myOpMode.telemetry.addLine("To DECREASE starting wait time, press DPAD DOWN");
            myOpMode.telemetry.addLine("To complete selection, press B");


            while (myOpMode.opModeInInit()) {
                myOpMode.telemetry.addData("Current Delay Time:", delaySeconds);
                myOpMode.sleep(50);
                myOpMode.telemetry.update();
                if (myOpMode.gamepad1.b) {
                    return delaySeconds;
                }
                if (myOpMode.isStarted()) {
                    return delaySeconds;
                }
                if (myOpMode.gamepad1.dpad_up) {
                    delaySeconds++;
                    myOpMode.sleep(150);
                } else if (myOpMode.gamepad1.dpad_down) {
                    delaySeconds--;
                }
            }


        }
        return delaySeconds;
    }


}
