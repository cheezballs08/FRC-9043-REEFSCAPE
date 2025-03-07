package frc.robot.commands.teleop.drivetrain;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.units.VisionProcessingUnit;
import frc.robot.utils.CameraPosition;
import frc.robot.utils.DriveType;

public class AimAtAprilTag extends Command {

  DrivetrainSubsystem drivetrainSubsystem;

  VisionProcessingUnit vision = VisionProcessingUnit.getUnit(CameraPosition.Front);
  
  DriveType driveType;

  Supplier<Double> xSpeedSupplier, ySpeedSupplier;

  double xSpeed, ySpeed, rSpeed;

  int targetId;

  double angleError;

  public AimAtAprilTag(
    DrivetrainSubsystem drivetrainSubsystem,
    DriveType driveType,
    Supplier<Double> xSpeedSupplier,
    Supplier<Double> ySpeedSupplier,
    int targetId
  ) {
    this.drivetrainSubsystem = drivetrainSubsystem;

    this.driveType = driveType;

    this.xSpeedSupplier = xSpeedSupplier;
    
    this.ySpeedSupplier = ySpeedSupplier;

    this.targetId = targetId;

    addRequirements(drivetrainSubsystem);

  }

  public void updateSpeeds() {
    xSpeed = xSpeedSupplier.get();
    ySpeed = ySpeedSupplier.get();
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    this.updateSpeeds();

    drivetrainSubsystem.drive(xSpeed, ySpeed, rSpeed, driveType);
  }

  @Override
  public void end(boolean interrupted) {
    drivetrainSubsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
