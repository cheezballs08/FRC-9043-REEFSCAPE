package frc.robot.subsystems.climb;

import com.revrobotics.spark.SparkMax;
import frc.robot.constants.MotorConstants;
import frc.robot.constants.ClimbCostants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSubsystem extends SubsystemBase {

  SparkMax motor;

  public ClimbSubsystem() {
    this.motor = new SparkMax(ClimbCostants.motorId, ClimbCostants.motorType);
    this.motor.configure(ClimbCostants.motorConfig, MotorConstants.resetMode, MotorConstants.persistMode);
  }

  // Motor hızı ayarlama
  public void setSpeed(double speed){
    this.motor.set(speed);
  }

  @Override
  public void periodic() {

  }
}
