package frc.robot.constants;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

public class ClimbCostants {
  
  public static final int motorId = 0;

  public static final MotorType motorType = null;

  public static final SparkBaseConfig motorDefaultConfig = new SparkMaxConfig()
  .smartCurrentLimit(0)
  .idleMode(null);

  public static final SparkBaseConfig motorConfig = new SparkMaxConfig()
  .apply(motorDefaultConfig)
  .inverted(false);

  public static final double climbSpeed = 0;
}
