package org.firstinspires.ftc.teamcode.cydogs.RRTests;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.MecanumDrive;


// The 'extends LinearOpMode' is needed so this code can run the build in op mode code from FIRST.
//    @Autonomous puts this code in the autonomous category on driver station
@Autonomous
public class RR_Basic extends LinearOpMode {

    /* declare variables

     */
    @Override
    public void runOpMode() {

        // Put code that should run during initialization HERE in this area
        Pose2d initialPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        //drive.setPoseEstimate(initialPose);

        // Wait for the start button to be pressed on the driver station
        waitForStart();

        if (opModeIsActive()) {
            // Put code that should run during the active mode HERE in this area
            Actions.runBlocking(
                    drive.actionBuilder(initialPose)
                            /*.lineToX(48)
                            .waitSeconds(1)
                            .lineToX(44)
                            .waitSeconds(1)*/
                            .splineTo(new Vector2d(48, 24), Math.PI / 2)
                            /*.turn(Math.toRadians(10))
                            .setTangent(Math.toRadians(-135))
                            .splineTo(new Vector2d(-56, -52), Math.PI / 4)
                            .lineToX(-52)
                            .splineTo(new Vector2d(-58, -37), Math.PI / 2)
                            .turn(Math.toRadians(10))
                            .splineTo(new Vector2d(-56, -52), Math.PI / 4)
                            .lineToX(-52)
                            .splineTo(new Vector2d(-60, -37), Math.PI / 2)
                            .turn(Math.toRadians(30))
                            .splineTo(new Vector2d(-56, -52), Math.PI / 4)
                            .lineToX(-52)*/
                            .build());
        }
    }

}
