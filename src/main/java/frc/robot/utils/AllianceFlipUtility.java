package frc.robot.utils;

import edu.wpi.first.math.geometry.*;
import frc.robot.constants.FieldConstants;

public class AllianceFlipUtility {
  public static double applyX(double x) {
    return FieldConstants.fieldLength - x;
  }

  public static double applyY(double y) {
    return FieldConstants.fieldWidth - y;
  }

  public static Translation2d apply(Translation2d translation) {
    return new Translation2d(applyX(translation.getX()), applyY(translation.getY()));
  }

  public static Rotation2d apply(Rotation2d rotation) {
    return rotation.rotateBy(Rotation2d.kPi);
  }

  public static Pose2d apply(Pose2d pose) {
    return new Pose2d(apply(pose.getTranslation()), apply(pose.getRotation()));
  }

  public static Translation3d apply(Translation3d translation) {
    return new Translation3d(
        applyX(translation.getX()), applyY(translation.getY()), translation.getZ());
  }

  public static Rotation3d apply(Rotation3d rotation) {
    return rotation.rotateBy(new Rotation3d(0.0, 0.0, Math.PI));
  }

  public static Pose3d apply(Pose3d pose) {
    return new Pose3d(apply(pose.getTranslation()), apply(pose.getRotation()));
  }
}