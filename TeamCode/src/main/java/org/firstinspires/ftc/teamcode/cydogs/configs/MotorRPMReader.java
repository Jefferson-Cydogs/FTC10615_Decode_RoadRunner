package org.firstinspires.ftc.teamcode.cydogs.configs;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Motor RPM Reader", group = "Diagnostics")
public class MotorRPMReader extends LinearOpMode {

    private DcMotor motor;
    private ElapsedTime timer = new ElapsedTime();

    private int lastEncoderPosition = 0;
    private double lastTime = 0;

    // Set this to the encoder ticks per revolution of the motor shaft
    private static final double TICKS_PER_REV = 537.7;

    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotor.class, "Launchers");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        timer.reset();

        while (opModeIsActive()) {
            double currentTime = timer.seconds();
            int currentPosition = motor.getCurrentPosition();

            double deltaTime = currentTime - lastTime;
            int deltaTicks = currentPosition - lastEncoderPosition;

            lastTime = currentTime;
            lastEncoderPosition = currentPosition;

            double ticksPerSecond = deltaTicks / deltaTime;
            double rpm = (ticksPerSecond / TICKS_PER_REV) * 60.0;

            telemetry.addData("Motor Power", motor.getPower());
            telemetry.addData("Encoder Position", currentPosition);
            telemetry.addData("RPM", rpm);
            telemetry.update();

            motor.setPower(1.0); // Run motor at full power
            sleep(100); // Update every 100ms
        }

        motor.setPower(0);
    }
}
