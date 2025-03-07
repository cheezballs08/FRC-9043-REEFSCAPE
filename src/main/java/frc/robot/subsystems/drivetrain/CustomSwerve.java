// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.drivetrain;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.studica.frc.AHRS;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ModuleConstants;
import frc.robot.constants.RobotConstants;
import frc.robot.units.VisionProcessingUnit;
import frc.robot.utils.CameraPosition;
import frc.robot.utils.DriveType;
import frc.robot.utils.Gyroscope;
import frc.robot.constants.DrivetrainConstants;

public class CustomSwerve extends SubsystemBase implements DrivetrainSubsystem {
  
  private SwerveModule frontLeftModule = new SwerveModule(
    DrivetrainConstants.frontLeftDriveMotorID, 
    DrivetrainConstants.frontLeftDriveMotorConfig, 
    DrivetrainConstants.frontLeftInvertDriveEncoder, 
    DrivetrainConstants.frontLeftAngleMotorID, 
    DrivetrainConstants.frontLeftAngleMotorConfig, 
    DrivetrainConstants.frontLeftInvertAngleEncoder,  
    DrivetrainConstants.frontLeftAbsoluteEncoderOffset, 
    DrivetrainConstants.frontLeftInvertAbsoluteEncoder
  );
  
  private SwerveModule frontRightModule = new SwerveModule(
    DrivetrainConstants.frontRightDriveMotorID, 
    DrivetrainConstants.frontRightDriveMotorConfig, 
    DrivetrainConstants.frontRightInvertDriveEncoder, 
    DrivetrainConstants.frontRightAngleMotorID, 
    DrivetrainConstants.frontRightAngleMotorConfig, 
    DrivetrainConstants.frontRightInvertAngleEncoder,  
    DrivetrainConstants.frontRightAbsoluteEncoderOffset, 
    DrivetrainConstants.frontRightInvertAbsoluteEncoder
  );
  
  private SwerveModule backLeftModule = new SwerveModule(
    DrivetrainConstants.backLeftDriveMotorID, 
    DrivetrainConstants.backLeftDriveMotorConfig, 
    DrivetrainConstants.backLeftInvertDriveEncoder, 
    DrivetrainConstants.backLeftAngleMotorID, 
    DrivetrainConstants.backLeftAngleMotorConfig, 
    DrivetrainConstants.backLeftInvertAngleEncoder,   
    DrivetrainConstants.backLeftAbsoluteEncoderOffset, 
    DrivetrainConstants.backLeftInvertAbsoluteEncoder
  );

  private SwerveModule backRightModule = new SwerveModule(
    DrivetrainConstants.backRightDriveMotorID, 
    DrivetrainConstants.backRightDriveMotorConfig,
    DrivetrainConstants.backRightInvertDriveEncoder, 
    DrivetrainConstants.backRightAngleMotorID, 
    DrivetrainConstants.backRightAngleMotorConfig, 
    DrivetrainConstants.backRightInvertAngleEncoder,  
    DrivetrainConstants.backRightAbsoluteEncoderOffset, 
    DrivetrainConstants.backRightInvertAbsoluteEncoder
  );

  private Gyroscope gyroscope = new Gyroscope(new AHRS(DrivetrainConstants.GyroscopeCommunicationType), DrivetrainConstants.invertGyroscope);
  
  private PPHolonomicDriveController controller = new PPHolonomicDriveController(ModuleConstants.drivePID, ModuleConstants.anglePID);

  private VisionProcessingUnit frontUnit = VisionProcessingUnit.getUnit(CameraPosition.Front);

  private VisionProcessingUnit leftUnit = VisionProcessingUnit.getUnit(CameraPosition.Left);

  private VisionProcessingUnit rightUnit = VisionProcessingUnit.getUnit(CameraPosition.Right);


  SwerveDrivePoseEstimator odometer = new SwerveDrivePoseEstimator(
    DrivetrainConstants.kinematics, 
    this.getRotation2d(), 
    this.getModulePositions(), 
    RobotConstants.initialPose
    );

  public CustomSwerve() {
    // For reseting the gyro.
    new Thread( 
    () -> {
      try {
        while (gyroscope.isCalibrating()) {
          Thread.sleep(10);
        }
        this.resetRobotAngle();

      } catch (Exception e) {
        e.printStackTrace();
      }

    }
    ).start();

    // Configuring the autobuilder
    AutoBuilder.configure(
      this::getPose, 
      this::resetOdometry, 
      this::getRobotRelativeSpeeds, 
      (speeds, feedworwards) -> this.drive(speeds), 
      controller, 
      RobotConstants.config, 
      () -> DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get() == DriverStation.Alliance.Red, 
      this
    );
  }

  @Override
  public void periodic() {
  }

  public void updateOdometer() {

    if (frontUnit.canEstimatePose()) {
      odometer.addVisionMeasurement(frontUnit.getEstimatedPose2d(), frontUnit.getEstimate().timestampSeconds);
    }
    if (leftUnit.canEstimatePose()) {
      odometer.addVisionMeasurement(leftUnit.getEstimatedPose2d(), rightUnit.getEstimate().timestampSeconds);
    }
    if (rightUnit.canEstimatePose()) {
      odometer.addVisionMeasurement(rightUnit.getEstimatedPose2d(), rightUnit.getEstimate().timestampSeconds);
    }

    odometer.update(this.getRotation2d(), this.getModulePositions());

  }

  public Pose2d getPose() {
    return odometer.getEstimatedPosition();
  }

  public double getRobotAngle() {
    return Math.IEEEremainder(gyroscope.getAngle(), 360);
  }

  public Rotation2d getRotation2d() {
    return Rotation2d.fromDegrees(this.getRobotAngle());
  }

  public SwerveModulePosition[] getModulePositions() {
    return new SwerveModulePosition[] {
      frontLeftModule.getPosition(),
      frontRightModule.getPosition(),
      backLeftModule.getPosition(),
      backRightModule.getPosition()
    };
  }


  // TODO: Bu iki metod baya yanlış olabilir. Kontrol et.
  // doğru gibi ama wpilib dokümanlarından bakmakta fayda var
  public ChassisSpeeds getFieldRelativeSpeeds() {
    return DrivetrainConstants.kinematics.toChassisSpeeds(this.getModuleStates());
  }

  public ChassisSpeeds getRobotRelativeSpeeds() {
    return ChassisSpeeds.fromFieldRelativeSpeeds(this.getFieldRelativeSpeeds(), this.getRotation2d());
  }

  // TODO: Bu böyle doğru mu kontrol et.
  // okay gibi duruyor
  public void resetOdometry(Pose2d pose) {
    odometer.resetPosition(this.getRotation2d(), this.getModulePositions(), pose);
  }

  public void resetRobotAngle() {
    gyroscope.reset();
  }

  public void stop() {
    frontLeftModule.stop();
    frontRightModule.stop();
    backLeftModule.stop();
    backRightModule.stop();
  }

  // Input in Front Left -> FrontRight -> BackLeft -> BackRight order
  public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, ModuleConstants.driveMaxSpeed);
    
    frontLeftModule.setDesiredState(desiredStates[0]);
    frontRightModule.setDesiredState(desiredStates[1]);
    backLeftModule.setDesiredState(desiredStates[2]);
    backRightModule.setDesiredState(desiredStates[3]);
  }

  public SwerveModuleState[] getModuleStates() {
    return new SwerveModuleState[] {
      frontLeftModule.getState(),
      frontRightModule.getState(),
      backLeftModule.getState(),
      backRightModule.getState()
    };
  }

  public void drive(ChassisSpeeds speeds) {
    this.setModuleStates(DrivetrainConstants.kinematics.toSwerveModuleStates(speeds));
  }

  public void drive(double xSpeed, double ySpeed, double rSpeed, DriveType driveType) {
    
    ChassisSpeeds speeds;

    if (driveType == DriveType.FieldRelative) {
      speeds = ChassisSpeeds.fromRobotRelativeSpeeds(xSpeed, ySpeed, rSpeed, this.getRotation2d());
    } else {
      speeds = new ChassisSpeeds(xSpeed, ySpeed, rSpeed);
    }

    this.setModuleStates(DrivetrainConstants.kinematics.toSwerveModuleStates(speeds));
  }

  public Pose2d getSimPose() {
    return new Pose2d();
  }
}
