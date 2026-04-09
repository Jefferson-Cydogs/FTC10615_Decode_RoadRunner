package org.firstinspires.ftc.teamcode.cydogs.chassis;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.components.AprilTagReaderDuo;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED.ColorOption;
import org.firstinspires.ftc.teamcode.cydogs.components.Feeders;
import org.firstinspires.ftc.teamcode.cydogs.components.Gates;
import org.firstinspires.ftc.teamcode.cydogs.components.Intake;
import org.firstinspires.ftc.teamcode.cydogs.components.LaunchersWithVelocity;

import java.util.Objects;

public class IndianaAuton extends IndianaChassis {
    // declare devices
    public AprilTagReaderDuo TagReader;
    public org.firstinspires.ftc.teamcode.cydogs.components.Intake Intake;
    public Feeders Feeders;
    public LaunchersWithVelocity Launchers;
    public ColorLED LauncherLED;
    private ColorLED LeftLED;
    private ColorLED RightLED;
    public Gates Gates;

    // declare public properties
    public String CurrentMotif = "None";
    public String Alliance;

    private boolean debugMode = false;

    // This is called a constructor method, it is the same name as the class
    //  and used when first instantiating the class in the place it's used
    public IndianaAuton(LinearOpMode currentOp, String alliance, boolean setDebugMode)
    {
        // this is how you call the constructor of the class you're extending
        super(currentOp);
        debugMode = setDebugMode;
        Alliance = alliance.toLowerCase();
        TagReader = new AprilTagReaderDuo(currentOp,Alliance);
        Feeders = new Feeders(currentOp);
        Intake = new Intake(currentOp);
        LauncherLED = new ColorLED(currentOp,"LauncherLED");
        Launchers = new LaunchersWithVelocity(currentOp);
        LeftLED = new ColorLED(currentOp,"LeftLED");
        RightLED = new ColorLED(currentOp,"RightLED");
        Gates = new Gates(currentOp);
        Intake.standardSpeed = 0.75;
    }

    // This says no uses but is important to keep so that the constructor can be
    //  called without setDebugMode
    public IndianaAuton(LinearOpMode currentOp, String alliance)
    {
        this(currentOp, alliance, false);
    }


    public void InitializeAuton()
    {
        InitializeChassisAutonomous();
        TagReader.initAprilTag();
        Launchers.initLauncher();
        Gates.CloseLeftGate();
        Gates.CloseRightGate();
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

    public void ColorLEDForAlliance(String NearOrFar)
    {
        if(NearOrFar.equalsIgnoreCase("near"))
        {
            LeftLED.SetColorByName("off");
            LauncherLED.SetColorByName(Alliance);
            RightLED.SetColorByName("off");
        }
        else {
            LeftLED.SetColorByName(Alliance);
            LauncherLED.SetColorByName(Alliance);
            RightLED.SetColorByName(Alliance);
        }
    }


    public void ShootPurple(int sleepFirst, double velocityPercentage, int bumperRunTime)
    {
        myOpMode.sleep(sleepFirst);
        Launchers.WaitForLaunchersToBeAtSpeed(velocityPercentage, 2000);
        RunLeftIntakeAndBumper(bumperRunTime);
    }
    public void ShootGreen(int sleepFirst, double velocityPercentage, int bumperRunTime, boolean noIntake)
    {
        myOpMode.sleep(sleepFirst);
        Launchers.WaitForLaunchersToBeAtSpeed(velocityPercentage, 2000);
        if(noIntake) {RunRightBumperNoIntake(bumperRunTime);}
        else {RunRightIntakeAndBumper(bumperRunTime);}

    }

    public void ShootPurpleNoWait(int sleepFirst, double velocityPercentage, int bumperRunTime)
    {
        myOpMode.sleep(sleepFirst);
        RunLeftIntakeAndBumper(bumperRunTime);
    }
    public void ShootGreenNoWait(int sleepFirst, double velocityPercentage, int bumperRunTime, boolean noIntake)
    {
        myOpMode.sleep(sleepFirst);
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

    private void RunRightBumperNoIntake(int ForHowLong)
    {

        Feeders.MoveRightBumper(ForHowLong);

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

    public void ShootFirstThreeShotsFast(double velocityPercentage, int preShotWait)
    {
        if (CurrentMotif == "PPG")
        {
            ShootPurple(preShotWait,velocityPercentage,300);
            ShootPurple(preShotWait, velocityPercentage, 600);//2000
            ShootGreen(preShotWait, velocityPercentage, 400, false);

        } else if(CurrentMotif=="GPP") {
            ShootGreen(preShotWait, velocityPercentage, 400, true);
            ShootPurple(preShotWait,velocityPercentage,300);
            ShootPurple(preShotWait, velocityPercentage, 600);
        }
        else  // PGP
        {
            ShootPurple(preShotWait,velocityPercentage,300);
            ShootGreen(preShotWait, velocityPercentage, 400, true);
            ShootPurple(preShotWait, velocityPercentage, 600);

        }
    }

    // maybe create variables for the frist and second times, so you can call it and pass
    //  first or second and then it uses the correct variables
    public void ShootSecondThreeShotsFast(double velocityPercentage, int preShotWait)
    {
        if (CurrentMotif == "PPG")
        {
            ShootPurple(preShotWait,velocityPercentage,300);
            ShootPurple(preShotWait, velocityPercentage, 600);//2000
            ShootGreen(preShotWait, velocityPercentage, 600, false);

        } else if(CurrentMotif=="GPP") {
            ShootGreen(preShotWait, velocityPercentage, 600, true);
            ShootPurple(preShotWait,velocityPercentage,300);
            ShootPurple(preShotWait, velocityPercentage, 600);
        }
        else  // PGP
        {
            ShootPurple(preShotWait,velocityPercentage,300);
            ShootGreen(preShotWait, velocityPercentage, 600, true);
            ShootPurple(preShotWait, velocityPercentage, 600);

        }
    }

    public void ShootAllFourSlots(double velocityPercentage, int preShotWait)
    {
        ShootPurple(preShotWait,velocityPercentage,300);
        ShootPurple(preShotWait, velocityPercentage, 600);//2000
        ShootGreen(preShotWait, velocityPercentage, 300, false);
        ShootGreen(preShotWait, velocityPercentage, 600, false);
    }






    public void EndAuton()
    {
        ColorLEDForAlliance();
        LaunchersWithVelocity.LauncherDecelerator.decelerateAsync(Launchers.Launchers, 0.5,0.02,50);

        myOpMode.sleep(2000);
    }





    public void ShootThirdThreeShotsFast(double velocityPercentage, int preShotWait)
    {
        ShootPurpleNoWait(preShotWait,velocityPercentage,300);
        ShootPurpleNoWait(preShotWait, velocityPercentage, 500);
        ShootGreenNoWait(preShotWait, velocityPercentage, 300, false);
        ShootGreenNoWait(preShotWait, velocityPercentage, 500, false);
    }

    public void ColorLEDsKITTSequence(int DurationInSeconds, String AllianceColor) {
        // Define Knight Rider style sequence
        ColorLED[][] Sequence = {
                {LeftLED},
                {LeftLED, LauncherLED},
                {LauncherLED, RightLED},
                {RightLED},
                {LauncherLED, RightLED},
                {LeftLED, LauncherLED},
        };

        long EndTime = System.currentTimeMillis() + (DurationInSeconds * 1000L);
        int StepDelay = 250; // ms per step, can be tuned

        while (System.currentTimeMillis() < EndTime) {
            for (ColorLED[] Step : Sequence) {
                //Turn all LEDs off
                LeftLED.SetColorName(ColorOption.OFF);
                LauncherLED.SetColorName(ColorOption.OFF);
                RightLED.SetColorName(ColorOption.OFF);

                //Turn current step LED on
                for (ColorLED LED : Step) {
                    if (Objects.equals(AllianceColor.toLowerCase(), "red")) {
                        LED.SetColorName(ColorOption.RED);
                    } else if (Objects.equals(AllianceColor.toLowerCase(), "blue")) {
                        LED.SetColorName(ColorOption.BLUE);
                    }
                }

                //Wait for step delay
                try {
                    Thread.sleep(StepDelay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        // Clear all LEDs at the end
        LeftLED.SetColorName(ColorOption.OFF);
        LauncherLED.SetColorName(ColorOption.OFF);
        RightLED.SetColorName(ColorOption.OFF);
    }

    public void CodeDebugger(String message)
    {
        if(debugMode) {
            myOpMode.telemetry.addData("Debugger: ", message);
            myOpMode.telemetry.update();
        }
    }
}
