package frc.robot.constants;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class AlgeaIntakeConstants {

  public static double lenght = 0.2;

  public static final int motor1ID = 0;
  public static final int motor2ID = 0;

  public static final MotorType motor1Type = null;
  public static final MotorType motor2Type = null;

  public static final double intakeSpeed = 0.0;
  public static final double outtakeSpeed = 0.0;
  
  public static final SparkBaseConfig motorDefaultConfig = new SparkMaxConfig()
  .smartCurrentLimit(0)
  .idleMode(IdleMode.kBrake);

  public static final SparkBaseConfig motor1Config = new SparkMaxConfig()
  .apply(motorDefaultConfig)
  .inverted(false);

  public static final SparkBaseConfig motor2Config = new SparkMaxConfig()
  .apply(motorDefaultConfig)
  .inverted(false);

  public static final int sensorID = 0;
}
