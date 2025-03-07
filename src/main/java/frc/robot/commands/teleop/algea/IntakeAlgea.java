// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.algea;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.AlgeaIntakeConstants;
import frc.robot.constants.CoralIntakeConstants;
import frc.robot.subsystems.algea.AlgaeIntakeSubsystem;

public class IntakeAlgea extends Command {

  AlgaeIntakeSubsystem subsystem;

  public IntakeAlgea(AlgaeIntakeSubsystem subsystem) {
    this.subsystem = subsystem;
    
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    subsystem.setSpeeds(AlgeaIntakeConstants.intakeSpeed);
  }

  @Override
  public void execute() {
    subsystem.setSpeeds(CoralIntakeConstants.intakeSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    subsystem.setSpeeds(0);
  }

  @Override
  public boolean isFinished() {
    return subsystem.isSensorActive();
  }
}
