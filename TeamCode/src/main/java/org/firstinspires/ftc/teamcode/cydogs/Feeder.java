package org.firstinspires.ftc.teamcode.cydogs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;

public class Feeder {
public static final double Servo_Power_Min =0.0;
public static final double Servo_Power_Max =1.0;

private LinearOpMode opMode;

private CRServo left_feeder;
private CRServo right_feeder;

    public Feeder(LinearOpMode opMode,String left_feeder){
    this.opMode= opMode;
    left_feeder=opMode.hardwareMap.get(CRServo.class,"left_feeder");

    }

    public Feeder(LinearOpMode opMode,String right_feeder){
        this.opMode= opMode;
        right_feeder=opMode.hardwareMap.get(CRServo.class,"right_feeder");

    }
    public void MoveFeeder(){
        left_feeder.setPower(0.6);
        right_feeder.setPower(-0.6);

    }



}
