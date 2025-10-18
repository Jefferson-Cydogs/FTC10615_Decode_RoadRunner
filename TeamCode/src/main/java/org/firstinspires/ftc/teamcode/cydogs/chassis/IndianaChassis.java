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
    private LinearOpMode myOpMode;
    private int WheelDiameter=104;
    private int RPM = 435;
    private double ticksPerRevolution=384.5;

    // mmPer90DegreeRotation needs to be configured for each robot based on it's chassis size
    private double mmPer90DegreeRotation=841;

    private double strafeCompensation = 1.081;

    public static final int OneTileMM = 610;

    // This is the constructor for the class.  It takes a parameter for currentOp, which allows
    //   it to store and use the current op mode.  The four wheels are initialized here.
    public IndianaChassis(LinearOpMode currentOp){

        // The op mode is important code provided by first.  It has the hardwareMap, sleep function,
        //   and telemetry functions.
        myOpMode = currentOp;
        HardwareMap hardwareMap = myOpMode.hardwareMap;

        // This gets the devices from the configuration on the robot.
        //    This call basically says "Get me the thing called FrontRightWheel from the
        //    configuration, and trust me it can be mapped to the DCMotor class.  If it's
        //    not a motor, then code later on will throw errors when it tries to do motor
        //    things with a non motor.
        FrontRightWheel = hardwareMap.get(DcMotor.class, "FrontRightWheel");
        BackRightWheel = hardwareMap.get(DcMotor.class, "BackRightWheel");
        FrontLeftWheel = hardwareMap.get(DcMotor.class, "FrontLeftWheel");
        BackLeftWheel = hardwareMap.get(DcMotor.class, "BackLeftWheel");

        BackLeftWheel.setDirection(DcMotor.Direction.FORWARD);
        FrontLeftWheel.setDirection(DcMotor.Direction.REVERSE);
        BackRightWheel.setDirection(DcMotor.Direction.REVERSE);
        FrontRightWheel.setDirection(DcMotor.Direction.FORWARD);

    }

    public void InitializeTeleop(double highSpeed, double lowSpeed, double rotateSpeed)
    {
        highSpeedDrive = highSpeed;
        lowSpeedDrive = lowSpeed;
        rotateSpeedDrive = rotateSpeed;
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

    public void InitializeAuton()
    {
        FrontRightWheel.setPower(0);
        BackRightWheel.setPower(0);
        FrontLeftWheel.setPower(0);
        BackLeftWheel.setPower(0);

        // Set the direction of the wheels.  Because of how the wheels are installed, one side
        //   has to be reverse.


        // > Set motors' ZeroPower behavior
        FrontLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeftWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRightWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // > Clear Encoders of prior data
        FrontLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeftWheel.setTargetPosition(0);
        FrontRightWheel.setTargetPosition(0);
        BackLeftWheel.setTargetPosition(0);
        BackRightWheel.setTargetPosition(0);

        // > Set some motors' modes different from RUN_WITHOUT_ENCODER (default)
        FrontLeftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackLeftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

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


    private float forward;
    private float strafe;
    private float rotate;

    public void OptimizedTeleopDrive()
    {
        double deadZone = 0.05;
        double leftFrontPower;
        double rightFrontPower;
        double leftBackPower;
        double rightBackPower;
        double mySpeedConstant;

        if(myOpMode.gamepad1.right_stick_y < myOpMode.gamepad1.left_stick_y ||
                myOpMode.gamepad1.right_stick_x > myOpMode.gamepad1.left_stick_x)
        {
            forward = -myOpMode.gamepad1.right_stick_y;
            strafe = myOpMode.gamepad1.right_stick_x;
            mySpeedConstant = highSpeedDrive;
        }
        else
        {
            forward = -myOpMode.gamepad1.left_stick_y;
            strafe = myOpMode.gamepad1.left_stick_x;
            mySpeedConstant = lowSpeedDrive;
        }

        rotate = myOpMode.gamepad1.right_trigger - myOpMode.gamepad1.left_trigger;


        forward = abs(forward) > deadZone ? (float) (mySpeedConstant * ((0.75 * pow(forward, 3)) + (0.25 * forward))) : 0;
        strafe = abs(strafe) > deadZone ? (float) (mySpeedConstant * ((0.75 * pow(strafe, 3)) + (0.25 * strafe))) : 0;
        rotate = abs(rotate) > deadZone ? (float) (rotateSpeedDrive * ((0.75 * pow(rotate, 3)) + (0.25 * rotate))) : 0;
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
        FrontLeftWheel.setPower(leftFrontPower);
        FrontRightWheel.setPower(rightFrontPower);
        BackLeftWheel.setPower(leftBackPower);
        BackRightWheel.setPower(rightBackPower);

    }

    // This function strafes left.
    public void StrafeLeft(int mmToTarget, double VelocityPercentage, int WaitTime){
        StrafeRight(-mmToTarget, VelocityPercentage, WaitTime);
    }

    // this function strafes right
    public void StrafeRight(int mmToTarget, double VelocityPercentage, int WaitTime) {
        double TicksToTarget;
        double TicksPerSecond;

        TicksToTarget = (mmToTarget / (WheelDiameter * Math.PI)) * ticksPerRevolution*strafeCompensation;
        TicksPerSecond = ((VelocityPercentage * RPM) / 60) * ticksPerRevolution;
        FrontLeftWheel.setTargetPosition((int) (FrontLeftWheel.getCurrentPosition() + TicksToTarget));
        FrontRightWheel.setTargetPosition((int) (FrontRightWheel.getCurrentPosition() - TicksToTarget));
        BackLeftWheel.setTargetPosition((int) (BackLeftWheel.getCurrentPosition() - TicksToTarget));
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

        while (myOpMode.opModeIsActive() && FrontLeftWheel.isBusy() && FrontRightWheel.isBusy() && BackLeftWheel.isBusy() && BackRightWheel.isBusy()) {
            // Do nothing until at least 1 wheel reaches TargetPosition
        }
        myOpMode.sleep(WaitTime);

    }

    /**
     * Move forward(+) or backwards(-) until reaching Position
     */
    public void MoveStraight(int mmToTarget, double VelocityPercentage, int WaitTime) {
        double TicksToTarget;
        double TicksPerSecond;

        TicksToTarget = (mmToTarget / (WheelDiameter * Math.PI)) * (ticksPerRevolution);
        TicksPerSecond = ((VelocityPercentage * RPM) / 60) * ticksPerRevolution;
        // myOpMode.telemetry.addData("ticksToTarget", TicksToTarget);
        //myOpMode.telemetry.update();
        FrontLeftWheel.setTargetPosition((int) (FrontLeftWheel.getCurrentPosition() + TicksToTarget));
        FrontRightWheel.setTargetPosition((int) (FrontRightWheel.getCurrentPosition() + TicksToTarget));
        BackLeftWheel.setTargetPosition((int) (BackLeftWheel.getCurrentPosition() + TicksToTarget));
        BackRightWheel.setTargetPosition((int) (BackRightWheel.getCurrentPosition() + TicksToTarget));
        ((DcMotorEx) FrontLeftWheel).setVelocity(TicksPerSecond);
        ((DcMotorEx) FrontRightWheel).setVelocity(TicksPerSecond);
        ((DcMotorEx) BackLeftWheel).setVelocity(TicksPerSecond);
        ((DcMotorEx) BackRightWheel).setVelocity(TicksPerSecond);
        while (myOpMode.opModeIsActive() && FrontLeftWheel.isBusy() && FrontRightWheel.isBusy() && BackLeftWheel.isBusy() && BackRightWheel.isBusy()) {
            // Do nothing until at least 1 wheel reaches TargetPosition
        }
        myOpMode.sleep(WaitTime);
    }

    public void RotateRight(double degree, double VelocityPercentage, int WaitTime){

        RotateLeft(-1*degree, VelocityPercentage, WaitTime);
    }

    public void RotateLeft(double degree, double VelocityPercentage, int WaitTime) {
        double mmToTarget;
        double TicksToTarget;
        double TicksPerSecond;

        // converts degree to a mm distance
        mmToTarget = degree * (mmPer90DegreeRotation / 90.0);
        // diamter of new robot wheels =
        // name is
        // uses the formula we've always had for rotation
        TicksToTarget = (mmToTarget / (WheelDiameter * Math.PI)) * ticksPerRevolution;
        TicksPerSecond = ((VelocityPercentage * RPM) / 60) * ticksPerRevolution;

        FrontLeftWheel.setTargetPosition((int) (FrontLeftWheel.getCurrentPosition() - TicksToTarget));
        FrontRightWheel.setTargetPosition((int) (FrontRightWheel.getCurrentPosition() + TicksToTarget));
        BackLeftWheel.setTargetPosition((int) (BackLeftWheel.getCurrentPosition() - TicksToTarget));
        BackRightWheel.setTargetPosition((int) (BackRightWheel.getCurrentPosition() + TicksToTarget));
        ((DcMotorEx) FrontLeftWheel).setVelocity(TicksPerSecond);
        ((DcMotorEx) FrontRightWheel).setVelocity(TicksPerSecond);
        ((DcMotorEx) BackLeftWheel).setVelocity(TicksPerSecond);
        ((DcMotorEx) BackRightWheel).setVelocity(TicksPerSecond);

        while (myOpMode.opModeIsActive() && FrontLeftWheel.isBusy() && FrontRightWheel.isBusy() && BackLeftWheel.isBusy() && BackRightWheel.isBusy()) {
            // Do nothing until at least 1 wheel reaches TargetPosition
        }
        myOpMode.sleep(WaitTime);
    }
}
