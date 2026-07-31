package org.firstinspires.ftc.teamcode.cydogs.summer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import  com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Test")
public class Test extends LinearOpMode {
DcMotor testMotor;
@Override
 public void runOpMode(){
testMotor = hardwareMap.get(DcMotor.class, "testmotor");
  telemetry.addLine("Initialized");
  telemetry.update();
  waitForStart();
while (opModeIsActive()){
testMotor.setPower(gamepad1.left_stick_y*0.8);
    telemetry.addLine("Robot is Running!");
    telemetry.update();
        }
    }
}