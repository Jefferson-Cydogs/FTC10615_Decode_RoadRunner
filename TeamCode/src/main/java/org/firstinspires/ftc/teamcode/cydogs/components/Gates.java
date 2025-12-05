package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;


public class Gates {
    private LinearOpMode opMode;
    private Servo leftGate;
    private Servo rightGate;
    private ColorLED LeftLED;
    private ColorLED RightLED;

    private final double leftGateOpen = 0.03;
    private final double leftGateClose = 0.84;
    private final double rightGateOpen = 0.07;
    private final double rightGateClose = 0.87;

    public Gates(LinearOpMode opMode)
    {
        this.opMode = opMode;

        leftGate = opMode.hardwareMap.get(Servo.class,"LeftGate");
        rightGate = opMode.hardwareMap.get(Servo.class,"RightGate");
        leftGate.setDirection(Servo.Direction.REVERSE);

        LeftLED = new ColorLED(opMode,"LeftLED");
        RightLED = new ColorLED(opMode,"RightLED");
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

    public void OpenBothGates()
    {
        leftGate.setPosition(leftGateOpen);
        rightGate.setPosition(rightGateOpen);
    }

    public void CloseBothGates()
    {
        leftGate.setPosition(leftGateClose);
        rightGate.setPosition(rightGateClose);
    }

    public void WaitForGateToOpen()
    {
        opMode.sleep(300);
    }

    public void CheckGatesStatus()
    {
        if (leftGate.getPosition() == leftGateClose) {
            LeftLED.SetColorName(ColorLED.ColorOption.YELLOW);
        } else {
            LeftLED.SetColorName(ColorLED.ColorOption.OFF);
        }
        if (rightGate.getPosition() == rightGateClose) {
            RightLED.SetColorName(ColorLED.ColorOption.YELLOW);
        } else {
            RightLED.SetColorName(ColorLED.ColorOption.OFF);
        }
    }

}
