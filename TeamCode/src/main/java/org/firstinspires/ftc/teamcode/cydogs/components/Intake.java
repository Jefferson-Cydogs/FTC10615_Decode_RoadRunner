package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
public class Intake {

    public static final double Servo_Power_Max = 1.0;

    private LinearOpMode opMode;
    private CRServo LeftIntake;
    private CRServo RightIntake;

    public Intake(LinearOpMode opMode) {
        this.opMode = opMode;
        LeftIntake = opMode.hardwareMap.get(CRServo.class, "LeftIntake");
        LeftIntake.setDirection(CRServo.Direction.REVERSE);
        RightIntake = opMode.hardwareMap.get(CRServo.class, "RightIntake");
    }

    public void turnLeftIntakeon() {
       LeftIntake.setPower(0.8);

    }
    public void reverseleftintake() {
        LeftIntake.setPower(-0.8);

    }

    public void turnleftintakeoff() {


        LeftIntake.setPower(0);
    }
    public void turnRightIntakeon() {
        RightIntake.setPower(0.8);

    }
    public void reverserightintake() {
        RightIntake.setPower(-0.8);

    }

    public void turnrightintakeoff() {


        RightIntake.setPower(0);
    }
    public void turnBothIntakeon() {
        RightIntake.setPower(0.8);
        LeftIntake.setPower(0.8);

    }


    public void turnbothintakeoff() {


        RightIntake.setPower(0);
        LeftIntake.setPower(0);
    }

}

