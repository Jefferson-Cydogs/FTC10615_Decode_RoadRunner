package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


public class LaunchersWithVelocity
{
    // Specs for typical motors we use:
    //    GoBilda 5203 Series Yellow Jacket 223 RPM, 751.8 PPR
    //    GoBilda 5203 Series Yellow Jacket 312 RPM, 537.7 PPR
    //       Without gearbox, theoretical 6,000RPM, empirical is 4,620RPM; ticks/second is always 28 at the motor shaft for goBILDA Yellow Jacket motors
    //    GoBilda 5203 Series Yellow Jacket 435 RPM, 384.5 PPR
    // Max TPS = (Motor's RPM / 60) * Motor's TicksPerRotation
    //public static final double MaxTicksPerSecond = (312.0 / 60.0) * 537.7; //TPS=2,796.04 with no modifications
    public static final double MaxTicksPerSecond = (4620.0 / 60.0) * 28.0; //TPS=2,156 without gearbox
    private final double VelocityTolerance = 0.022; //1.5%

    private final double VelocityCorrection = 0.975;

    private LinearOpMode opMode;
    public DcMotorEx Launchers;

    public void initLauncher() {
    }

    public LaunchersWithVelocity(LinearOpMode opMode)
    {
        this.opMode = opMode;

        Launchers = opMode.hardwareMap.get(DcMotorEx.class,"Launchers");
        Launchers.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        Launchers.setDirection(DcMotorEx.Direction.FORWARD);
        Launchers.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        Launchers.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        Launchers.setVelocityPIDFCoefficients(75, 0, 0, 15.4863);
    }

    public double GetCurrentVelocity() {
        return Launchers.getVelocity();
    }

    public double GetCurrentVelocityPercent()
    {
        return Launchers.getVelocity()/MaxTicksPerSecond;
    }

    public void RunAtVelocity(double TargetVelocityPercentage)
    {
        Launchers.setVelocity(TargetVelocityPercentage*VelocityCorrection * MaxTicksPerSecond);
    }

    /** Fix from goBILDA for the Floodgate Power Switch disconnect issue.
        Would need to be called in a loop, until reaching targetVelocity. Needs testing* */
    void setSafeVelocity(double targetVelocity)
    {
        final double SLEW_RATE = 0.2 * MaxTicksPerSecond;
        double currentVelocity = Launchers.getVelocity();

        double desiredChange = targetVelocity - currentVelocity;
        double limitedChange = Math.max(-SLEW_RATE,
                                        Math.min(desiredChange, SLEW_RATE));

        Launchers.setVelocity(currentVelocity += limitedChange);
    }

    public void StopLaunchersSafely()
    {
        /*double IntermediateVelocity = Launchers.getVelocity() / 3;

        Launchers.setVelocity(IntermediateVelocity * 2);
        opMode.sleep(200);
        Launchers.setVelocity(IntermediateVelocity);
        opMode.sleep(200);
        Launchers.setVelocity(0);*/
        while (Launchers.getVelocity() > (MaxTicksPerSecond * 0.1)) {
        }

        Launchers.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        Launchers.setVelocity(0);
    }

    public void ResetLaunchersToFloat()
    {
        Launchers.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
    }

    public static class LauncherDecelerator
    {
        /**
         * Smoothly decelerates a motor in a separate thread.
         *
         * @param decayFactor  Power reduction factor per step (e.g., 0.6 = 40% drop)
         * @param minThreshold Minimum power before stopping (e.g., 0.05)
         * @param intervalMs   Delay between steps in milliseconds (e.g., 20)
         */
        public static void decelerateAsync(DcMotorEx motor, double decayFactor, double minThreshold, int intervalMs) {
            new Thread(() -> {
                double velocity = motor.getVelocity();

                while (Math.abs(velocity) > minThreshold) {
                    velocity *= decayFactor;
                    motor.setVelocity(velocity);

                    try {
                        Thread.sleep(intervalMs);
                    }
                    catch (InterruptedException e) {
                        break;
                    }
                }

                motor.setVelocity(0);
            }).start();
        }
    }

    public boolean IsMotorAtSpeed(double TargetVelocityPercentage)
    {
        double TargetVelocity = TargetVelocityPercentage * MaxTicksPerSecond;

        return (Math.abs(Launchers.getVelocity() - TargetVelocity) <= (TargetVelocity * VelocityTolerance));
    }

    public boolean IsMotorTooStrong(double TargetVelocityPercentage)
    {
        return (Launchers.getVelocity() > ((TargetVelocityPercentage * MaxTicksPerSecond) * (1 + VelocityTolerance)));
    }

    public void TurnPowerOff() {
        Launchers.setVelocity(0);
    }

    public void WaitForLaunchersToBeAtSpeed(double targetSpeed, int giveUpMilliseconds)
    {
        long start = System.currentTimeMillis();
        long elapsed;
        while(!IsMotorAtSpeed(targetSpeed)) {
            elapsed = System.currentTimeMillis() - start;
            if (elapsed > giveUpMilliseconds) {break;}
        }
    }
}
