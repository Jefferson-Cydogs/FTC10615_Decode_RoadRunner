package org.firstinspires.ftc.teamcode.cydogs.learning;
import  com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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
    public String Team;
    public int LeftMost=0;
    public int RightMost=0;
    public int IdealTag=0;
    public double IdealPitch;
    private static final boolean USE_WEBCAM = true;


    // public double DistanceFromScore(){
       //Need to get detection range and make it an if statement/tell what range is
     // if(detection.ftcPose.range){}
  //  }

    public AprilTagWheelie(LinearOpMode opModeIn,String TeamIn)
    {

        opMode = opModeIn;
        Team=TeamIn;
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
                if (detection.id==21||detection.id==22||detection.id==23) {
                    if (IdealTag == 0) {
                        opMode.telemetry.addData("IdealTag=0","");
                        IdealTag = detection.id;
                        IdealPitch = detection.ftcPose.pitch;
                        opMode.telemetry.addData("IdealTag is now:",IdealTag);
                        opMode.telemetry.addData("IdealPitch is now:",IdealPitch);
                    } else {
                        if (Team == "Blue" && detection.ftcPose.pitch > IdealPitch) {
                            opMode.telemetry.addData("team is blue and new pitch is:",detection.ftcPose.pitch);
                            IdealTag = detection.id;
                            IdealPitch = detection.ftcPose.pitch;
                        }
                        if (Team == "Red" && detection.ftcPose.pitch < IdealPitch) {
                            IdealTag = detection.id;
                            IdealPitch = detection.ftcPose.pitch;
                        }
                    }
                }
                if(IdealTag == 23)
                {
                     Obelisk="PPG";
                }  if(IdealTag == 22)
                {
                    Obelisk="PGP";
                }  if(IdealTag == 21)
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
// I need a variable created outside of loop to store current left most or right $
// most tag.  I would initialize my stored variable to 0.  Need two $
//   int to store ideal tag $
//   double to store the ideal tag's pitch $
// going through loop, IF ideal tag = 0, then I add my current ID and pitch$
//   to my stored variables.$
// else if ideal tag is not 0 (just plain else), check the new detection ID pitch
//    against the stored pitch.  If team is blue and pitch is higher, then replace
//    my ideal ID and pitch with the new one.  if team is red and pitch is lower,
//    then replace the same way.  Otherwise, don't replace.