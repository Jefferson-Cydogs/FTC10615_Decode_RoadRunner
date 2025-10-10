package org.firstinspires.ftc.teamcode.cydogs.StarterBot;

import static java.lang.Math.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

//@TeleOp(name = "Teleop_StarterBotMecanum")
public class TeleOp_StarterBotMecanum extends LinearOpMode {

    private DcMotor leftFrontWheel;
    private DcMotor rightFrontWheel;
    private DcMotor leftBackWheel;
    private DcMotor rightBackWheel;
    private DcMotor launcher;
    private CRServo leftFeeder;
    private CRServo rightFeeder;

    String IDLE;
    String SPIN_UP;
    String LAUNCH;
    String LAUNCHING;
    String launchState;
    int LAUNCHER_TARGET_VELOCITY;
    int LAUNCHER_MIN_VELOCITY;
    ElapsedTime launchTime;

    /**
     * This file includes a teleop (driver-controlled) file for the goBILDA® StarterBot for the
     * 2025-2026 FIRST® Tech Challenge season DECODE™. It leverages a differential/Skid-Steer
     * system for robot mobility, one high-speed motor driving two "launcher wheels",
     * and two servos which feed that launcher. Likely the most niche concept we'll
     * leverage in this example is closed-loop motor velocity control. This control
     * method reads the current speed as reported by the motor's encoder and applies
     * a varying amount of power to reach, and then hold a target velocity. The FTC
     * SDK calls this control method "RUN_USING_ENCODER". This contrasts to the
     * default "RUN_WITHOUT_ENCODER" where you control the power applied to the
     * motor directly. Since the dynamics of a launcher wheel system varies greatly
     * from those of most other FTC mechanisms, we will also need to adjust the
     * "PIDF" coefficients with some that are a better fit for our application.
     */
    @Override
    public void runOpMode() {
        leftFrontWheel = hardwareMap.get(DcMotor.class, "leftFrontWheel");
        rightFrontWheel = hardwareMap.get(DcMotor.class, "rightFrontWheel");
        leftBackWheel = hardwareMap.get(DcMotor.class, "leftBackWheel");
        rightBackWheel = hardwareMap.get(DcMotor.class, "rightBackWheel");
        launcher = hardwareMap.get(DcMotor.class, "launcher");
        leftFeeder = hardwareMap.get(CRServo.class, "leftFeeder");
        rightFeeder = hardwareMap.get(CRServo.class, "rightFeeder");

        // Put initialization blocks here.
        createVariables();
        initMotors();

        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.

            while (opModeIsActive()) {
                // Put loop blocks here.
                mecanumDrive(-gamepad1.right_stick_y, gamepad1.right_stick_x, gamepad1.right_trigger - gamepad1.left_trigger);
                if (gamepad1.y) {
                    ((DcMotorEx) launcher).setVelocity(LAUNCHER_TARGET_VELOCITY);
                } else if (gamepad1.b) {
                    ((DcMotorEx) launcher).setVelocity(0);
                }
                launch(gamepad1.rightBumperWasPressed());
                telemetry.addData("launchState", launchState);
                telemetry.addData("Launcher Motor Velocity", ((DcMotorEx) launcher).getVelocity());
                telemetry.update();
            }
        }
    }

    /**
     * Describe this function...
     */
    private void createVariables() {
        IDLE = "IDLE";
        SPIN_UP = "SPIN_UP";
        LAUNCH = "LAUNCH";
        LAUNCHING = "LAUNCHING";
        launchState = IDLE;
        LAUNCHER_TARGET_VELOCITY = 1500;
        LAUNCHER_MIN_VELOCITY = 1450;
        launchTime = new ElapsedTime();
    }

    /**
     * This initializes the motors and servos
     */
    private void initMotors() {
        leftBackWheel.setDirection(DcMotor.Direction.REVERSE);
        leftFrontWheel.setDirection(DcMotor.Direction.REVERSE);
        launcher.setDirection(DcMotor.Direction.FORWARD);
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFrontWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFeeder.setDirection(CRServo.Direction.REVERSE);
        leftFeeder.setPower(0);
        rightFeeder.setPower(0);
        ((DcMotorEx) launcher).setVelocityPIDFCoefficients(300, 0, 0, 10);
    }

    /**
     * This takes input from the joysticks, and applies power to all 4 drive motors
     * to move the robot as requested by the driver.
     */
    private void mecanumDrive(float forward, float strafe, float rotate) {
        double leftFrontPower;
        double rightFrontPower;
        double leftBackPower;
        double rightBackPower;
        double maxPower;

        forward = (float) (0.8 * (0.75 * pow(forward, 3) + (0.25 * forward)));
        strafe = (float) (0.8 * (0.75 * pow(strafe, 3) + 0.25 * strafe));
        rotate = (float) (0.8 * (0.75 * pow(rotate, 3) + 0.25 * rotate));
        leftFrontPower = forward + strafe + rotate;
        rightFrontPower = forward - strafe - rotate;
        leftBackPower = forward - strafe + rotate;
        rightBackPower = forward + strafe - rotate;
        // Normalize powers if any exceed 1.0
        maxPower = max(
            abs(leftFrontPower),
            max(abs(rightFrontPower),
                max(abs(leftBackPower), abs(rightBackPower)))
        );
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

    /**
     * This takes input from the driver pressing a button to launch the artifact
     */
    private void launch(boolean shotRequested) {
        if (launchState.equals(IDLE)) {
            if (shotRequested) {
                launchState = SPIN_UP;
            }
        } else if (launchState.equals(SPIN_UP)) {
            ((DcMotorEx) launcher).setVelocity(LAUNCHER_TARGET_VELOCITY);
            if (((DcMotorEx) launcher).getVelocity() > LAUNCHER_MIN_VELOCITY) {
                launchState = LAUNCH;
            }
        } else if (launchState.equals(LAUNCH)) {
            leftFeeder.setPower(1);
            rightFeeder.setPower(1);
            launchTime.reset();
            launchState = LAUNCHING;
        } else if (launchState.equals(LAUNCHING)) {
            if (launchTime.seconds() > 0.2) {
                leftFeeder.setPower(0);
                rightFeeder.setPower(0);
                launchState = IDLE;
            }
        }
    }
}
