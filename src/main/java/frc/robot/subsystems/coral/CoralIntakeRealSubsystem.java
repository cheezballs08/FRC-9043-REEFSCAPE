package frc.robot.subsystems.coral;

import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;

import com.ctre.phoenix6.hardware.CANcoder;
import com.thethriftybot.ThriftyNova;

import frc.robot.utils.Logger;
import frc.robot.constants.CoralIntakeConstants;
import frc.robot.utils.CANCoderWrapper;
import frc.robot.utils.PhotoelectricSensor;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class CoralIntakeRealSubsystem extends SubsystemBase implements CoralIntakeSubsystem {

  ThriftyNova intakeMotor1, intakeMotor2;

  ThriftyNova angleMotor;

  CANCoderWrapper angleEncoder;
  
  ProfiledPIDController angleController;

  ArmFeedforward feedforward;

  PhotoelectricSensor sensor;
  
  public CoralIntakeRealSubsystem() {
    this.intakeMotor1 = new ThriftyNova(CoralIntakeConstants.intakeMotor1ID);
    intakeMotor1.setBrakeMode(true);
    intakeMotor1.setInverted(CoralIntakeConstants.intakeMotor1Inverted);
    
    this.intakeMotor2 = new ThriftyNova(CoralIntakeConstants.intakeMotor2ID);
    intakeMotor2.setBrakeMode(true);
    intakeMotor2.setInverted(CoralIntakeConstants.intakeMotor2Inverted);
    
    this.angleMotor = new ThriftyNova(CoralIntakeConstants.angleMotorID);
    angleMotor.setBrakeMode(true);

    angleMotor.setInverted(CoralIntakeConstants.angleMotorInverted);
    
    CANcoder cancoder = new CANcoder(CoralIntakeConstants.encoderID);

    cancoder.getConfigurator().apply(CoralIntakeConstants.encoderConfiguration);

    this.angleEncoder = new CANCoderWrapper(cancoder);

    this.angleEncoder.setPositionConversionFactor(CoralIntakeConstants.positionConversionConstant);
    this.angleEncoder.setVelocityConversionFactor(CoralIntakeConstants.velocityConversionConstant);

    this.angleController = new ProfiledPIDController(CoralIntakeConstants.P, CoralIntakeConstants.I, CoralIntakeConstants.D, CoralIntakeConstants.constraints);
    this.angleController.setIZone(CoralIntakeConstants.IZ);
  
    this.feedforward = new ArmFeedforward(
      CoralIntakeConstants.S, 
      CoralIntakeConstants.G,
      CoralIntakeConstants.V,
      CoralIntakeConstants.A 
    );

    this.sensor = new PhotoelectricSensor(CoralIntakeConstants.sensorID);
  }

  @Override
  public void periodic() {
    Logger.log("CoralIntake/Speeds/IntakeMotor1", intakeMotor1.get());
    Logger.log("CoralIntake/Speeds/IntakeMotor2", intakeMotor2.get());
    Logger.log("CoralIntake/Speeds/AngleMotor", angleMotor.get());
    
    Logger.log("CoralIntake/Voltages/IntakeMotor1", intakeMotor1.getVoltage());
    Logger.log("CoralIntake/Voltages/IntakeMotor2", intakeMotor2.getVoltage());
    Logger.log("CoralIntake/Voltages/AngleMotor", angleMotor.getVoltage());

    Logger.log("CoralIntake/Encoder/Position", angleEncoder.getPosition());
    Logger.log("CoralIntake/Encoder/Velocity", angleEncoder.getVelocity());
    Logger.log("CoralIntake/Encoder/RawPosition", angleEncoder.getRawPosition());
    Logger.log("CoralIntake/Encoder/RawVelocity", angleEncoder.getRawPosition());
    

    Logger.log("CoralIntake/Controller/SetpointPosition", angleController.getSetpoint().position);
    Logger.log("CoralIntake/Controller/SetpointVelocity", angleController.getSetpoint().velocity);
    Logger.log("CoralIntake/Controller/PositionError", angleController.getPositionError());
    Logger.log("CoralIntake/Controller/VelocityError", angleController.getVelocityError());
    Logger.log("CoralIntake/Controller/AccumulatedError", angleController.getAccumulatedError());
    Logger.log("CoralIntake/Controller/AtSetpoint", angleController.atSetpoint());

    Logger.log("CoralIntake/Sensor", this.isSensorActive());
  }

  public void setSpeeds(double speed) {
    this.intakeMotor1.set(speed);
    this.intakeMotor2.set(speed);
  }

  public void setAngle(double angle) {

    double position = this.angleEncoder.getPosition();
    
    double output = this.angleController.calculate(position, angle);

    output += this.feedforward.calculate(position, this.angleEncoder.getVelocity());

    this.angleMotor.setVoltage(output);
  }

  public void setPosition(double speed, double angle) {
    this.setSpeeds(speed);
    this.setAngle(angle);
  }

  public boolean isAtSetpoint() {
    return this.angleController.atSetpoint();
  }

  public boolean isSensorActive() {
    return this.sensor.isActivated();
  }

  public Trigger getSensorAsTrigger() {
    return this.sensor.asTrigger();
  }

  public MechanismLigament2d getLigament() {
      throw new UnsupportedOperationException("Sim dışı kullanılan metod 'getLigament'");
  }

  @Override
  public Transform3d getAngleTrasnsform() {
    throw new UnsupportedOperationException("Sim dışı kullanılan metod 'getAngleTrasnsform'");
  }

  @Override
  public double getAngle() {
    return angleEncoder.getPosition();
  }
}
