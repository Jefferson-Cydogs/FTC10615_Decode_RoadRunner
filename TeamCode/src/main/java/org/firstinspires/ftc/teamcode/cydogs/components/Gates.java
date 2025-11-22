package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;


public class Gates {
    private LinearOpMode opMode;
    private Servo leftGate;
    private Servo rightGate;

    private double rightGateClose = 0.87;
    private double rightGateOpen = 0.07;
    private double leftGateClose = 0.84;
    private double leftGateOpen = 0.03;

    // left gate is now on port 5 of control.  was port 0 of expansion
    public Gates(LinearOpMode opMode)
    {
        this.opMode = opMode;

        leftGate = opMode.hardwareMap.get(Servo.class,"LeftGate");
        rightGate = opMode.hardwareMap.get(Servo.class,"RightGate");

        leftGate.setDirection(Servo.Direction.REVERSE);
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
    public void OpenBothGates()
    {
        rightGate.setPosition(rightGateOpen);
        leftGate.setPosition(leftGateOpen);
    }
    public void CloseBothGates()
    {
        leftGate.setPosition(leftGateClose);
        rightGate.setPosition(rightGateClose);
    }
    public void CloseRightGate()
    {
        rightGate.setPosition(rightGateClose);
    }

    public void WaitForGateToOpen()
    {
        opMode.sleep(300);
    }


}
