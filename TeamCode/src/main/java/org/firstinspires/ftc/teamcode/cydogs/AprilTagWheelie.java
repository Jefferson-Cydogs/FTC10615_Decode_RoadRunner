package org.firstinspires.ftc.teamcode.cydogs;
import  com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import  com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
public class AprilTagWheelie {
    public String Obelisk;
    private  LinearOpMode opMode;
    private  AprilTagProcessor aprilTag;
    private  VisionPortal visionPortal;


    private static final boolean USE_WEBCAM = true;


 //   public double DistanceFromScore(){
       //Need to get detection range and make it an if statement/tell what range is
     //   if(detection.ftcPose.range){}
  //  }

    public AprilTagWheelie(LinearOpMode opModeIn)
    {
        opMode = opModeIn;
    }
    public void initAprilTag() {

        // Create the AprilTag processor.
        aprilTag = new AprilTagProcessor.Builder()


                .build();
        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(opMode.hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }


        // Set and enable the processor.
        builder.addProcessor(aprilTag);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

    }

    public String telemetryAprilTag() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        opMode.telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if(detection.id == 23)
                {
                     Obelisk="PPG";
                }  if(detection.id == 22)
                {
                    Obelisk="PGP";
                }  if(detection.id == 21)
                {
                    Obelisk="GPP";
                }
                opMode.telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                opMode.telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                opMode.telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                opMode.telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                opMode.telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                opMode.telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop

        // Add "key" information to telemetry
        opMode.telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        opMode.telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        opMode.telemetry.addLine("RBE = Range, Bearing & Elevation");

        return Obelisk;

    }

}
