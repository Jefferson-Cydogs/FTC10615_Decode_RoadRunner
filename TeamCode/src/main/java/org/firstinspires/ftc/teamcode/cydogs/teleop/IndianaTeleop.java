package org.firstinspires.ftc.teamcode.cydogs.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.cydogs.components.ColorLED;


@TeleOp
public class IndianaTeleop extends LinearOpMode {

    // declare variables here
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
    private double highSpeedDrive = 0.7;
    private double lowSpeedDrive = 0.3;
    private double rotateSpeedDrive = 0.5;

    private CRServo LeftFeeder;
    private CRServo RightFeeder;
    private CRServo Intake;
    private Servo IntakeGate;
    private DcMotor LeftLauncher;
    private DcMotor RightLauncher;
    private ColorSensor BackParkingSensor ;
    private ColorSensor LeftParkingSensor ;
    private ColorSensor LeftIntakeSensor ;
    private ColorSensor RightIntakeSensor ;

    // LeftFeeder, RightFeeder
    // Intake
    // LeftIntakeSensor
    // RightIntakeSensor
    // LeftLauncher
    // RightLauncher
    // IntakeGate
    // BackParkingSensor
    // LeftParkingSensor
    // LauncherLED



    @Override
    public void runOpMode() {

        // Execute initialization actions here
        initializeWheels();
        initializeDevices();
        initializePositions();

        ColorLED CL = new ColorLED(this,"LauncherLED");
        waitForStart();
        while (opModeIsActive()) {
            // Execute OpMode actions here
            CL.SetColor(.7,0);
            // .5 is green
            // .7 is
            driveChassis();
            manageDriverControls();
            manageManipulatorControls();

        }
    }

    private void manageDriverControls()
    {
        if(gamepad1.y)
        {
            // do something if triangle is pushed
        }
        else if(gamepad1.square)
        {
            // do something if square is pushed
        }

    }

    private void manageManipulatorControls()
    {
        if(gamepad1.right_bumper)
        {
            Intake.setPower(.8);

        }
        else
        {
            Intake.setPower(0);
        }

        if(gamepad1.dpad_up)
        {
            RightLauncher.setPower(.6);
            LeftLauncher.setPower(.6);
            // need to indicate when full power

        }
        if(gamepad1.dpad_down)
        {
            RightLauncher.setPower(0);
            LeftLauncher.setPower(0);
        }
        if(gamepad1.x)
        {
            LeftFeeder.setPower(.6);
            sleep(200);
            LeftFeeder.setPower(0);
        }
        if(gamepad1.b)
        {
            RightFeeder.setPower(.6);
            sleep(200);
            RightFeeder.setPower(0);

        }
    }
        // lED on back for when launchers are at full speed
    private void initializeWheels()
    {
        BackLeftWheel = hardwareMap.get(DcMotor.class, "BackLeftWheel");
        FrontLeftWheel = hardwareMap.get(DcMotor.class, "FrontLeftWheel");
        BackRightWheel = hardwareMap.get(DcMotor.class, "BackRightWheel");
        FrontRightWheel = hardwareMap.get(DcMotor.class, "FrontRightWheel");

        // 0 is left back
        // 1 is front left
        // 2 is front right
        // 3 is right back

        // INITIALIZATION BLOCKS:
        // > Reverse motors'/servos' direction as needed. FORWARD is default.
        BackLeftWheel.setDirection(DcMotor.Direction.FORWARD);
        FrontLeftWheel.setDirection(DcMotor.Direction.REVERSE);
        BackRightWheel.setDirection(DcMotor.Direction.REVERSE);
        FrontRightWheel.setDirection(DcMotor.Direction.FORWARD);
        // > Set motors' ZeroPower behavior
        BackLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BackRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FrontLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FrontRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        // > Ensure motors are stopped; necessary if motors will be configured to RUN_USING_ENCODER (for Velocity instead of Power)
        FrontLeftWheel.setPower(0);
        FrontRightWheel.setPower(0);
        BackLeftWheel.setPower(0);
        BackRightWheel.setPower(0);
        // > Clear Encoders of prior data; necessary if motors will be configured to RUN_USING_ENCODER (for Velocity instead of Power)
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

        LeftLauncher = hardwareMap.get(DcMotor.class, "LeftLauncher");
        RightLauncher = hardwareMap.get(DcMotor.class, "RightLauncher");
        LeftFeeder =hardwareMap.get(CRServo.class, "LeftFeeder");
        RightFeeder =hardwareMap.get(CRServo.class, "RightFeeder");
        Intake =hardwareMap.get(CRServo.class, "Intake");
        Intake.setDirection(CRServo.Direction.REVERSE);
        RightFeeder.setDirection(CRServo.Direction.REVERSE);

        BackParkingSensor = hardwareMap.get(ColorSensor.class,"BackParkingSensor");
        LeftParkingSensor = hardwareMap.get(ColorSensor.class,"LeftParkingSensor");
        LeftIntakeSensor = hardwareMap.get(ColorSensor.class,"LeftIntakeSensor");
        RightIntakeSensor = hardwareMap.get(ColorSensor.class,"RightIntakeSensor");

        LeftLauncher.setDirection(DcMotor.Direction.REVERSE);
        LeftLauncher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        RightLauncher.setDirection(DcMotor.Direction.REVERSE);
        RightLauncher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);



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

        if (gamepad1_RightStickYValue != 0 || gamepad1_RightStickXValue != 0 || gamepad1_LeftStickYValue != 0 || gamepad1_LeftStickXValue != 0 || gamepad1_TriggersValue != 0)
        {
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
            // MOve all wheels based on the above calculations, using formulas for Mecanum wheels.
            BackLeftWheel.setPower(Straight + FastStraight - Strafe - FastStrafe + Rotate);
            BackRightWheel.setPower(Straight + FastStraight + Strafe + FastStrafe - Rotate);
            FrontLeftWheel.setPower(Straight + FastStraight + Strafe + FastStrafe + Rotate);
            FrontRightWheel.setPower(Straight + FastStraight - Strafe - FastStrafe - Rotate);
        }
        else
        {
            // Stop all motors if their controls are not touched
            BackLeftWheel.setPower(0);
            BackRightWheel.setPower(0);
            FrontLeftWheel.setPower(0);
            FrontRightWheel.setPower(0);
        }
    }
}
