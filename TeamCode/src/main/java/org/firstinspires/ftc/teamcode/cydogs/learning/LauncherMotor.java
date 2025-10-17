package org.firstinspires.ftc.teamcode.cydogs.learning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class LauncherMotor {
    public static final double MOTOR_MIN = -1.0;
    public static final double MOTOR_MAX = 1.0;
    private LinearOpMode opMode;
    private DcMotor myMotor;

    public LauncherMotor(LinearOpMode opMode, String LauncherMotor) {
        this.opMode = opMode;
        myMotor = opMode.hardwareMap.get(DcMotor.class, LauncherMotor);
        myMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void runAtPower(double power, long waitMs) {
        power = Math.max(MOTOR_MIN, Math.min(MOTOR_MAX, power));
        myMotor.setPower(power);

        opMode.sleep(waitMs);

        myMotor.setPower(0);
    }
}
