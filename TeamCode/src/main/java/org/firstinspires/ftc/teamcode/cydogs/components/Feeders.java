package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;


public class Feeders {
    private LinearOpMode opMode;
    private CRServo left_bumper;
    private CRServo right_bumper;

    public Feeders(LinearOpMode opMode)
    {
        this.opMode = opMode;

        left_bumper = opMode.hardwareMap.get(CRServo.class,"LeftFeeder");
        right_bumper = opMode.hardwareMap.get(CRServo.class,"RightFeeder");

        right_bumper.setDirection(CRServo.Direction.REVERSE);
    }

    public void MoveBumpers()
    {
        left_bumper.setPower(0.6);
        right_bumper.setPower(0.6);
        opMode.sleep(200);
        left_bumper.setPower(0);
        right_bumper.setPower(0);
    }

    public void MoveLeftBumper()
    {
        MoveLeftBumper(500);
    }
    public void MoveLeftBumper(int RunForMilliseconds)
    {
        left_bumper.setPower(.7);
        opMode.sleep(RunForMilliseconds);
        left_bumper.setPower(0);
    }

    public void MoveRightBumper()
    {
        MoveRightBumper(500);
    }
    public void MoveRightBumper(int RunForMilliseconds)
    {
        right_bumper.setPower(0.7);
        opMode.sleep(RunForMilliseconds);
        right_bumper.setPower(0);
    }

    public void ActivateLeftBumper()
    {
        left_bumper.setPower(0.6);
    }

    public void DeactivateLeftBumper()
    {
        left_bumper.setPower(0);
    }

    public void ActivateRightBumper()
    {
        right_bumper.setPower(0.6);
    }

    public void DeactivateRightBumper()
    {
        right_bumper.setPower(0.0);
    }

    public void ReverseRightBumper()
    {
        right_bumper.setPower(-0.6);
    }

    public void ReverseLeftBumper()
    {
        left_bumper.setPower(-0.6);
    }

}
