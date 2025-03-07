package frc.robot.constants;

import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.simulation.SimCameraProperties;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.apriltag.AprilTagFieldLayout.OriginPosition;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;

public class VisionConstants {
  public static final String frontCameraName = "frontcamera";

  public static final String leftCameraName = "leftCamera";

  public static final String rightCameraName = "rightCamera";

  public static final String simulationName = "main";

  public static final Transform3d robotToFrontCameraTransform = new Transform3d(0, 0, 0.5, new Rotation3d(0, -Math.toRadians(0), 0));

  public static final Transform3d robotToRightCameraTransform = new Transform3d(0, 0, 0.5, new Rotation3d(0, -Math.toRadians(0), -Math.toRadians(90)));

  public static final Transform3d robotToLeftCameraTransform = new Transform3d(0, 0, 0.5, new Rotation3d(0, -Math.toRadians(0), +Math.toRadians(90)));

  public static final PoseStrategy poseEstimationStrategy = PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR;

  public static final AprilTagFieldLayout fieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeAndyMark);
  
  static {
    fieldLayout.setOrigin(OriginPosition.kBlueAllianceWallRightSide);
  }

  public static final SimCameraProperties frontCameraProperties = new SimCameraProperties();

  public static final SimCameraProperties leftCameraProperties = new SimCameraProperties();

  public static final SimCameraProperties rightCameraProperties = new SimCameraProperties();

  static {
    frontCameraProperties.setCalibration(640, 480, Rotation2d.fromDegrees(90));
    frontCameraProperties.setCalibError(0.25, 0.08);
    frontCameraProperties.setFPS(60);
    frontCameraProperties.setAvgLatencyMs(35);
    frontCameraProperties.setLatencyStdDevMs(5);
  
    leftCameraProperties.setCalibration(640, 480, Rotation2d.fromDegrees(90));
    leftCameraProperties.setCalibError(0.25, 0.08);
    leftCameraProperties.setFPS(60);
    leftCameraProperties.setAvgLatencyMs(35);
    frontCameraProperties.setLatencyStdDevMs(5);

    rightCameraProperties.setCalibration(640, 480, Rotation2d.fromDegrees(90));
    rightCameraProperties.setCalibError(0.25, 0.08);
    rightCameraProperties.setFPS(60);
    rightCameraProperties.setAvgLatencyMs(35);
    frontCameraProperties.setLatencyStdDevMs(5);
  }
}
