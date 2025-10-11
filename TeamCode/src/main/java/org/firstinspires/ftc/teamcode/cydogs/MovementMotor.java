package org.firstinspires.ftc.teamcode.cydogs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class MovementMotor {
    private LinearOpMode opMode;
    private DcMotor myMotor;
    public static final double MOTOR_MIN=-1.0;
    public static final double MOTOR_MAX=1.0;
    public MovementMotor(LinearOpMode opMode, String MovementMotor) {
        this.opMode = opMode;
        myMotor = opMode.hardwareMap.get(DcMotor.class, MovementMotor);
        myMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        myMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        myMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
        public void runToPosition(int targetPosition, double power) {
            power=Math.max(MOTOR_MIN, Math.min(MOTOR_MAX,power));
            myMotor.setTargetPosition(targetPosition);
            myMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            myMotor.setPower(Math.abs(power));

        }
    }




