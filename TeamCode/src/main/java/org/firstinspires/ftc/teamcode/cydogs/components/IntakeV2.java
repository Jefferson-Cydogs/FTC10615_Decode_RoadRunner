package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;


public class IntakeV2 {
    private LinearOpMode opMode;
    private DcMotor intake;

    private double standardSpeed = 0.8;
    private double standardReverseSpeed = -0.8;

    public IntakeV2(LinearOpMode opMode)
    {
        this.opMode = opMode;

        intake = opMode.hardwareMap.get(DcMotor.class, "Intake");

        intake.setDirection(DcMotor.Direction.REVERSE);

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
