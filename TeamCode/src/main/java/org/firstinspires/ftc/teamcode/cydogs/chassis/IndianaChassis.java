package org.firstinspires.ftc.teamcode.cydogs.chassis;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import static java.lang.Math.*;

public class IndianaChassis {
    public DcMotor FrontLeftWheel;
    public DcMotor FrontRightWheel;
    public DcMotor BackLeftWheel;
    public DcMotor BackRightWheel;
    private double highSpeedDrive = 0.7;
    private double lowSpeedDrive = 0.3;
    private double rotateSpeedDrive = 0.5;
    private final double DriveControlDeadZone = 0.05;
    private float gamepad1_RightStickYValue;
    private float gamepad1_RightStickXValue;
    private float gamepad1_LeftStickYValue;
    private float gamepad1_LeftStickXValue;
    private float gamepad1_TriggersValue;
    private double FastStraight;
    private double Straight;
    private double FastStrafe;
    private double Strafe;
    private double Rotate;
    private double FrontLeftPower;
    private double FrontRightPower;
    private double BackLeftPower;
    private double BackRightPower;
    private LinearOpMode myOpMode;
    private final int WheelsDiameter =104;
    private final int MotorsRPM = 435;
    private final double MotorsTicksPerRevolution =384.5;

    // mmPer90DegreeRotation needs to be configured for each robot based on it's chassis size
    private double mmPer90DegreesRotation=785;

    private double strafeCompensation = 1.081;

    public static final int OneTileMM = 610;

    // This is the constructor for the class.  It takes a parameter for currentOp, which allows
    //   it to store and use the current op mode.  The four wheels are initialized here.
    public IndianaChassis(LinearOpMode currentOp)
    {
        // The op mode is important code provided by first.  It has the hardwareMap, sleep function,
        //   and telemetry functions.
        myOpMode = currentOp;
        HardwareMap hardwareMap = myOpMode.hardwareMap;

        // This gets the devices from the configuration on the robot.
        //    This call basically says "Get me the thing called FrontRightWheel from the
        //    configuration, and trust me it can be mapped to the DCMotor class. If it's
        //    not a motor, then code later on will throw errors when it tries to do motor
        //    things with a non motor.
        FrontLeftWheel = hardwareMap.get(DcMotor.class, "FrontLeftWheel");
        FrontRightWheel = hardwareMap.get(DcMotor.class, "FrontRightWheel");
        BackLeftWheel = hardwareMap.get(DcMotor.class, "BackLeftWheel");
        BackRightWheel = hardwareMap.get(DcMotor.class, "BackRightWheel");

        FrontLeftWheel.setDirection(DcMotor.Direction.REVERSE);
        //FrontRightWheel.setDirection(DcMotor.Direction.FORWARD); * not needed as FORWARD is default
        //BackLeftWheel.setDirection(DcMotor.Direction.FORWARD); * not needed as FORWARD is default
        BackRightWheel.setDirection(DcMotor.Direction.REVERSE);
    }

    public void InitializeTeleop(double highSpeed, double lowSpeed, double rotateSpeed)
    {
        highSpeedDrive = highSpeed;
        lowSpeedDrive = lowSpeed;
        rotateSpeedDrive = rotateSpeed;

        // Set motors' ZeroPower behavior
        FrontLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FrontRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BackLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BackRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Clear Encoders of prior data; only necessary if motors will be configured to RUN_USING_ENCODER (for Velocity instead of Power)
        FrontLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // > Set some motors' modes different from RUN_WITHOUT_ENCODER (default); suggested if Auton was using RUN_TO_POSITION
        FrontLeftWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRightWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeftWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRightWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Ensure motors are stopped, for predictable behavior and avoiding unintended motion
        FrontLeftWheel.setPower(0);
        FrontRightWheel.setPower(0);
        BackLeftWheel.setPower(0);
        BackRightWheel.setPower(0);
    }

    public void InitializeAutonomous()
    {
        // Set motors' ZeroPower behavior
        FrontLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Clear Encoders of prior data
        FrontLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set the current position as initial one
        FrontLeftWheel.setTargetPosition(0);
        FrontRightWheel.setTargetPosition(0);
        BackLeftWheel.setTargetPosition(0);
        BackRightWheel.setTargetPosition(0);

        // Set some motors' modes different from RUN_WITHOUT_ENCODER (default)
        FrontLeftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackLeftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Ensure motors are stopped, for predictable behavior and avoiding unintended motion
        FrontLeftWheel.setPower(0);
        FrontRightWheel.setPower(0);
        BackLeftWheel.setPower(0);
        BackRightWheel.setPower(0);
    }

    public void TraditionalTeleopDrive()
    {
        gamepad1_RightStickYValue = -myOpMode.gamepad1.right_stick_y;
        gamepad1_RightStickXValue = myOpMode.gamepad1.right_stick_x;
        gamepad1_LeftStickYValue = -myOpMode.gamepad1.left_stick_y;
        gamepad1_LeftStickXValue = myOpMode.gamepad1.left_stick_x;
        gamepad1_TriggersValue = myOpMode.gamepad1.right_trigger - myOpMode.gamepad1.left_trigger;

        if (gamepad1_RightStickYValue != 0 || gamepad1_RightStickXValue != 0 || gamepad1_LeftStickYValue != 0 || gamepad1_LeftStickXValue != 0 || gamepad1_TriggersValue != 0)
        {
            // Set robot's fast move forward(+) or backwards(-) power
            FastStraight = highSpeedDrive * (0.75 * Math.pow(gamepad1_RightStickYValue, 3) + 0.25 * gamepad1_RightStickYValue);
            // Set robot's move forward(+) or backwards(-) power
            Straight = lowSpeedDrive * gamepad1_LeftStickYValue;
            // Set robot's fast strafe right(+) or left(-) power
            FastStrafe = highSpeedDrive * (0.75 * Math.pow(gamepad1_RightStickXValue, 3) + 0.25 * gamepad1_RightStickXValue);
            // Set robot's strafe right(+) or left(-) power
            Strafe = lowSpeedDrive * gamepad1_LeftStickXValue;
            // Set robot's clockwise(+) or counter-clockwise(-) rotation power
            Rotate = rotateSpeedDrive * (0.75 * Math.pow(gamepad1_TriggersValue, 3) + 0.25 * gamepad1_TriggersValue);
            // MOve all wheels based on the above calculations, using formulas for Mecanum wheels.
            FrontLeftWheel.setPower(FastStraight + Straight + FastStrafe + Strafe + Rotate);
            FrontRightWheel.setPower(FastStraight + Straight - FastStrafe - Strafe - Rotate);
            BackLeftWheel.setPower(FastStraight + Straight - FastStrafe - Strafe + Rotate);
            BackRightWheel.setPower(FastStraight + Straight + FastStrafe + Strafe - Rotate);
        }
        else
        {
            // Stop all motors if their controls are not touched
            FrontLeftWheel.setPower(0);
            FrontRightWheel.setPower(0);
            BackLeftWheel.setPower(0);
            BackRightWheel.setPower(0);
        }
    }

    public void OptimizedTeleopDrive()
    {
        Straight = -myOpMode.gamepad1.left_stick_y;
        FastStraight = -myOpMode.gamepad1.right_stick_y;
        Strafe = myOpMode.gamepad1.left_stick_x;
        FastStrafe = myOpMode.gamepad1.right_stick_x;
        Rotate = myOpMode.gamepad1.right_trigger - myOpMode.gamepad1.left_trigger;

        FastStraight = abs(FastStraight) > DriveControlDeadZone ? (float) (highSpeedDrive * (0.75 * pow(FastStraight, 3) + 0.25 * FastStraight)) : 0;
        FastStrafe = abs(FastStrafe) > DriveControlDeadZone ? (float) (highSpeedDrive * (0.75 * pow(FastStrafe, 3) + 0.25 * FastStrafe)) : 0;
        Rotate = abs(Rotate) > DriveControlDeadZone ? (float) (rotateSpeedDrive * (0.75 * pow(Rotate, 3) + 0.25 * Rotate)): 0;
        Straight = abs(Straight) > DriveControlDeadZone ? (float) (lowSpeedDrive * Straight) : 0;
        Strafe = abs(Strafe) > DriveControlDeadZone ? (float) (lowSpeedDrive * Strafe) : 0;
        FrontLeftPower = FastStraight + Straight + FastStrafe + Strafe + Rotate;
        FrontRightPower = FastStraight + Straight - FastStrafe - Strafe - Rotate;
        BackLeftPower = FastStraight + Straight - FastStrafe - Strafe + Rotate;
        BackRightPower = FastStraight + Straight + FastStrafe + Strafe - Rotate;
        // Normalize powers if any exceed 1.0
        double maxPower = max(abs(FrontLeftPower),
                              max(abs(FrontRightPower),
                                  max(abs(BackLeftPower), abs(BackRightPower))));
        if (maxPower > 1.0) {
            FrontLeftPower /= maxPower;
            FrontRightPower /= maxPower;
            BackLeftPower /= maxPower;
            BackRightPower /= maxPower;
        }
        FrontLeftWheel.setPower(FrontLeftPower);
        FrontRightWheel.setPower(FrontRightPower);
        BackLeftWheel.setPower(BackLeftPower);
        BackRightWheel.setPower(BackRightPower);
    }

    /**
     * Move forward(+) or backwards(-) until reaching Position
     */
    public void MoveStraight(int mmToTarget, double VelocityPercentage, int WaitTime)
    {
        double TicksToTarget;
        double TicksPerSecond;

        TicksToTarget = (mmToTarget / (WheelsDiameter * Math.PI)) * MotorsTicksPerRevolution;
        TicksPerSecond = VelocityPercentage * ((MotorsRPM / 60) * MotorsTicksPerRevolution);
        //myOpMode.telemetry.addData("ticksToTarget", TicksToTarget);
        //myOpMode.telemetry.update();
        FrontLeftWheel.setTargetPosition((int) (FrontLeftWheel.getCurrentPosition() + TicksToTarget));
        FrontRightWheel.setTargetPosition((int) (FrontRightWheel.getCurrentPosition() + TicksToTarget));
        BackLeftWheel.setTargetPosition((int) (BackLeftWheel.getCurrentPosition() + TicksToTarget));
        BackRightWheel.setTargetPosition((int) (BackRightWheel.getCurrentPosition() + TicksToTarget));

        // The (DcMotorEx) is called casting.  It says take the FrontLeftWheel, and while we know it's a DcMotor
        //   treat it like a DcMotorEx.  DcMotorEx has more functionality than DcMotor (such as setVelocity).  Not all hardware
        //   can be treated as a DcMotorEx, but ours can.  DcMotorEx inherits from DcMotor, so it has all the
        //   functionality of DcMotor plus more.  We should figure out if we can just declare these as DcMotorEx in
        //   the first place.  After tournaments.
        ((DcMotorEx) FrontLeftWheel).setVelocity(TicksPerSecond);
        ((DcMotorEx) FrontRightWheel).setVelocity(TicksPerSecond);
        ((DcMotorEx) BackLeftWheel).setVelocity(TicksPerSecond);
        ((DcMotorEx) BackRightWheel).setVelocity(TicksPerSecond);

        while (myOpMode.opModeIsActive() &&
               FrontLeftWheel.isBusy() && FrontRightWheel.isBusy() && BackLeftWheel.isBusy() && BackRightWheel.isBusy())
        {
            // Do nothing until at least 1 wheel reaches TargetPosition
        }
        myOpMode.sleep(WaitTime);
    }

    // Strafes right until reaching Position
    public void StrafeRight(int mmToTarget, double VelocityPercentage, int WaitTime)
    {
        double TicksToTarget;
        double TicksPerSecond;

        TicksToTarget = ((mmToTarget / (WheelsDiameter * Math.PI)) * MotorsTicksPerRevolution) * strafeCompensation;
        TicksPerSecond = VelocityPercentage * ((MotorsRPM / 60) * MotorsTicksPerRevolution);
        FrontLeftWheel.setTargetPosition((int) (FrontLeftWheel.getCurrentPosition() + TicksToTarget));
        FrontRightWheel.setTargetPosition((int) (FrontRightWheel.getCurrentPosition() - TicksToTarget));
        BackLeftWheel.setTargetPosition((int) (BackLeftWheel.getCurrentPosition() - TicksToTarget));
        BackRightWheel.setTargetPosition((int) (BackRightWheel.getCurrentPosition() + TicksToTarget));

        ((DcMotorEx) FrontLeftWheel).setVelocity(TicksPerSecond);
        ((DcMotorEx) FrontRightWheel).setVelocity(TicksPerSecond);
        ((DcMotorEx) BackLeftWheel).setVelocity(TicksPerSecond);
        ((DcMotorEx) BackRightWheel).setVelocity(TicksPerSecond);

        while (myOpMode.opModeIsActive() &&
               FrontLeftWheel.isBusy() && FrontRightWheel.isBusy() && BackLeftWheel.isBusy() && BackRightWheel.isBusy())
        {
            // Do nothing until at least 1 wheel reaches TargetPosition
        }
        myOpMode.sleep(WaitTime);
    }

    // This function strafes left.
    public void StrafeLeft(int mmToTarget, double VelocityPercentage, int WaitTime){
        StrafeRight(-mmToTarget, VelocityPercentage, WaitTime);
    }

    public void RotateLeft(double degrees, double VelocityPercentage, int WaitTime) {
        double mmToTarget;
        double TicksToTarget;
        double TicksPerSecond;

        // converts degree to a mm distance
        mmToTarget = degrees * (mmPer90DegreesRotation / 90.0);
        // diameter of new robot wheels =
        // uses the formula we've always had for rotation
        TicksToTarget = (mmToTarget / (WheelsDiameter * PI)) * MotorsTicksPerRevolution;
        TicksPerSecond = VelocityPercentage * ((MotorsRPM / 60) * MotorsTicksPerRevolution);

        FrontLeftWheel.setTargetPosition((int) (FrontLeftWheel.getCurrentPosition() - TicksToTarget));
        FrontRightWheel.setTargetPosition((int) (FrontRightWheel.getCurrentPosition() + TicksToTarget));
        BackLeftWheel.setTargetPosition((int) (BackLeftWheel.getCurrentPosition() - TicksToTarget));
        BackRightWheel.setTargetPosition((int) (BackRightWheel.getCurrentPosition() + TicksToTarget));

        ((DcMotorEx) FrontLeftWheel).setVelocity(TicksPerSecond);
        ((DcMotorEx) FrontRightWheel).setVelocity(TicksPerSecond);
        ((DcMotorEx) BackLeftWheel).setVelocity(TicksPerSecond);
        ((DcMotorEx) BackRightWheel).setVelocity(TicksPerSecond);

        while (myOpMode.opModeIsActive() &&
               FrontLeftWheel.isBusy() && FrontRightWheel.isBusy() && BackLeftWheel.isBusy() && BackRightWheel.isBusy())
        {
            // Do nothing until at least 1 wheel reaches TargetPosition
        }
        myOpMode.sleep(WaitTime);
    }

    public void RotateRight(double degree, double VelocityPercentage, int WaitTime)
    {
        RotateLeft(-1*degree, VelocityPercentage, WaitTime);
    }





}
