package org.firstinspires.ftc.teamcode.cydogs.indiana;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
public class Intake {

    public static final double Servo_Power_Max = 1.0;

    private LinearOpMode opMode;
    private CRServo MyIntake;

    public Intake(LinearOpMode opMode) {
        this.opMode = opMode;
        MyIntake = opMode.hardwareMap.get(CRServo.class, "Intake");
        MyIntake.setDirection(CRServo.Direction.REVERSE);

    }

    public void turnIntakeon() {
        MyIntake.setPower(0.6);

    }
    public void reverseintake() {
        MyIntake.setPower(-0.6);

    }

    public void turnintakeoff() {


        MyIntake.setPower(0);
    }
}

