// Şuan işlem altında, ne yapacağımı biliyorum sadece yetiştiremedim :(

package frc.robot.commands.auto.drivetrain;

import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.VisionConstants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.units.VisionProcessingUnit;
import frc.robot.utils.CameraPosition;
import frc.robot.utils.DriveType;
import frc.robot.utils.Logger;

public class ChaseBestApriltag extends Command {

  DrivetrainSubsystem drivetrainSubsystem;

  VisionProcessingUnit frontUnit = VisionProcessingUnit.getUnit(CameraPosition.Front);

  double xSpeed, ySpeed, rSpeed;

  PhotonTrackedTarget target;

  ProfiledPIDController xController;
  ProfiledPIDController yController;
  ProfiledPIDController rController;

  Transform3d apriltagToGoal;
  
  Transform3d camToTarget;

  Pose3d cameraPose;
  
  Pose3d robotPose;
  
  Pose3d targetPose;

  Pose3d goalPose;

  public ChaseBestApriltag(
    DrivetrainSubsystem drivetrainSubsystem,
    double desiredXOffset,
    double desiredYOffset,
    double desiredAngle
    ) {

    this.drivetrainSubsystem = drivetrainSubsystem;

    this.apriltagToGoal = new Transform3d(
      desiredXOffset, 
      desiredYOffset, 
      0,
      new Rotation3d(new Rotation2d(Units.degreesToRadians(desiredAngle) + Math.PI))
    );

    this.xController = new ProfiledPIDController(
      AutoConstants.PDrive, 
      AutoConstants.IDrive, 
      AutoConstants.DDrive, 
      AutoConstants.driveConstraints
    );
    this.xController.setIZone(AutoConstants.IZDrive);
    this.xController.setTolerance(AutoConstants.distanceTolerance);

    this.yController = new ProfiledPIDController(
      AutoConstants.PDrive, 
      AutoConstants.IDrive, 
      AutoConstants.DDrive, 
      AutoConstants.driveConstraints
    );
    this.yController.setIZone(AutoConstants.IZDrive);
    this.yController.setTolerance(AutoConstants.distanceTolerance);

    this.rController = new ProfiledPIDController(
      AutoConstants.PAngle, 
      AutoConstants.IAngle, 
      AutoConstants.DAngle, 
      AutoConstants.angleConstraints
    );
    this.rController.setIZone(AutoConstants.IZAngle);
    this.rController.enableContinuousInput(-Math.PI, Math.PI);
    this.rController.setTolerance(AutoConstants.angleTolerance);

    addRequirements(drivetrainSubsystem);
  }

  @Override
  public void initialize() {
    if (frontUnit.getBestTarget() != null) {
      target = frontUnit.getBestTarget();

      robotPose = new Pose3d(drivetrainSubsystem.getPose());
    
      camToTarget = target.getBestCameraToTarget();
  
      cameraPose = robotPose.transformBy(VisionConstants.robotToFrontCameraTransform);
  
      targetPose = cameraPose.transformBy(camToTarget);
  
      goalPose = targetPose.transformBy(apriltagToGoal);
      
      return;
    }
    else {
      this.cancel();      
    }
  }

  @Override
  public void execute() {

    if (goalPose == null) {
      drivetrainSubsystem.stop();
      this.initialize();
      return;
    }

    robotPose = new Pose3d(drivetrainSubsystem.getPose());

    Logger.log("ChaseApriltag/GoalPose", new Pose2d(goalPose.getX(), goalPose.getY(), goalPose.getRotation().toRotation2d()));
    Logger.log("ChaseApriltag/TargetPose", targetPose);

    xSpeed = xController.calculate(robotPose.getX(), goalPose.getX());
    Logger.log("ChaseApriltag/XSpeed", xSpeed);
    Logger.log("ChaseApriltag/XError", xController.getPositionError());

    ySpeed = yController.calculate(robotPose.getY(), goalPose.getY());
    Logger.log("ChaseApriltag/YSpeed", ySpeed);
    Logger.log("ChaseApriltag/YError", yController.getPositionError());

    rSpeed = rController.calculate(robotPose.getRotation().getZ(), goalPose.getRotation().getZ());
    Logger.log("ChaseApriltag/RSpeed", rSpeed);
    Logger.log("ChaseApriltag/RError", rController.getPositionError());

    drivetrainSubsystem.drive(xSpeed, ySpeed, rSpeed, DriveType.FieldRelative);
  }

  @Override
  public void end(boolean interrupted) {
    drivetrainSubsystem.stop();
  }

  @Override
  public boolean isFinished() {
    if (xController.atSetpoint() && yController.atSetpoint() && rController.atSetpoint()) {
      return true;
    }  
    
    return false;
  }
}