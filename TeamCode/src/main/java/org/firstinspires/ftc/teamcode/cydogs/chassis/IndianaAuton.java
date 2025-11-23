package org.firstinspires.ftc.teamcode.cydogs.chassis;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cydogs.components.AprilTagReaderDuo;
import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED;
import org.firstinspires.ftc.teamcode.cydogs.components.Feeders;
import org.firstinspires.ftc.teamcode.cydogs.z_archive.Intake;
import org.firstinspires.ftc.teamcode.cydogs.components.IntakeV2;
import org.firstinspires.ftc.teamcode.cydogs.components.Gates;
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
    public Gates Gates;

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
        Gates = new Gates(currentOp);
        Intake.standardSpeed = 0.75;

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
        //Launchers.WaitForLaunchersToBeAtSpeed(velocityPercentage, 2000);
        RunLeftIntakeAndBumper(bumperRunTime);
    }
    public void ShootGreenNoWait(int sleepFirst, double velocityPercentage, int bumperRunTime, boolean noIntake)
    {
        myOpMode.sleep(sleepFirst);
        //Launchers.WaitForLaunchersToBeAtSpeed(velocityPercentage, 2000);
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
            // was .55
        MoveStraight(1100, 0.6, 100);
        RotateLeft(83, 0.6, 100);

            // was 150
        myOpMode.sleep(100);
        GetMotif();
        ColorLEDForMotif();
    }

    public void RedNearOpeningFlourish()
    {
        MoveStraight(1100, 0.6, 100);
        RotateRight(68, 0.6, 100);
        myOpMode.sleep(100);
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

    public void BlueCommonStart(double velocityPercentage, int preShotWait)
    {

        // this clears bumper servo bug
        Feeders.MoveBumpersToFixBug();

        //moved earlier
        // #LauncherON
        Launchers.RunAtVelocity(velocityPercentage);

        BlueNearOpeningFlourish();

        // reduce pressure on gates before shooting
        ReverseFeeders(150);

        // was .55.  was 47 degrees
        RotateLeft(48, 0.55, 100);

        // need to open gates
        Gates.OpenBothGates();
        // #GatesOpen
        // sleep til Gates are open
        Gates.WaitForGateToOpen();


        ShootFirstThreeShotsFast(velocityPercentage, preShotWait);

        // need to sleep so it doesn't move while shooting
        myOpMode.sleep(200);
        // TO ADD
        // Check to see if color sensors see anything, if so, run
        // ejection code


        // was .5
        RotateLeft(24,.55,100);



        // #GatesClosed
        // was .45
        StrafeLeft(205, .5, 100);
        Gates.CloseBothGates();

        BlueAllianceNearPurplePurpleGreen();
        // #Intake ON

        // was .5
        MoveStraight(-800, .6, 100);
        Feeders.DeactivateRightBumper();

        // #Intake OFF
        Intake.turnIntakeOff();
    }


    private void BlueAllianceNearPurplePurpleGreen()
    {
        // Get purple
        // #Intake ON
        Intake.turnIntakeOn();

        // #LeftFeeder ON
        Feeders.ActivateLeftBumper();

        MoveStraight(700, .3, 100);
        //  indiana.MoveStraight(425, .4, 100);
        //  indiana.MoveStraight(320, .2, 300);

        // #LeftFeeder OFF
        Feeders.DeactivateLeftBumper();

        // Get Green
        // #RightFeeder ON
        Feeders.ActivateRightBumper();

        StrafeLeft(117,.4, 100);

        MoveStraight(230, .4, 100);

        // #LeftFeeder OFF
        Feeders.DeactivateLeftBumper();


        // #RightFeeder OFF


    }

    public void EndAuton()
    {


        ColorLEDForAlliance();
        LaunchersWithVelocity.LauncherDecelerator.decelerateAsync(Launchers.Launchers, 0.5,0.02,50);

        myOpMode.sleep(2000);
    }


    public void RedCommonStart(double velocityPercentage, int preShotWait)
    {

        // this clears bumper servo bug
        Feeders.MoveBumpersToFixBug();

        //moved earlier
        // #LauncherON
        Launchers.RunAtVelocity(velocityPercentage);

        RedNearOpeningFlourish();

        // reduce pressure on gates before shooting
        ReverseFeeders(150);

        // was .55.  was 47 degrees
        RotateRight(61, 0.55, 100);

        // need to open gates
        Gates.OpenBothGates();
        // #GatesOpen
        // sleep til Gates are open
        Gates.WaitForGateToOpen();


        ShootFirstThreeShotsFast(velocityPercentage, preShotWait);

        // was .5
        RotateRight(27,.5,100);

        // #GatesClosed
        // was .45
        StrafeRight(274, .5, 100);
        Gates.CloseBothGates();

        RedAllianceNearPurplePurpleGreen();
        // #Intake ON

        // was .5
        MoveStraight(-800, .6, 100);
        Feeders.DeactivateRightBumper();

        // #Intake OFF
        Intake.turnIntakeOff();
    }

    public void RedAllianceNearPurplePurpleGreen()
    {
        // Get purple
        // #Intake ON
        Intake.turnIntakeOn();

        // #LeftFeeder ON
        Feeders.ActivateLeftBumper();

        MoveStraight(645, .25, 200);
        //  indiana.MoveStraight(425, .4, 100);
        //  indiana.MoveStraight(320, .2, 300);

        // #LeftFeeder OFF
        Feeders.DeactivateLeftBumper();

        // Get Green
        // #RightFeeder ON
        Feeders.ActivateRightBumper();

        StrafeLeft(128,.4, 100);

        MoveStraight(220, .3, 200);

        // #LeftFeeder OFF
        Feeders.DeactivateLeftBumper();


        // #RightFeeder OFF
    }

    public void ShootThirdThreeShotsFast(double velocityPercentage, int preShotWait)
    {
        ShootPurpleNoWait(preShotWait,velocityPercentage,300);
        ShootPurpleNoWait(preShotWait, velocityPercentage, 500);
        ShootGreenNoWait(preShotWait, velocityPercentage, 300, false);
        ShootGreenNoWait(preShotWait, velocityPercentage, 500, false);
    }
}
