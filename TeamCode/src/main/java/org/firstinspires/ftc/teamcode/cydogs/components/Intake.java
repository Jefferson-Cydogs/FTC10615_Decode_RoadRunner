package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;


public class Intake {
    private LinearOpMode opMode;
    private CRServo LeftIntake;
    private CRServo RightIntake;

    public Intake(LinearOpMode opMode)
    {
        this.opMode = opMode;

        LeftIntake = opMode.hardwareMap.get(CRServo.class, "LeftIntake");
        RightIntake = opMode.hardwareMap.get(CRServo.class, "RightIntake");

        LeftIntake.setDirection(CRServo.Direction.REVERSE);
        RightIntake.setDirection(CRServo.Direction.REVERSE);
    }

    public void turnLeftIntakeon()
    {
       LeftIntake.setPower(0.9);
    }

    public void reverseleftintake()
    {
        LeftIntake.setPower(-0.8);
    }

    public void turnleftintakeoff()
    {
        LeftIntake.setPower(0);
    }

    public void turnRightIntakeon()
    {
        RightIntake.setPower(0.9);
    }

    public void reverserightintake()
    {
        RightIntake.setPower(-0.8);
    }

    public void turnrightintakeoff()
    {
        RightIntake.setPower(0);
    }

    public void turnBothIntakesOn()
    {
        RightIntake.setPower(0.9);
        LeftIntake.setPower(0.9);
    }

    public void turnBothIntakesOff()
    {
        RightIntake.setPower(0);
        LeftIntake.setPower(0);
    }

}
