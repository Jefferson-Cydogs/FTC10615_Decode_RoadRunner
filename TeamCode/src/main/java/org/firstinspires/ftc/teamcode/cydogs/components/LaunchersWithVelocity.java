package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;


public class LaunchersWithVelocity
{
    // GoBilda 5203 Series Yellow Jacket 223 RPM -> 751.8 PPR
    // GoBilda 5203 Series Yellow Jacket 312 RPM -> 537.7 PPR
    // GoBilda 5203 Series Yellow Jacket 435 RPM -> 384.5 PPR
    // Max TPS = (Motor's RPM / 60) * Motor's Ticks per rotation
    public static final double MaxTicksPerSecond = (312.0 / 60.0) * 537.7;
    //public double velocityIn= 0.6;

    private LinearOpMode opMode;
    private DcMotorEx LeftLauncher;
    private DcMotorEx RightLauncher;

    public void initLauncher()
    {

    }

    public LaunchersWithVelocity(LinearOpMode opMode)
    {
        this.opMode = opMode;

        //LeftLauncher = opMode.hardwareMap.get(DcMotorEx.class,"LeftLauncher");
        RightLauncher = opMode.hardwareMap.get(DcMotorEx.class,"RightLauncher");

        //LeftLauncher.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        RightLauncher.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        //LeftLauncher.setDirection(DcMotorEx.Direction.REVERSE);
        //RightLauncher.setDirection(DcMotorEx.Direction.REVERSE);

        //LeftLauncher.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        RightLauncher.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public void RunAtVelocity(double VelocityPercentage)
    {
        double TargetVelocity = VelocityPercentage * MaxTicksPerSecond;

        //LeftLauncher.setVelocity(targetVelocity);
        RightLauncher.setVelocity(TargetVelocity);
    }

    public boolean IsMotorAtSpeed(double TargetVelocity)
    {
        //double CurrentLeftTicks= LeftLauncher.getVelocity();
        double CurrentRightTicks= RightLauncher.getVelocity();

        double TargetTicks = TargetVelocity * MaxTicksPerSecond;

        if (CurrentRightTicks < TargetTicks)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean IsMotorTooStrong(double TargetVelocity)
    {
        //double CurrentLeftTicks= LeftLauncher.getVelocity();
        double CurrentRightTicks= RightLauncher.getVelocity();

        double TargetTicks = TargetVelocity * MaxTicksPerSecond;

        if (CurrentRightTicks >= (TargetTicks * 1.01))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void TurnPowerOff()
    {
       // LeftLauncher.setVelocity(0);
        RightLauncher.setVelocity(0);
    }

}
