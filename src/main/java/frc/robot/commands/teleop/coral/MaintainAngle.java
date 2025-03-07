// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.teleop.coral;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.coral.CoralIntakeSubsystem;

public class MaintainAngle extends Command {

  CoralIntakeSubsystem subsystem;

  double angle;

  public MaintainAngle(CoralIntakeSubsystem subsystem) {

    this.subsystem = subsystem;
    this.angle = subsystem.getAngle();

    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    angle = subsystem.getAngle();
    subsystem.setAngle(angle);
  }

  @Override
  public void execute() {
    subsystem.setAngle(angle);
  }

  @Override
  public void end(boolean interrupted) {
    subsystem.setAngle(angle);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
