package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


public class IntakeV2 {
    private LinearOpMode opMode;
    private DcMotorEx intake;

    private double standardSpeed = 0.7;
    private double standardReverseSpeed = -0.7;

    public IntakeV2(LinearOpMode opMode)
    {
        this.opMode = opMode;

        intake = opMode.hardwareMap.get(DcMotorEx.class, "Intake");

        intake.setDirection(DcMotorEx.Direction.REVERSE);

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
