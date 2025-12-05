package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;


public class Gates {
    private LinearOpMode opMode;
    private Servo leftGate;
    private Servo rightGate;
    private ColorLED LauncherLED;

    private final double leftGateOpen = 0.03;
    private final double leftGateClose = 0.85;
    private final double rightGateOpen = 0.07;
    private final double rightGateClose = 0.88;

    public Gates(LinearOpMode opMode)
    {
        this.opMode = opMode;

        leftGate = opMode.hardwareMap.get(Servo.class,"LeftGate");
        rightGate = opMode.hardwareMap.get(Servo.class,"RightGate");
        leftGate.setDirection(Servo.Direction.REVERSE);

        LauncherLED = new ColorLED(opMode,"LauncherLED");
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
        if ((leftGate.getPosition() == leftGateClose) && (rightGate.getPosition() == rightGateClose)) {
            LauncherLED.SetColorName(ColorLED.ColorOption.YELLOW);
        } else {
            LauncherLED.SetColorName(ColorLED.ColorOption.OFF);
        }
    }

}
