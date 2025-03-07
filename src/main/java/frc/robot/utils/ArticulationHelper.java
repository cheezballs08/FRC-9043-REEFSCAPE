package frc.robot.utils;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import frc.robot.constants.CoralIntakeConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.RobotConstants;
import frc.robot.subsystems.coral.CoralIntakeSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;

public class ArticulationHelper {
  DrivetrainSubsystem drivetrainSubsystem;
  CoralIntakeSubsystem coralIntakeSubsystem;
  ElevatorSubsystem elevatorSubsystem;

  Pose3d centimeter000 = new Pose3d(0, 0, 0,    new Rotation3d());
  Pose3d centimeter025 = new Pose3d(0, 0, 0.25, new Rotation3d());
  Pose3d centimeter050 = new Pose3d(0, 0, 0.5,  new Rotation3d());
  Pose3d centimeter075 = new Pose3d(0, 0, 0.75, new Rotation3d());
  Pose3d centimeter100 = new Pose3d(0, 0, 1,    new Rotation3d());
  Pose3d centimeter125 = new Pose3d(0, 0, 1.25, new Rotation3d());
  Pose3d centimeter150 = new Pose3d(0, 0, 1.5,  new Rotation3d());
  Pose3d centimeter175 = new Pose3d(0, 0, 1.75, new Rotation3d());
  Pose3d centimeter200 = new Pose3d(0, 0, 2,    new Rotation3d());
  Pose3d centimeter225 = new Pose3d(0, 0, 2.25, new Rotation3d());
  Pose3d centimeter250 = new Pose3d(0, 0, 2.5,  new Rotation3d());

  public ArticulationHelper(DrivetrainSubsystem drivetrainSubsystem, CoralIntakeSubsystem coralIntakeSubsystem, ElevatorSubsystem elevatorSubsystem) {
    this.drivetrainSubsystem = drivetrainSubsystem;
    this.coralIntakeSubsystem = coralIntakeSubsystem;
    this.elevatorSubsystem = elevatorSubsystem;
  }

  public void updateCoralPose() {
    Transform3d elevatorTransform = elevatorSubsystem.getElevatorTransform();

    Rotation3d angle = coralIntakeSubsystem.getAngleTrasnsform().getRotation();

    Transform3d output = new Transform3d(
      CoralIntakeConstants.robotToIntake.getX(), 
      CoralIntakeConstants.robotToIntake.getY(), 
      CoralIntakeConstants.robotToIntake.getZ() + elevatorTransform.getZ(), 
      angle.minus(angle.times(2))
    ); 

    Logger.log("Articulation/Coral", output);
  }

  public void updateElevatorPose() {
    Transform3d elevatorTransform = elevatorSubsystem.getElevatorTransform();

    Logger.log("Articulation/Elevator", ElevatorConstants.robotToElevator.plus(elevatorTransform));
  }

  public void update() {
    this.updateCoralPose();
    this.updateElevatorPose();

    Transform3d fieldToRobot = new Transform3d(new Pose3d(RobotConstants.origin), new Pose3d(drivetrainSubsystem.getSimPose())); 

    Logger.log("Articulation/0 Centimeter",    centimeter000.transformBy(fieldToRobot));
    Logger.log("Articulation/0.25 Centimeter", centimeter025.transformBy(fieldToRobot));
    Logger.log("Articulation/0.5 Centimeter",  centimeter050.transformBy(fieldToRobot));
    Logger.log("Articulation/0.75 Centimeter", centimeter075.transformBy(fieldToRobot));
    Logger.log("Articulation/1 Centimeter",    centimeter100.transformBy(fieldToRobot));
    Logger.log("Articulation/1.25 Centimeter", centimeter125.transformBy(fieldToRobot));
    Logger.log("Articulation/1.5 Centimeter",  centimeter150.transformBy(fieldToRobot));
    Logger.log("Articulation/1.75 Centimeter", centimeter175.transformBy(fieldToRobot));
    Logger.log("Articulation/2 Centimeter",    centimeter200.transformBy(fieldToRobot));
    Logger.log("Articulation/2.25 Centimeter", centimeter225.transformBy(fieldToRobot));
    Logger.log("Articulation/2.5 Centimeter",  centimeter250.transformBy(fieldToRobot));
  }
}
