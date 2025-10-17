package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class ColorLED {

    public static final double SERVO_MIN = 0.0;
    public static final double SERVO_MAX = 1.0;

    private LinearOpMode opMode;
    private Servo myServo;

    public ColorLED(LinearOpMode opMode, String LEDName) {
        this.opMode = opMode;
        myServo = opMode.hardwareMap.get(Servo.class, LEDName);
    }

    public void SetColor(double ColorNumber) {
        ;
        ColorNumber = Math.max(SERVO_MIN, Math.min(SERVO_MAX, ColorNumber));
        myServo.setPosition(ColorNumber);
    }
}