package org.firstinspires.ftc.teamcode.cydogs;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
public class practiceservo {
    public static final double Servo_Min = 0.0;
    public static final double Servo_Max = 0.5;

    private Servo wonderServo;
    private LinearOpMode operatorMode;

    public void ExampleServo(LinearOpMode operatorMode, String servoName){
        this.operatorMode = operatorMode;
        wonderServo = operatorMode.hardwareMap.get(Servo.class, servoName);

    }

    public void moveTo(double position,double waitMs){
        position = Math.max(Servo_Min, Math.min(Servo_Max,position));

    }
}



