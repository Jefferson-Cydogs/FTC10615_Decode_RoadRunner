package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;


public class ColorLED {
    final double TOLERANCE = 0.02;

    private LinearOpMode opMode;
    private Servo myServo;

    public ColorLED(LinearOpMode opMode, String LEDName)
    {
        this.opMode = opMode;

        myServo = opMode.hardwareMap.get(Servo.class, LEDName);
    }

    public void SetColorByName(String ColorName)
    {
        switch (ColorName.toLowerCase()) {
            case "white":
                myServo.setPosition(1.0);
                break;
            case "purple":
                myServo.setPosition(0.720);
                break;
            case "blue":
                myServo.setPosition(0.611);
                break;
            case "green":
                myServo.setPosition(0.500);
                break;
            case "red":
                myServo.setPosition(0.280);
                break;
            case "off":
            default:
                if (myServo.getPosition() > TOLERANCE) {
                    myServo.setPosition(0.0);
                }
        }
    }

    public enum ColorOption {
        WHITE(1.000),
        PURPLE(0.720),
        BLUE(0.611),
        GREEN(0.500),
        YELLOW(0.377),
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

        if (Math.abs(myServo.getPosition() - TargetPosition) > TOLERANCE) {
            myServo.setPosition(TargetPosition);
        }
    }

}
