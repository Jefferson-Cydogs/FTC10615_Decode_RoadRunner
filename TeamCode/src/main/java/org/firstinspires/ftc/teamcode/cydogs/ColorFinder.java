package org.firstinspires.ftc.teamcode.cydogs;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.cydogs.chassis.MegalodogChassis;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;
public class ColorFinder {

    public static final float MIN_SATURATION = 0.5f;
    public static final float MIN_VALUE = 0.3f;
    public static final int REQUIRED_CONSECUTIVE_HITS=3;

public static final float RED_HUE_LOW_1 = 0f;
public static final float RED_HUE_HIGH_1 = 20f;
public static final float RED_HUE_HIGH_2 = 360f;
public static final float RED_HUE_LOW_2 = 340f;

public static final float BLUE_HUE_LOW = 190f;
public static final float BLUE_HUE_HIGH = 260f;

private final LinearOpMode opMode;
private final MegalodogChassis chassis;
private final NormalizedColorSensor colorSensor;

    public ColorFinder(LinearOpMode opMode, MegalodogChassis chassis, String FunColor){
        this.opMode=opMode;
        this.chassis=chassis;
        HardwareMap hw= opMode.hardwareMap;
        this.colorSensor=hw.get(NormalizedColorSensor.class,FunColor );
             if (colorSensor instanceof SwitchableLight){
                 try{
                     ((SwitchableLight)colorSensor).enableLight(true);
                 }catch (Exception ignored){
                 }
             }
    }




public enum TargetColor{RED,BLUE}

public boolean driveUntilTape(TargetColor targetColor,double drivePower, long timeoutMs){
        long start = System.currentTimeMillis();
        int consecutiveHits = 0;
chassis.MoveStraight(500,0.5,500);
    while (opMode.opModeIsActive()&&(System.currentTimeMillis()-start)<timeoutMs);

    NormalizedRGBA colors= colorSensor.getNormalizedColors();
    float[] hsv= new float[3];
    int r = (int) (colors.red *255);
    int g = (int) (colors.green*255);
    int b = (int) (colors.blue*255);
    Color.RGBToHSV(r,g,b,hsv);


    }































}
