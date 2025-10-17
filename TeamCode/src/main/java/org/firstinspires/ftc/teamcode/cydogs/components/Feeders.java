
package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;

public class Feeders {
    public static final double Servo_Power_Min =0.0;
    public static final double Servo_Power_Max =1.0;

    private LinearOpMode opMode;

    private CRServo left_bumper;
    private CRServo right_bumper;
    public Feeders(LinearOpMode opMode){
        this.opMode= opMode;
        left_bumper=opMode.hardwareMap.get(CRServo.class,"LeftFeeder");
        right_bumper=opMode.hardwareMap.get(CRServo.class,"RightFeeder");
        right_bumper.setDirection(CRServo.Direction.REVERSE);

    }

    public void MoveBumper()
    {
        left_bumper.setPower(0.6);
        right_bumper.setPower(0.6);
        opMode.sleep(200);
        left_bumper.setPower(0);
        right_bumper.setPower(0);
    }
    public void MoveLeftBumper()
    {
        left_bumper.setPower(.6);
        opMode.sleep(200);
        left_bumper.setPower(0);
    }
    public void MoveRightBumper()
    {
        right_bumper.setPower(0.6);
        opMode.sleep(200);
        right_bumper.setPower(0);

    }
}
