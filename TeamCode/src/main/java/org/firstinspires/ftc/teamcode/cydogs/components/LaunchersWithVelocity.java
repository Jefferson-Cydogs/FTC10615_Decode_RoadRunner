package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;


public class LaunchersWithVelocity
{
    // Specs for typical motors we use:
    //    GoBilda 5203 Series Yellow Jacket 223 RPM, 751.8 PPR
    //    GoBilda 5203 Series Yellow Jacket 312 RPM, 537.7 PPR
    //    GoBilda 5203 Series Yellow Jacket 435 RPM, 384.5 PPR
    // Max TPS = (Motor's RPM / 60) * Motor's TicksPerRotation
    public static final double MaxTicksPerSecond = (312.0 / 60.0) * 537.7; //TPS=2,796.04

    private LinearOpMode opMode;
    private DcMotorEx Launchers;

    public void initLauncher()
    {
    }

    public LaunchersWithVelocity(LinearOpMode opMode)
    {
        this.opMode = opMode;

        Launchers = opMode.hardwareMap.get(DcMotorEx.class,"RightLauncher");
        Launchers.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        Launchers.setDirection(DcMotorEx.Direction.REVERSE);
        Launchers.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        //Launchers.setVelocityPIDFCoefficients(300, 0, 0, 10);
    }

    public double GetCurrentVelocity()
    {
        return Launchers.getVelocity();
    }

    public void RunAtVelocity(double TargetVelocityPercentage) {
        double TargetVelocity = TargetVelocityPercentage * MaxTicksPerSecond;

        Launchers.setVelocity(TargetVelocity);
        /*while (Launchers.getVelocity() < TargetVelocity) {
            //Do nothing
        }*/
    }

    public void StopLaunchersSafely ()
    {
        if (Launchers.getVelocity() < (MaxTicksPerSecond * 0.1))
        {
            Launchers.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            Launchers.setVelocity(0);
            Launchers.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        }
    }

    public boolean IsMotorAtSpeed(double TargetVelocityPercentage)
    {
        double CurrentVelocity = Launchers.getVelocity();
        double TargetVelocity = TargetVelocityPercentage * MaxTicksPerSecond;

        return (Math.abs(CurrentVelocity - TargetVelocity) <= (TargetVelocity * 0.02));
    }

    public boolean IsMotorTooStrong(double TargetVelocityPercentage)
    {
        double CurrentVelocity= Launchers.getVelocity();
        double TargetVelocity = TargetVelocityPercentage * MaxTicksPerSecond;

        return (CurrentVelocity > (TargetVelocity * 1.02));
    }

    public void TurnPowerOff()
    {
        Launchers.setVelocity(0);
    }

}
