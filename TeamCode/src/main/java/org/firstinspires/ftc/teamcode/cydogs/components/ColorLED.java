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
        ColorNumber = Math.max(SERVO_MIN,
                               Math.min(SERVO_MAX, ColorNumber));
        myServo.setPosition(ColorNumber);
    }

    public void SetColorByName(String ColorName) {
        final double TOLERANCE = 0.01;

        switch (ColorName.toLowerCase()) {
            case "white":
                //if (Math.abs(myServo.getPosition() - 1.0) > TOLERANCE) {
                    myServo.setPosition(1.0);
                //}
                break;
            case "purple":
                //if (Math.abs(myServo.getPosition() - 0.720) > TOLERANCE) {
                    myServo.setPosition(0.720);
                //}
                break;
            case "blue":
                //if (Math.abs(myServo.getPosition() - 0.611) > TOLERANCE) {
                    myServo.setPosition(0.611);
                //}
                break;
            case "green":
                //if (Math.abs(myServo.getPosition() - 0.500) > TOLERANCE) {
                    myServo.setPosition(0.500);
                //}
                break;
            case "red":
                //if (Math.abs(myServo.getPosition() - 0.280) > TOLERANCE) {
                    myServo.setPosition(0.280);
                //}
                break;
            case "off":
            default:
                if (Math.abs(myServo.getPosition() - 0.0) > TOLERANCE) {
                    myServo.setPosition(0.0);
                }
        }
    }

    public boolean IsAlreadyOff() {
        return (myServo.getPosition() == 0);
    }

    public enum ColorOption {
        WHITE(1.000),
        PURPLE(0.720),
        GREEN(0.500),
        RED(0.280),
        OFF(0.000);

        private final double Position;

        ColorOption(double Position) {
            this.Position = Position;
        }

        public double getPosition() {
            return Position;
        }
    }

    public void SetColorName(ColorOption Color) {
        double TargetPosition = Color.getPosition();

        if (Math.abs(myServo.getPosition() - TargetPosition) > 0.02) {
            myServo.setPosition(TargetPosition);
        }
    }

}
