package org.firstinspires.ftc.teamcode.cydogs;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Launcher {
    public static final double MOTOR_MIN = -1.0;
    public static final double MOTOR_MAX = 1.0;

    public void initLauncher(){

    }

    public LinearOpMode opMode;
    public DcMotor Launcher;

    public Launcher(LinearOpMode opMode){
        this.opMode = opMode;
        Launcher = opMode.hardwareMap.get(DcMotor.class,"launcher");
        Launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
    public void runAtPower(double power){
        power = Math.max(MOTOR_MIN, Math.min(MOTOR_MAX, power));
        Launcher.setPower(power);
    }
   public void turnPowerOff(){
        Launcher.setPower(0);
   }

}
