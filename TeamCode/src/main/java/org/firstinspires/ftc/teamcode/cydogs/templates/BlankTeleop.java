package org.firstinspires.ftc.teamcode.cydogs.templates;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp
public class BlankTeleop extends LinearOpMode {

    // declare variables here
    //
    private DcMotor BackLeftWheel;
    private DcMotor FrontLeftWheel;
    private DcMotor BackRightWheel;
    private DcMotor FrontRightWheel;
    private float gamepad1_RightStickYValue;
    private float gamepad1_RightStickXValue;
    private float gamepad1_LeftStickYValue;
    private float gamepad1_LeftStickXValue;
    private float gamepad1_TriggersValue;
    private double Straight;
    private double Strafe;
    private double Rotate;
    private double FastStraight;
    private double FastStrafe;
    private double highSpeedDrive = 0.8;
    private double lowSpeedDrive = 0.3;
    private double rotateSpeedDrive = 0.7;


    @Override
    public void runOpMode() {

        //    Do initialization things here
        initializeWheels();
        initializeDevices();
        initializePositions();


        while (opModeIsActive()) {
            // do op mode things here
            driveChassis();
            manageDriverControls();

        }
    }

    private void manageDriverControls()
    {
        if(gamepad1.triangle)
        {
            // do something if triangle is pushed
        }
        if(gamepad1.square)
        {
            // do something if square is pushed
        }

    }

    private void initializeWheels()
    {
        BackLeftWheel = hardwareMap.get(DcMotor.class, "BackLeftWheel");
        FrontLeftWheel = hardwareMap.get(DcMotor.class, "FrontLeftWheel");
        BackRightWheel = hardwareMap.get(DcMotor.class, "BackRightWheel");
        FrontRightWheel = hardwareMap.get(DcMotor.class, "FrontRightWheel");

        // INITIALIZATION BLOCKS:
        // > Reverse motors'/servos' direction as needed
        BackLeftWheel.setDirection(DcMotor.Direction.REVERSE);
        FrontLeftWheel.setDirection(DcMotor.Direction.REVERSE);
        BackRightWheel.setDirection(DcMotor.Direction.FORWARD);
        FrontRightWheel.setDirection(DcMotor.Direction.FORWARD);
        // > Set motors' ZeroPower behavior
        BackLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BackRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FrontLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FrontRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        // > Clear Encoders of prior data
        FrontLeftWheel.setPower(0);
        FrontRightWheel.setPower(0);
        BackLeftWheel.setPower(0);
        BackRightWheel.setPower(0);
        BackLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // > Set some motors' modes different from RUN_WITHOUT_ENCODER (default)
        BackLeftWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRightWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontLeftWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRightWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void initializeDevices()
    {

    }

    private void initializePositions()
    {

    }

    private void driveChassis()
    {
        gamepad1_RightStickYValue = -gamepad1.right_stick_y;
        gamepad1_RightStickXValue = gamepad1.right_stick_x;
        gamepad1_LeftStickYValue = -gamepad1.left_stick_y;
        gamepad1_LeftStickXValue = gamepad1.left_stick_x;
        gamepad1_TriggersValue = gamepad1.right_trigger - gamepad1.left_trigger;

        if (gamepad1_RightStickYValue != 0 || gamepad1_RightStickXValue != 0 || gamepad1_LeftStickYValue != 0 || gamepad1_LeftStickXValue != 0 || gamepad1_TriggersValue != 0) {
            // Set robot's move forward(+) or backwards(-) power
            Straight = lowSpeedDrive * (0.75 * Math.pow(gamepad1_LeftStickYValue, 3) + 0.25 * gamepad1_LeftStickYValue);
            // Set robot's strafe right(+) or left(-) power
            Strafe = lowSpeedDrive * (0.75 * Math.pow(gamepad1_LeftStickXValue, 3) + 0.25 * gamepad1_LeftStickXValue);
            // Set robot's clockwise(+) or counter-clockwise(-) rotation power
            Rotate = rotateSpeedDrive * (0.75 * Math.pow(gamepad1_TriggersValue, 3) + 0.25 * gamepad1_TriggersValue);
            // Set robot's fast move forward(+) or backwards(-) power
            FastStraight = highSpeedDrive * gamepad1_RightStickYValue;
            // Set robot's fast strafe right(+) or left(-) power
            FastStrafe = highSpeedDrive * gamepad1_RightStickXValue;
            BackLeftWheel.setPower((((Straight + FastStraight) - Strafe) - FastStrafe) + Rotate);
            BackRightWheel.setPower((Straight + FastStraight + Strafe + FastStrafe) - Rotate);
            FrontLeftWheel.setPower(Straight + FastStraight + Strafe + FastStrafe + Rotate);
            FrontRightWheel.setPower((((Straight + FastStraight) - Strafe) - FastStrafe) - Rotate);
        } else {
            // Stop all motors if their controls are not touched
            BackLeftWheel.setPower(0);
            BackRightWheel.setPower(0);
            FrontLeftWheel.setPower(0);
            FrontRightWheel.setPower(0);
        }
    }
}
