package frc.robot.commands.teleop.climb;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ClimbCostants;
import frc.robot.subsystems.climb.ClimbSubsystem;

public class ClimbBackward extends Command {

  ClimbSubsystem subsystem;

  public ClimbBackward(ClimbSubsystem subsystem) {
    this.subsystem = subsystem;

    addRequirements(this.subsystem);
  }

  @Override
  public void initialize() {
    subsystem.setSpeed(ClimbCostants.climbSpeed);
  }

  @Override
  public void execute() {
    subsystem.setSpeed(ClimbCostants.climbSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    subsystem.setSpeed(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
