package org.firstinspires.ftc.teamcode.cydogs.chassis;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.components.AprilTagReaderDuo;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED;
import org.firstinspires.ftc.teamcode.cydogs.components.Feeders;
import org.firstinspires.ftc.teamcode.cydogs.z_archive.Intake;
import org.firstinspires.ftc.teamcode.cydogs.components.IntakeV2;
import org.firstinspires.ftc.teamcode.cydogs.components.LaunchersWithVelocity;

public class IndianaAuton extends IndianaChassis {
    // declare devices
    public AprilTagReaderDuo TagReader;
    public IntakeV2 Intake;
    public Intake Intakes;
    public Feeders Feeders;
    public LaunchersWithVelocity Launchers;
    public ColorLED LauncherLED;
    private ColorLED LeftLED;
    private ColorLED RightLED;

    // declare public properties
    public String CurrentMotif = "None";
    public String Alliance;

    // add code to light LED for motif
    public IndianaAuton(LinearOpMode currentOp, String alliance)
    {
        super(currentOp);
        Alliance = alliance.toLowerCase();
        TagReader = new AprilTagReaderDuo(currentOp,Alliance);
        Feeders = new Feeders(currentOp);
        Intake = new IntakeV2(currentOp);
        LauncherLED = new ColorLED(currentOp,"LauncherLED");
        Launchers = new LaunchersWithVelocity(currentOp);
        LeftLED = new ColorLED(currentOp,"LeftLED");
        RightLED = new ColorLED(currentOp,"RightLED");

    }


    public void InitializeAuton()
    {
        InitializeChassisAutonomous();
        TagReader.initAprilTag();
        Launchers.initLauncher();
        LauncherLED.SetColorByName(Alliance);
        LeftLED.SetColorByName(Alliance);
        RightLED.SetColorByName(Alliance);
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

    public void ColorLEDForMotif()
    {
        if (CurrentMotif == "PPG")
        {
            LeftLED.SetColorByName("purple");
            LauncherLED.SetColorByName("purple");
            RightLED.SetColorByName("green");

        } else if(CurrentMotif=="GPP") {
            LeftLED.SetColorByName("green");
            LauncherLED.SetColorByName("purple");
            RightLED.SetColorByName("purple");
        }
        else  // PGP
        {
            LeftLED.SetColorByName("purple");
            LauncherLED.SetColorByName("green");
            RightLED.SetColorByName("purple");

        }
    }

    public void ColorLEDForAlliance()
    {
        LeftLED.SetColorByName(Alliance);
        LauncherLED.SetColorByName(Alliance);
        RightLED.SetColorByName(Alliance);
    }

    public void ShootThreeShots(double velocityPercentage)
    {
        if (CurrentMotif == "PPG")
        {
            ShootPurple(900,velocityPercentage,300);
            ShootPurple(600, velocityPercentage, 1500);//2000
            ShootGreen(600, velocityPercentage, 800, false);

        } else if(CurrentMotif=="GPP") {
            ShootGreen(900, velocityPercentage, 800, true);
            ShootPurple(600,velocityPercentage,300);
            ShootPurple(600, velocityPercentage, 1500);
        }
        else  // PGP
        {
            ShootPurple(900,velocityPercentage,300);
            ShootGreen(600, velocityPercentage, 800, true);
            ShootPurple(600, velocityPercentage, 1500);

        }
    }

    public void ShootThreeShotsSecondTime(double velocityPercentage)
    {
        if (CurrentMotif == "PPG")
        {
            ShootPurple(900,velocityPercentage,300);
            ShootPurple(600, velocityPercentage, 1500);//2000
            ShootGreen(600, velocityPercentage, 2500, false);

        } else if(CurrentMotif=="GPP") {
            ShootGreen(900, velocityPercentage, 2500, true);
            ShootPurple(600,velocityPercentage,300);
            ShootPurple(600, velocityPercentage, 1500);
        }
        else  // PGP
        {
            ShootPurple(900,velocityPercentage,300);
            ShootGreen(600, velocityPercentage, 2500, true);
            ShootPurple(600, velocityPercentage, 1500);

        }
    }

    public void ShootPurple(int sleepFirst, double velocityPercentage, int bumperRunTime)
    {
        myOpMode.sleep(sleepFirst);
        while(!Launchers.IsMotorAtSpeed(velocityPercentage)){}
        RunLeftIntakeAndBumper(bumperRunTime);
    }
    public void ShootGreen(int sleepFirst, double velocityPercentage, int bumperRunTime, boolean noIntake)
    {
        myOpMode.sleep(sleepFirst);
        while(!Launchers.IsMotorAtSpeed(velocityPercentage)){}
        if(noIntake) {RunRightBumperNoIntake(bumperRunTime);}
        else {RunRightIntakeAndBumper(bumperRunTime);}

    }

    private void RunLeftIntakeAndBumper(int ForHowLong)
    {
        Intake.turnIntakeOn();
        Feeders.MoveLeftBumper(ForHowLong);
       Intake.turnIntakeOff();
    }

    private void RunRightIntakeAndBumper(int ForHowLong)
    {
       Intake.turnIntakeOn();
        Feeders.MoveRightBumper(ForHowLong);
      Intake.turnIntakeOff();
    }
    private void RunLeftBumperNoIntake(int ForHowLong)
    {

        Feeders.MoveLeftBumper(ForHowLong);

    }

    private void RunRightBumperNoIntake(int ForHowLong)
    {

        Feeders.MoveRightBumper(ForHowLong);

    }

    public int AskStartWaitTime() {
        int delaySeconds = 0;

        if (myOpMode.opModeInInit()) {


            while (myOpMode.opModeInInit()) {
                myOpMode.telemetry.addLine("Driver,");
                myOpMode.telemetry.addLine("To INCREASE starting wait time, press DPAD UP");
                myOpMode.telemetry.addLine("To DECREASE starting wait time, press DPAD DOWN");
                myOpMode.telemetry.addLine("To complete selection, press B");
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

    public void BlueNearOpeningFlourish()
    {
        MoveStraight(1100, 0.55, 100);
        RotateLeft(83, 0.55, 100);
        myOpMode.sleep(150);
        GetMotif();
        ColorLEDForMotif();
    }

    public void RedNearOpeningFlourish()
    {
        MoveStraight(1100, 0.55, 100);
        RotateRight(68, 0.55, 100);
        myOpMode.sleep(150);
        GetMotif();
        ColorLEDForMotif();
    }

    public void EjectAllArtifacts(int forHowLong)
    {
        Intake.reverseIntake();
        Feeders.ReverseLeftBumper();
        Feeders.ReverseRightBumper();
        myOpMode.sleep(forHowLong);
        Feeders.DeactivateLeftBumper();
        Feeders.DeactivateRightBumper();
        Intake.turnIntakeOff();

    }

    public void ReverseFeeders(int forHowLong)
    {
        Feeders.ReverseLeftBumper();
        Feeders.ReverseRightBumper();
        myOpMode.sleep(forHowLong);
        Feeders.DeactivateLeftBumper();
        Feeders.DeactivateRightBumper();
    }


}
