package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class DriverConfigQuestions {

    public static int AskStartWaitTime(LinearOpMode myOpMode) {
        int delaySeconds = 0;

        if (myOpMode.opModeInInit()) {


            while (myOpMode.opModeInInit()) {
                myOpMode.telemetry.addLine("Driver,");
                myOpMode.telemetry.addLine("To INCREASE starting wait time, press DPAD UP");
                myOpMode.telemetry.addLine("To DECREASE starting wait time, press DPAD DOWN");
                myOpMode.telemetry.addLine("To complete selection, press B");
                myOpMode.telemetry.addData("Current Delay Time:", delaySeconds);
                myOpMode.sleep(50);
                myOpMode.telemetry.update();
                if (myOpMode.gamepad1.b) {
                    return delaySeconds;
                }
                if (myOpMode.isStarted()) {
                    return delaySeconds;
                }
                if (myOpMode.gamepad1.dpad_up) {
                    delaySeconds++;
                    myOpMode.sleep(150);
                } else if (myOpMode.gamepad1.dpad_down) {
                    delaySeconds--;
                }
            }


        }
        return delaySeconds;
    }
}
