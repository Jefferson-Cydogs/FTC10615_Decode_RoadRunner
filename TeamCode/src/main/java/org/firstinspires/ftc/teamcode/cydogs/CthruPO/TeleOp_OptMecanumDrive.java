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
        leftFrontWheel = hardwareMap.get(DcMotor.class, "FrontLeftWheel");
        rightFrontWheel = hardwareMap.get(DcMotor.class, "FrontRightWheel");
        leftBackWheel = hardwareMap.get(DcMotor.class, "BackLeftWheel");
        rightBackWheel = hardwareMap.get(DcMotor.class, "BackRightWheel");

        // Put initialization blocks here.
        createVariables();
        initMotors();

        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.

            while (opModeIsActive()) {
                // Put loop blocks here.
                mecanumDrive(-gamepad1.right_stick_y, gamepad1.right_stick_x, -gamepad1.left_stick_y, gamepad1.left_stick_x,gamepad1.right_trigger - gamepad1.left_trigger);
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
    private void mecanumDrive(float forward, float strafe, float slowForward, float slowStrafe, float rotate) {
        double deadZone = 0.05;
        double highSpeed = 0.8;
        double lowSpeed = 0.3;
        double leftFrontPower;
        double rightFrontPower;
        double leftBackPower;
        double rightBackPower;

        forward = abs(forward) > deadZone ? (float) (highSpeed * ((0.75 * pow(forward, 3)) + (0.25 * forward))) : 0;
        strafe = abs(strafe) > deadZone ? (float) (highSpeed * ((0.75 * pow(strafe, 3)) + (0.25 * strafe))) : 0;
        slowForward = abs(slowForward) > deadZone ? (float) (lowSpeed * slowForward) : 0;
        slowStrafe = abs(slowStrafe) > deadZone ? (float) (lowSpeed * slowStrafe) : 0;
        rotate = abs(rotate) > deadZone ? (float) (highSpeed * ((0.75 * pow(rotate, 3)) + (0.25 * rotate))) : 0;
        leftFrontPower = forward + slowForward + strafe + slowStrafe + rotate;
        rightFrontPower = forward + slowForward - strafe - slowStrafe - rotate;
        leftBackPower = forward + slowForward - strafe - slowStrafe + rotate;
        rightBackPower = forward + slowForward + strafe + slowStrafe - rotate;
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
