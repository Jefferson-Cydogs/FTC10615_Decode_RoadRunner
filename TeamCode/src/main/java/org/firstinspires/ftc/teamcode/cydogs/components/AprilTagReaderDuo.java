package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.cydogs.chassis.IndianaChassis;
import org.firstinspires.ftc.teamcode.cydogs.core.EventTracker;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.Objects;

public class AprilTagReaderDuo {
    private String Obelisk;
    private  LinearOpMode opMode;
    private  AprilTagProcessor aprilTag;
    private  VisionPortal visionPortal;
    private String team;
    private int leftMost=0;
    private int rightMost=0;
    private static final boolean USE_WEBCAM = true;
    public int AprilTagId;

    public AprilTagDetection CurrentScoringTag;



    public AprilTagReaderDuo(LinearOpMode opModeIn, String TeamIn)
    {
        opMode = opModeIn;
        team=TeamIn;
    }
    public void initAprilTag() {

        if (visionPortal != null) return; // Already initialized

        // Create the AprilTag processor.
        aprilTag = new AprilTagProcessor.Builder().build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder().addProcessor(aprilTag);

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(opMode.hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

    }

    public String getObelisk() {
        if (aprilTag == null) return "None"; // protect against processor not being initialized

        List<AprilTagDetection> detections = aprilTag.getDetections();
        opMode.telemetry.addData("# AprilTags Detected", detections.size());
        displayDetections(detections);

        AprilTagDetection selectedTag = null;

        for (AprilTagDetection detection : detections) {
            if (detection.metadata == null) continue;

            // First valid tag, always select
            if (selectedTag == null) {
                selectedTag = detection;
                continue;
            }

            // Choose based on team color
            if ("blue".equalsIgnoreCase(team)) {
                // Blue wants the rightmost tag
                if (detection.center.x > selectedTag.center.x) {
                    selectedTag = detection;
                }
            } else if ("red".equalsIgnoreCase(team)) {
                // Red wants the leftmost tag
                if (detection.center.x < selectedTag.center.x) {
                    selectedTag = detection;
                }
            }
        }


        if (selectedTag != null) {
            switch (selectedTag.id) {
                case 21: return "GPP";
                case 22: return "PGP";
                case 23: return "PPG";
            }
        }

        return "None";
    }

    public AprilTagDetection GetScoringTag(String team){
        // need a variable to store target apriltag ID in

        if (Objects.equals(team, "red")){
            // set target ID to be correct number
            AprilTagId=24;
        } else if (Objects.equals(team, "blue")) {
            AprilTagId=20;
        }
        // else set it to the other number

        if (aprilTag == null) return null; // protect against processor not being initialized

        List<AprilTagDetection> AprilTagList = aprilTag.getDetections();
        if(AprilTagList==null )
        {
            opMode.telemetry.addLine("No april tags found");
            return null;
        }
        displayDetections(AprilTagList);
        // create a variable to store a list of detections
        // call GetDetections to return a list of april tags found
        // call display detections to write them to the telemtry

        for (AprilTagDetection detection : AprilTagList) {
            if (detection.metadata == null) continue;

            if (detection.id==AprilTagId){
                return detection;
            }
            // check to see if current detection is the correct ID
            // if so, return it

        }
       return null; // if my code makes it here, I didn't find the right one.  return null;
    }

    public List<AprilTagDetection> GetDetections()
    {
        if (aprilTag == null) return null; // protect against processor not being initialized

        List<AprilTagDetection> detections = aprilTag.getDetections();
        if(detections==null )
        {
            opMode.telemetry.addLine("No april tags found");
            return null;
        }
        opMode.telemetry.addData("# AprilTags Detected", detections.size());
        return detections;
    }

    public void displayDetections(List<AprilTagDetection> detections)
    {
        if(detections==null )
        {
            opMode.telemetry.addLine("No april tags found");
            return;
        }
        for (AprilTagDetection detection : detections) {
            if (detection.metadata != null) {
                opMode.telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                opMode.telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                opMode.telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                opMode.telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                opMode.telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                opMode.telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop
    }

    public void turnToFaceAprilTagTeleop(IndianaChassis indiana, String team, double turnPower, double angleThresholdDeg, ElapsedTime currentTimer, EventTracker eventTracker) {
        // Get the yaw angle to the tag in degrees.
        AprilTagDetection scoringTag = GetScoringTag(team);
        if(scoringTag == null) return;

        CurrentScoringTag = scoringTag;

        double bearing = scoringTag.ftcPose.bearing;
        opMode.telemetry.addData("Bearing:", bearing);


        // Turn until facing the tag (yaw ≈ 0)
        while (Math.abs(bearing) > angleThresholdDeg) {
            opMode.telemetry.addData("Bearing to tag", bearing);
            opMode.telemetry.update();
            if (bearing > 0) {
                // Tag is to the right → turn right
                indiana.setTurnPower(turnPower);
            } else {
                // Tag is to the left → turn left
                indiana.setTurnPower(-turnPower);
            }


            scoringTag = GetScoringTag(team);
            if(scoringTag == null)
            {
                indiana.stopMotors();
                return;
            }
            bearing = scoringTag.ftcPose.bearing;
        }

        // Stop the robot
        indiana.stopMotors();

    }
    public void turnToFaceAprilTagAuton(IndianaChassis indiana, String team, double angleAdjustment, double turnPower, double angleThresholdDeg, ElapsedTime currentTimer, EventTracker eventTracker) {
        // Get the yaw angle to the tag in degrees.
        AprilTagDetection scoringTag = GetScoringTag(team);
        if(scoringTag == null) return;

        CurrentScoringTag = scoringTag;

        double bearing = scoringTag.ftcPose.bearing;
        opMode.telemetry.addData("Bearing:", bearing);


        indiana.RotateLeft(bearing+angleAdjustment,.3,100);

    }


}
