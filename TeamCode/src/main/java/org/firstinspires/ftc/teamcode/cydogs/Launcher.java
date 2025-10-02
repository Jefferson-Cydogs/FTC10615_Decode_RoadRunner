package org.firstinspires.ftc.teamcode.cydogs;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Launcher {
    public static final double MOTOR_MIN = -1.0;
    public static final double MOTOR_MAX = 1.0;

    public LinearOpMode opMode;
    public DcMotor Launche;

    public Launcher(LinearOpMode opMode){
        this.opMode = opMode;
        Launche = opMode.hardwareMap.get(DcMotor.class,"launcher");
        Launche.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
    public void runAtPower(double power){
        power = Math.max(MOTOR_MIN, Math.min(MOTOR_MAX, power));
        Launche.setPower(power);
    }
   public void turnPowerOff(){
        Launche.setPower(0);
   }

}
