package frc.robot.constants;

import com.pathplanner.lib.config.PIDConstants;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;

public class ModuleConstants {
  
  public static final double wheelDiameter = 0;

  public static final double wheelCoefficientOfFriction = 0;

  public static final MotorType driveMotorType = MotorType.kBrushless;
  public static final MotorType angleMotorType = MotorType.kBrushless;

  public static final IdleMode idleMode = IdleMode.kBrake;

  public static final int driveCurrentLimit = 0;
  public static final int angleCurrentLimit = 0;

  public static final SparkBaseConfig driveMotorDefaultConfig = new SparkMaxConfig()
  .smartCurrentLimit(driveCurrentLimit)
  .idleMode(idleMode);
      
  public static final SparkBaseConfig angleMotorDefaultConfig = new SparkMaxConfig()
  .smartCurrentLimit(angleCurrentLimit)
  .idleMode(idleMode);

  // Meters
  public static final double driveMaxSpeed = 7;
  public static final double driveMaxAcceleration = 0;

  // Radians
  public static final double angleMaxSpeed = 4;
  public static final double angleMaxAcceleration = 0;

  public static final double driveMotorGearRatio = 0;
  // Rotation to meters.
  public static final double driveEncoderPositionConversionFactor = 0;
  // Rotation per minute to meters per second
  public static final double driveEncoderSpeedConversionFactor = 0;

  public static final double angleMotorGearRatio = 0;
  // Rotations to radians
  public static final double angleEncoderPositionConversionFactor = 0;
  // rotations per minute to radians per second
  public static final double angleEncoderSpeedConversionFactor = 0;

  public static final double mangeticEncoderPositionConversionFactor = 0;
  public static final double mangeticEncoderSpeedConversionFactor = 0;

  public static final double PDrive = 0.00023;
  public static final double IDrive = 0.0000002;
  public static final double DDrive = 1;
  public static final double IZDrive = 0;

  public static final Constraints driveConstraints = new Constraints(driveMaxSpeed, driveMaxAcceleration);

  public static final PIDConstants drivePID = new PIDConstants(20, 0, 0, 0);

  public static final double PAngle = 0.0020645;
  public static final double IAngle = 0;
  public static final double DAngle = 0;
  public static final double IZAngle = 0;

  public static final Constraints angleConstraints = new Constraints(driveMaxSpeed, driveMaxAcceleration);

  public static final PIDConstants anglePID = new PIDConstants(PAngle, IAngle, DAngle, IZAngle);

/*  public static final ModuleConfig moduleConfig = new ModuleConfig(
      wheelDiameter / 2,
      driveMaxSpeed, 
      wheelCoefficientOfFriction, 
      DCMotor.getNEO(1).withReduction(driveMotorGearRatio), 
      driveCurrentLimit, 
      1
  );*/

}
