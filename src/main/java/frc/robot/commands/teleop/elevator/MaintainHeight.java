package frc.robot.commands.teleop.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.ElevatorSubsystem;

public class MaintainHeight extends Command {

  ElevatorSubsystem subsystem;
  
  double desiredPosition;

  public MaintainHeight(ElevatorSubsystem subsystem) {
    this.subsystem = subsystem;
    this.desiredPosition = subsystem.getHeight();

    addRequirements(this.subsystem);
  }

  @Override
  public void initialize() {
    desiredPosition = subsystem.getHeight();
  }

  @Override
  public void execute() {
    subsystem.setElevatorPosition(desiredPosition);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
