package frc.robot.subsystems.elevator;

import frc.robot.utils.Logger;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ElevatorConstants;

public class ElevatorSimSubsystem extends SubsystemBase implements ElevatorSubsystem {

  ElevatorSim simulation;

  ProfiledPIDController controller;

  ElevatorFeedforward feedforward;

  MechanismLigament2d ligament;

  public ElevatorSimSubsystem() {
    this.simulation  = new ElevatorSim(
      DCMotor.getNEO(2), 
      ElevatorConstants.gearing, 
      ElevatorConstants.elevatorMass, 
      ElevatorConstants.drumRadius, 
      0, 
      ElevatorConstants.elevatorHeight, 
      true, 
      ElevatorConstants.startingHeight, 
      0,
      0
    );
    
    this.controller = new ProfiledPIDController(
      ElevatorConstants.P, 
      ElevatorConstants.I, 
      ElevatorConstants.D, 
      ElevatorConstants.constraints
    );

    this.feedforward = new ElevatorFeedforward(
      ElevatorConstants.S, 
      ElevatorConstants.G, 
      ElevatorConstants.V, 
      ElevatorConstants.A
    );
    
    this.ligament = new MechanismLigament2d(
      "elevator", 
      ElevatorConstants.startingHeight, 
      90
    );  
  }

  @Override
  public void periodic() {
    
    if (RobotState.isEnabled()) {
      simulation.update(0.020);
    }

    Logger.log("Elevator/Encoder/Position", simulation.getPositionMeters());
    Logger.log("Elevator/Controller/SetpointPosition", controller.getSetpoint().position);
    Logger.log("Elevator/Controller/SetpointVelocity", controller.getSetpoint().velocity);
    Logger.log("Elevator/Controller/PositionError", controller.getPositionError());
    Logger.log("Elevator/Controller/VelocityError", controller.getVelocityError());
    Logger.log("Elevator/Controller/AccumulatedError", controller.getAccumulatedError());
    Logger.log("Elevator/Controller/AtSetpoint", controller.atSetpoint());
      
    this.ligament.setLength(simulation.getPositionMeters());
  }

  @Override
  public void setElevatorPosition(double desiredPosition) {
    double currentPosition = simulation.getPositionMeters();
    
    double currentVelocity = simulation.getVelocityMetersPerSecond();

    double output = controller.calculate(currentPosition, desiredPosition);

    output += feedforward.calculate(currentVelocity);

    this.setVoltages(output);
  }

  @Override
  public void setVoltages(double voltage) {
    simulation.setInputVoltage(voltage);
  }

  @Override
  public MechanismLigament2d getLigament() {
    return ligament;
  }

  @Override
  public Transform3d getElevatorTransform() {
    return new Transform3d(
      0, 
      0, 
      simulation.getPositionMeters(), 
    new Rotation3d()
    );
  }

  @Override
  public double getHeight() {
    return simulation.getPositionMeters();
  }

  @Override
  public boolean isAtSetpoint() {
    return controller.atSetpoint();
  }
}
