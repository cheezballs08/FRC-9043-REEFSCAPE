package frc.robot.constants;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.MagnetSensorConfigs;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;

import com.revrobotics.spark.config.SparkMaxConfig;

public class ElevatorConstants {

  // PIVOT to ARM 18 DEG
  // ARM TO CORAL HOLD 145 DEG
  // CORAL HOLD 53 DEG
  // FEED 53 DEG

  public static final double elevatorHeight = 2;
  public static final double restHeight = 0;

  /* Coral Intake coral dalcıklarına paralel olunca
  public static final double coralL1Height = 0;
  public static final double coralL2Height = 0.405;
  public static final double coralL3Height = 0.805;
  public static final double coralL4Height = 1.65;
  public static final double coralFeedHeight = 0.15;
  */

  public static final double coralL1Height = 0;
  public static final double coralL2Height = 0.19;
  public static final double coralL3Height = 0.60;
  public static final double coralL4Height = 1.65;
  public static final double coralFeedHeight = 0.10;

  public static final double algeaStartHeight = 0;
  public static final double algeaOutputHeight = 0;
  public static final double algeaStage1Height = 0.60;
  public static final double algeaStage2Height = 0;

  public static final double startingHeight = restHeight;
  
  public static final double elevatorMass = 22;
  
  public static final double drumRadius = 0.03343;

  public static final double gearing = 11.28;
  
  public static final SparkBaseConfig motorDefaultConfig = new SparkMaxConfig()
  .smartCurrentLimit(0)
  .idleMode(IdleMode.kBrake);
  
  public static final int motor1ID = 0;
  public static final MotorType motor1type = MotorType.kBrushless;
  public static final SparkBaseConfig motor1Config = new SparkMaxConfig()
  .apply(motorDefaultConfig)
  .inverted(false);
    
  public static final int motor2ID = 0;
  public static final MotorType motor2type = MotorType.kBrushless;
  public static final SparkBaseConfig motor2Config = new SparkMaxConfig()
  .apply(motorDefaultConfig)
  .inverted(false);

  public static final int encoderID = 0;

  public static final CANcoderConfiguration encoderConfiguration = new CANcoderConfiguration()
  .withMagnetSensor(
    new MagnetSensorConfigs()
    .withAbsoluteSensorDiscontinuityPoint(1)
    .withSensorDirection(SensorDirectionValue.Clockwise_Positive) 
  );

  public static final double encoderPositionConversionFactor = 0.0;
  public static final double encoderVelocityConversionFactor = 0.0;

  public static final double P = 60;
  public static final double I = 80;
  public static final double IZ = 0.2;
  public static final double D = 0.1;

  public static final Constraints constraints = new Constraints(100, 100);

  public static final double S = 0;
  public static final double G = 0.1;
  public static final double V = 0;
  public static final double A = 0;

  public static final Transform3d robotToElevator = new Transform3d(
    0.16,
    -0.02,
    0.245,
    new Rotation3d()
  );
}
