package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;


public class IntakeV2
{
    private LinearOpMode opMode;
    private DcMotorEx intake;

    public double standardSpeed = 0.70;
    public double standardReverseSpeed = -0.75;

    public IntakeV2(LinearOpMode opMode)
    {
        this.opMode = opMode;

        intake = opMode.hardwareMap.get(DcMotorEx.class, "Intake");

        intake.setDirection(DcMotorEx.Direction.REVERSE);
    }

    void setSafePower(double targetPower)
    {
        final double SLEW_RATE = 0.2;
        double currentPower = intake.getPower();

        double desiredChange = targetPower - currentPower;
        double limitedChange = Math.max(-SLEW_RATE,
                                        Math.min(desiredChange, SLEW_RATE));

        intake.setPower(currentPower += limitedChange);
    }

    public void turnIntakeOn()
    {
       intake.setPower(standardSpeed);
    }

    public void reverseIntake()
    {
        intake.setPower(standardReverseSpeed);
    }

    public void turnIntakeOff()
    {
        intake.setPower(0);
    }

}
