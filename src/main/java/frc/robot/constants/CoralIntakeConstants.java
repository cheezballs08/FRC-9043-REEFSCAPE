package frc.robot.constants;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.MagnetSensorConfigs;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

public class CoralIntakeConstants {

  public static final double gearing = 20;
  
  public static final double intakeLength = 0.2;
  
  public static final double intakeWidth = 0.02;

  public static final double intakeMass = 4;
  
  public static final double momentOfInertia = SingleJointedArmSim.estimateMOI(intakeLength, intakeMass);

  public static final double maximumAngle = Units.degreesToRadians(90);

  public static final double minimumAngle = Units.degreesToRadians(-90);

  public static final MotorType motorType = MotorType.kBrushless; 

  public static final SparkBaseConfig motorDefaultConfig = new SparkMaxConfig()
  .smartCurrentLimit(0)
  .idleMode(IdleMode.kBrake);
  
  public static final int intakeMotor1ID = 0;
  public static final boolean intakeMotor1Inverted = false;
  public static final SparkBaseConfig intakeMotor1Config = new SparkMaxConfig()
  .apply(motorDefaultConfig)
  .inverted(intakeMotor1Inverted);

  public static final int intakeMotor2ID = 0;
  public static final boolean intakeMotor2Inverted = false;
  public static final SparkBaseConfig intakeMotor2Config = new SparkMaxConfig()
  .apply(motorDefaultConfig)
  .inverted(intakeMotor2Inverted);
  
  public static final int angleMotorID = 0;
  public static final boolean angleMotorInverted = false;
  public static final SparkBaseConfig angleMotorConfig = new SparkMaxConfig()
  .apply(motorDefaultConfig)
  .inverted(angleMotorInverted);

  public static final double intakeSpeed = 0.0;
  
  public static final double outtakeSpeed = 0.0;

  public static final double angleOffset = 14.5;
  
  /* Coral dallarına paralel yükseklikler
  public static final double feedAngle = 40 + angleOffset;
  public static final double L1Angle = -10 + angleOffset;
  public static final double L2Angle = -30 + angleOffset;
  public static final double L4Angle = -45 + angleOffset;
  public static final double restAngle = 90;
  public static final double startingAngle = Units.degreesToRadians(restAngle);
  */

  public static final double feedAngle = 50 + angleOffset;
  public static final double L1Angle = -10 + angleOffset;
  public static final double L2Angle = -5 + angleOffset;
  public static final double L4Angle = -48 + angleOffset;
  public static final double restAngle = 90;
  public static final double startingAngle = Units.degreesToRadians(restAngle);
  

  public static final int sensorID = 0; 
  
  public static final int encoderID = 0;

    public static final CANcoderConfiguration encoderConfiguration = new CANcoderConfiguration()
  .withMagnetSensor(
    new MagnetSensorConfigs()
    .withAbsoluteSensorDiscontinuityPoint(0.5)
    .withSensorDirection(SensorDirectionValue.Clockwise_Positive)
  );
  
  public static final double positionConversionConstant = 0;
  public static final double velocityConversionConstant = 0;
  
  // P = 0.2 I = 0.5 D = 0.001 IZ = 1
  public static final double P = 0.2;
  public static final double I = 0.5;
  public static final double D = 0.001;
  public static final double IZ = 1;
  
  public static final Constraints constraints = new Constraints(1000, 1000);

  public static final double S = 0;
  public static final double G = 1;
  public static final double V = 0;
  public static final double A = 0;

  public static final double mechansimOffset = 90;

  public static final Transform3d robotToIntake = new Transform3d(
    0.34, 
    0.21, 
    0.53, 
    new Rotation3d()
  );
}
