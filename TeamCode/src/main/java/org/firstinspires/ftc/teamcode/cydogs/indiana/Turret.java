package org.firstinspires.ftc.teamcode.cydogs.indiana;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
public class Turret {
    public static final double MOTOR_MIN = -1.0;
    public static final double MOTOR_MAX = 1.0;
    private LinearOpMode opMode;
    private DcMotor LeftLauncher;
    private DcMotor RightLauncher;

        public void initLauncher(){

         }

    public Turret (LinearOpMode opMode){
        this.opMode = opMode;
        LeftLauncher = opMode.hardwareMap.get(DcMotor.class,"LeftLauncher");
        LeftLauncher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        RightLauncher = opMode.hardwareMap.get(DcMotor.class,"RightLauncher");
        RightLauncher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        RightLauncher.setDirection(DcMotor.Direction.REVERSE);
        LeftLauncher.setDirection(DcMotor.Direction.REVERSE);
        }



    public void runAtPower(double power){
        power = Math.max(MOTOR_MIN, Math.min(MOTOR_MAX, power));
        LeftLauncher.setPower(power);
        RightLauncher.setPower(power);
    }
    public void turnPowerOff(){
        LeftLauncher.setPower(0);
        RightLauncher.setPower(0);
    }

}

