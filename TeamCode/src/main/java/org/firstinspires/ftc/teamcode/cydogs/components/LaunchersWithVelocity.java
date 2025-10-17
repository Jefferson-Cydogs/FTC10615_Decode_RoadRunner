package org.firstinspires.ftc.teamcode.cydogs.components;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class LaunchersWithVelocity {
    public static final double Max_Ticks=312.0/60.0*384.5;
    public double velocityIn= 0.6;
    private LinearOpMode opMode;
    private DcMotorEx LeftLauncher;
    private DcMotorEx RightLauncher;

        public void initLauncher(){

         }

    public LaunchersWithVelocity(LinearOpMode opMode){
        this.opMode = opMode;
        LeftLauncher = opMode.hardwareMap.get(DcMotorEx.class,"LeftLauncher");
        LeftLauncher.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        RightLauncher = opMode.hardwareMap.get(DcMotorEx.class,"RightLauncher");
        RightLauncher.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        RightLauncher.setDirection(DcMotorEx.Direction.REVERSE);
        LeftLauncher.setDirection(DcMotorEx.Direction.REVERSE);
        RightLauncher.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        LeftLauncher.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }



    public void runAtPower(double velocityIn){
        double goalVelocity=velocityIn*Max_Ticks;
        LeftLauncher.setVelocity(goalVelocity);
        RightLauncher.setVelocity(goalVelocity);
    }
    public boolean CheckMotor(double TargetVelocity){
        double CurrentLeftTicks= LeftLauncher.getVelocity();
        double CurrentRightTicks= RightLauncher.getVelocity();
        double TargetTicks =TargetVelocity*Max_Ticks;
            if (CurrentLeftTicks>=TargetTicks&&CurrentRightTicks>=TargetTicks){
                return true;
            }
            else {
                return false;
            }
    }
    public void turnPowerOff(){
        LeftLauncher.setVelocity(0);
        RightLauncher.setVelocity(0);
    }

}

