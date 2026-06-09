package org.firstinspires.ftc.teamcode.cydogs.learning;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

/**
 * AprilTagAlignOpMode
 *
 * Detects an AprilTag and rotates the mecanum robot in place until the
 * camera (and therefore the robot's front) is pointing directly at the
 * center of the tag (yaw error ≈ 0°).
 *
 */
@TeleOp(name = "AprilTag Face Align", group = "Vision")
@Disabled
public class AprilTagAlign extends LinearOpMode {

    // ── Target tag ────────────────────────────────────────────────────────────
    // Set to -1 to lock onto the FIRST tag seen, or set a specific tag ID.
    private static final int TARGET_TAG_ID = -1;

    // ── PID / rotation tuning ─────────────────────────────────────────────────
    /**
     * How much turn power to apply per degree of yaw error.
     * Start small (0.02) and increase until the robot turns briskly without
     * oscillating.
     */
    private static final double KP = 0.025;

    /**
     * Integral gain – helps eliminate steady-state error.
     * Set to 0 first; add a small value (0.001–0.005) only if needed.
     */
    private static final double KI = 0.002;

    /**
     * Derivative gain – dampens oscillation.
     * Tune last; typical range 0.005–0.02.
     */
    private static final double KD = 0.010;

    /** Minimum motor power to overcome static friction (stiction). */
    private static final double MIN_TURN_POWER = 0.08;

    /** Maximum rotation power (0–1). Keeps turns smooth and safe. */
    private static final double MAX_TURN_POWER = 0.55;

    /** Robot is considered "aligned" when yaw error is within this band (°). */
    private static final double ALIGNED_THRESHOLD_DEG = 1.5;

    /** Stop the auto-align after this many seconds without seeing a tag. */
    private static final double TAG_TIMEOUT_SEC = 2.0;

    // ── Hardware ──────────────────────────────────────────────────────────────
    private DcMotor frontLeft, frontRight, backLeft, backRight;

    // ── Vision ────────────────────────────────────────────────────────────────
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;

    // ── PID state ─────────────────────────────────────────────────────────────
    private double integralSum = 0.0;
    private double lastError = 0.0;
    private final ElapsedTime pidTimer = new ElapsedTime();

    @Override
    public void runOpMode() {

        // ── Init hardware ────────────────────────────────────────────────────
        initDriveMotors();
        initVision();

        telemetry.addData("Status", "Initialized – waiting for Start");
        telemetry.update();
        waitForStart();

        pidTimer.reset();
        ElapsedTime tagSeenTimer = new ElapsedTime();
        boolean tagWasSeen = false;

        // ── Main loop ────────────────────────────────────────────────────────
        while (opModeIsActive()) {
            AprilTagDetection target = getTargetDetection();

            if (target != null) {
                tagWasSeen = true;
                tagSeenTimer.reset();

                // ftcPose.yaw = horizontal angle from camera to tag center (°).
                // Positive yaw → tag is to the LEFT → robot must turn LEFT (CCW).
                // Negative yaw → tag is to the RIGHT → robot must turn RIGHT (CW).
                double yawError = target.ftcPose.yaw;

                if (Math.abs(yawError) <= ALIGNED_THRESHOLD_DEG) {
                    // ── Aligned ───────────────────────────────────────────────
                    stopMotors();
                    resetPid();
                    telemetry.addData("Status", "✅ ALIGNED");
                    telemetry.addData("Yaw err", "%.2f°", yawError);
                } else {
                    // ── Compute PID turn power ────────────────────────────────
                    double dt = pidTimer.seconds();
                    pidTimer.reset();

                    integralSum += yawError * dt;
                    // Clamp integral to prevent wind-up
                    integralSum = clamp(integralSum, -30.0, 30.0);

                    double derivative = (dt > 0) ? (yawError - lastError) / dt : 0.0;
                    lastError = yawError;

                    double rawPower = KP * yawError
                            + KI * integralSum
                            + KD * derivative;

                    // Apply stiction floor while respecting the power ceiling
                    double turnPower = applyStiction(rawPower, MIN_TURN_POWER, MAX_TURN_POWER);

                    // Positive turnPower → spin CCW (turn left) to chase positive yaw
                    spinInPlace(turnPower);

                    telemetry.addData("Status", "🔄 Aligning");
                    telemetry.addData("Yaw error", "%.2f°", yawError);
                    telemetry.addData("Turn power", "%.3f", turnPower);
                    telemetry.addData("Tag ID", target.id);
                    telemetry.addData("Range", "%.1f in", target.ftcPose.range);
                }

            } else {
                // ── No tag visible ────────────────────────────────────────────
                if (tagWasSeen && tagSeenTimer.seconds() < TAG_TIMEOUT_SEC) {
                    telemetry.addData("Status", "⚠ Tag lost – holding last turn");
                    // Keep last motor command; don't call stopMotors() yet
                } else {
                    stopMotors();
                    resetPid();
                    telemetry.addData("Status", "👀 Searching for AprilTag…");
                }
            }

            telemetry.update();
        }

        // Clean up
        visionPortal.close();
    }

    // ══════════════════════════════════════════════════════════════════════════
    // Helper – Vision
    // ══════════════════════════════════════════════════════════════════════════

    private void initVision() {
        aprilTagProcessor = new AprilTagProcessor.Builder()
                // Optional: supply a tag library for known field tags
                // .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTagProcessor)
                // Reduce resolution for speed if needed:
                // .setCameraResolution(new android.util.Size(640, 480))
                .build();
    }

    /**
     * Returns the best AprilTag detection to align with.
     * If TARGET_TAG_ID is -1, returns the first detection found.
     * Otherwise returns only detections matching TARGET_TAG_ID.
     */
    private AprilTagDetection getTargetDetection() {
        List<AprilTagDetection> detections = aprilTagProcessor.getDetections();
        if (detections == null || detections.isEmpty()) return null;

        for (AprilTagDetection d : detections) {
            // ftcPose is null until the tag's physical size is known;
            // skip detections without pose data.
            if (d.ftcPose == null) continue;
            if (TARGET_TAG_ID == -1 || d.id == TARGET_TAG_ID) return d;
        }
        return null;
    }

    // ══════════════════════════════════════════════════════════════════════════
    // Helper – Drive
    // ══════════════════════════════════════════════════════════════════════════

    private void initDriveMotors() {
        frontLeft = hardwareMap.get(DcMotor.class, "FrontLeftWheel");
        frontRight = hardwareMap.get(DcMotor.class, "FrontRightWheel");
        backLeft = hardwareMap.get(DcMotor.class, "BackLeftWheel");
        backRight = hardwareMap.get(DcMotor.class, "BackRightWheel");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        //backLeft.setDirection(DcMotor.Direction.REVERSE);
        //frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        for (DcMotor m : new DcMotor[]{frontLeft, frontRight, backLeft, backRight}) {
            m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            m.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    /**
     * Spin the robot in place.
     * @param power Positive = CCW (turn left), Negative = CW (turn right)
     *
     * Mecanum in-place rotation:
     * Left motors: -power (backward)
     * Right motors: +power (forward)
     */
    private void spinInPlace(double power) {
        frontLeft.setPower(-power);
        backLeft.setPower(-power);
        frontRight.setPower(power);
        backRight.setPower(power);
    }

    private void stopMotors() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    // ══════════════════════════════════════════════════════════════════════════
    // Helper – Math / PID
    // ══════════════════════════════════════════════════════════════════════════

    private void resetPid() {
        integralSum = 0.0;
        lastError = 0.0;
        pidTimer.reset();
    }

    /**
     * Ensures the output has at least MIN magnitude (to beat stiction)
     * but no more than MAX magnitude.
     */
    private double applyStiction(double value, double min, double max) {
        if (Math.abs(value) < 1e-6) return 0.0; // truly zero → stop
        double sign = Math.signum(value);
        double floored = sign * Math.max(Math.abs(value), min);
        return clamp(floored, -max, max);
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
