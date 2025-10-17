package org.firstinspires.ftc.teamcode.cydogs.components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class AprilTagReaderDuo {
    private String Obelisk;
    private  LinearOpMode opMode;
    private  AprilTagProcessor aprilTag;
    private  VisionPortal visionPortal;
    private String team;
    private int leftMost=0;
    private int rightMost=0;
    private static final boolean USE_WEBCAM = true;



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
        if (aprilTag == null) return null; // protect against processor not being initialized

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

        return null; // or "Unknown", "None", etc.
    }

    public List<AprilTagDetection> GetDetections()
    {
        if (aprilTag == null) return null; // protect against processor not being initialized

        List<AprilTagDetection> detections = aprilTag.getDetections();
        opMode.telemetry.addData("# AprilTags Detected", detections.size());
        return detections;
    }

    public void displayDetections(List<AprilTagDetection> detections)
    {
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

}
