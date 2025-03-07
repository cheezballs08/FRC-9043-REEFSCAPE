package frc.robot.constants;

import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import frc.robot.utils.AllianceFlipUtility;

import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.utils.Logger;

public class AutoConstants {

  public static final String autoName = "auto-2";

  public static final double PDrive = 3;
  public static final double IDrive = 0;
  public static final double IZDrive = 0;
  public static final double DDrive = 0;

  public static final double PAngle = 3;
  public static final double IAngle = 0.015;
  public static final double IZAngle = 0.02;
  public static final double DAngle = 0;

  // 1 0.25
  public static final Constraints driveConstraints = new Constraints(10000, 10000);

  public static final Constraints angleConstraints = new Constraints(10000, 10000);

  public static final double distanceTolerance = 0.01;
  public static final double angleTolerance = 0.04;

  public static final PathConstraints pathConstraints = new PathConstraints(
    3, 
    3, 
    3, 
    3
  );

  public static Pose2d coralDrop1PoseOriginal = new Pose2d(6.182, 4.025, Rotation2d.fromDegrees(180));
  public static Pose2d coralDrop2PoseOriginal = new Pose2d(5.275, 2.523, Rotation2d.fromDegrees(120));
  public static Pose2d coralDrop3PoseOriginal = new Pose2d(3.646, 2.563,  Rotation2d.fromDegrees(60));
  public static Pose2d coralDrop4PoseOriginal = new Pose2d(2.795, 4.016, Rotation2d.fromDegrees(0));
  public static Pose2d coralDrop5PoseOriginal = new Pose2d(3.627, 5.478, Rotation2d.fromDegrees(-60));
  public static Pose2d coralDrop6PoseOriginal = new Pose2d(5.275, 5.468, Rotation2d.fromDegrees(-120));
  
  //public static Pose2d leftFeederPose = new Pose2d(2.040, 6.046, Rotation2d.fromDegrees(125));
  public static Pose2d leftFeederPoseOriginal = new Pose2d(0.94, 6.79, Rotation2d.fromDegrees(125));
  //public static Pose2d rightFeederPose = new Pose2d(1.940, 2.085, Rotation2d.fromDegrees(-125));
  public static Pose2d rightFeederPoseOriginal = new Pose2d(0.91, 1.30, Rotation2d.fromDegrees(-125));
  
  public static Pose2d algeaOuttakePoseOriginal = new Pose2d(6.028, 1.390, Rotation2d.fromDegrees(-90));

  public static Pose2d coralDrop1Pose;
  public static Pose2d coralDrop2Pose;
  public static Pose2d coralDrop3Pose;
  public static Pose2d coralDrop4Pose;
  public static Pose2d coralDrop5Pose;
  public static Pose2d coralDrop6Pose;
  
  public static Pose2d leftFeederPose;
  public static Pose2d rightFeederPose;

  public static Pose2d algeaOuttakePose;

  public static void updatePoses() {

    coralDrop1Pose = coralDrop1PoseOriginal;
    coralDrop2Pose = coralDrop2PoseOriginal;
    coralDrop3Pose = coralDrop3PoseOriginal;
    coralDrop4Pose = coralDrop4PoseOriginal;
    coralDrop5Pose = coralDrop5PoseOriginal;
    coralDrop6Pose = coralDrop6PoseOriginal;

    leftFeederPose = leftFeederPoseOriginal;
    rightFeederPose = rightFeederPoseOriginal;

    algeaOuttakePose = algeaOuttakePoseOriginal;
        
    if (RobotConstants.alliance != Alliance.Blue) {      
      coralDrop1Pose = AllianceFlipUtility.apply(coralDrop1Pose);
      coralDrop2Pose = AllianceFlipUtility.apply(coralDrop2Pose);
      coralDrop3Pose = AllianceFlipUtility.apply(coralDrop3Pose);
      coralDrop4Pose = AllianceFlipUtility.apply(coralDrop4Pose);
      coralDrop5Pose = AllianceFlipUtility.apply(coralDrop5Pose);
      coralDrop6Pose = AllianceFlipUtility.apply(coralDrop6Pose);
  
      leftFeederPose = AllianceFlipUtility.apply(leftFeederPose);
      rightFeederPose = AllianceFlipUtility.apply(rightFeederPose);
  
      algeaOuttakePose = AllianceFlipUtility.apply(algeaOuttakePose);
    }  
  }

  public static void logPoses() {
    Logger.log("Test/CoralDrop1Pose", coralDrop1Pose);
    Logger.log("Test/CoralDrop2Pose", coralDrop2Pose);
    Logger.log("Test/CoralDrop3Pose", coralDrop3Pose);
    Logger.log("Test/CoralDrop4Pose", coralDrop4Pose);
    Logger.log("Test/CoralDrop5Pose", coralDrop5Pose);
    Logger.log("Test/CoralDrop6Pose", coralDrop6Pose);

    Logger.log("Test/LeftFeederPose", leftFeederPose);
    Logger.log("Test/RightFeederPose", rightFeederPose);

    Logger.log("Test/AlgeaOuttakePose", algeaOuttakePose);
  }
}
