package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;


public class Gates {
    private LinearOpMode opMode;
    private Servo leftGate;
    private Servo rightGate;

    private double rightGateClose = 0.86;
    private double rightGateOpen = 0.06;
    private double leftGateClose = 0.83;
    private double leftGateOpen = 0.07;
    public Gates(LinearOpMode opMode)
    {
        this.opMode = opMode;

        leftGate = opMode.hardwareMap.get(Servo.class,"LeftGate");
        rightGate = opMode.hardwareMap.get(Servo.class,"RightGate");

        //rightGate.setDirection(CRServo.Direction.REVERSE);
    }

    public void OpenLeftGate()
    {
        leftGate.setPosition(leftGateOpen);
    }
    public void CloseLeftGate()
    {
        leftGate.setPosition(leftGateClose);
    }
    public void OpenRightGate()
    {
        rightGate.setPosition(rightGateOpen);
    }
    public void CloseRightGate()
    {
        rightGate.setPosition(rightGateClose);
    }


}
