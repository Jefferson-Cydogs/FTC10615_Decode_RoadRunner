package org.firstinspires.ftc.teamcode.cydogs.CthruPO;

import static java.lang.Math.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class TeleOp_OptMecanumDrive extends LinearOpMode {

    private DcMotor leftFrontWheel;
    private DcMotor rightFrontWheel;
    private DcMotor leftBackWheel;
    private DcMotor rightBackWheel;

    @Override
    public void runOpMode() {
        leftFrontWheel = hardwareMap.get(DcMotor.class, "leftFrontWheel");
        rightFrontWheel = hardwareMap.get(DcMotor.class, "rightFrontWheel");
        leftBackWheel = hardwareMap.get(DcMotor.class, "leftBackWheel");
        rightBackWheel = hardwareMap.get(DcMotor.class, "rightBackWheel");

        // Put initialization blocks here.
        createVariables();
        initMotors();

        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.

            while (opModeIsActive()) {
                // Put loop blocks here.
                mecanumDrive(-gamepad1.right_stick_y, gamepad1.right_stick_x, gamepad1.right_trigger - gamepad1.left_trigger);
                //telemetry.update();
            }
        }
    }

    /**
     * Describe this function...
     */
    private void createVariables() {

    }

    /**
     * This initializes the motors and servos
     */
    private void initMotors() {
        leftBackWheel.setDirection(DcMotor.Direction.REVERSE);
        leftFrontWheel.setDirection(DcMotor.Direction.REVERSE);
        leftFrontWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * This takes input from the joysticks, and applies power to all 4 drive motors
     * to move the robot as requested by the driver.
     */
    private void mecanumDrive(float forward, float strafe, float rotate) {
        double deadZone = 0.05;
        double leftFrontPower;
        double rightFrontPower;
        double leftBackPower;
        double rightBackPower;

        forward = abs(forward) > deadZone ? (float) (0.8 * ((0.75 * pow(forward, 3)) + (0.25 * forward))) : 0;
        strafe = abs(strafe) > deadZone ? (float) (0.8 * ((0.75 * pow(strafe, 3)) + (0.25 * strafe))) : 0;
        rotate = abs(rotate) > deadZone ? (float) (0.8 * ((0.75 * pow(rotate, 3)) + (0.25 * rotate))) : 0;
        leftFrontPower = forward + strafe + rotate;
        rightFrontPower = forward - strafe - rotate;
        leftBackPower = forward - strafe + rotate;
        rightBackPower = forward + strafe - rotate;
        // Normalize powers if any exceed 1.0
        double maxPower = max(abs(leftFrontPower),
                max(abs(rightFrontPower),
                        max(abs(leftBackPower), abs(rightBackPower))));
        if (maxPower > 1.0) {
            leftFrontPower /= maxPower;
            rightFrontPower /= maxPower;
            leftBackPower /= maxPower;
            rightBackPower /= maxPower;
        }
        leftFrontWheel.setPower(leftFrontPower);
        rightFrontWheel.setPower(rightFrontPower);
        leftBackWheel.setPower(leftBackPower);
        rightBackWheel.setPower(rightBackPower);
    }
}
