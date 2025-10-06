package org.firstinspires.ftc.teamcode.cydogs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class ColorLight {

    public static final double SERVO_MIN = 0.0;
    public static final double SERVO_MAX = 1.0;

    private LinearOpMode opMode;
    private Servo myServo;

    public ColorLight(LinearOpMode opMode) {
        this.opMode = opMode;
        myServo = opMode.hardwareMap.get(Servo.class, "artifactIndicatorLight");
    }

    public void SetColor(double ColorNumber, long waitMs) {
        ;
        ColorNumber = Math.max(SERVO_MIN, Math.min(SERVO_MAX, ColorNumber));
        myServo.setPosition(ColorNumber);
        opMode.sleep(waitMs);
    }
}