package frc.robot.subsystems.drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.utils.DriveType;

public interface DrivetrainSubsystem extends Subsystem {

  ChassisSpeeds getFieldRelativeSpeeds();
  
  ChassisSpeeds getRobotRelativeSpeeds();

  Pose2d getPose();

  Pose2d getSimPose();

  void updateOdometer();
  
  void resetOdometry(Pose2d pose2d);

  void stop();

  void drive(ChassisSpeeds speeds);
  
  void drive(double xSpeed, double ySpeed, double rSpeed, DriveType driveType);
}
