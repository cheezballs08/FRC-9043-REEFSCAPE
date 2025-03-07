package frc.robot;

import java.util.function.Supplier;

import org.photonvision.simulation.VisionSystemSim;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.auto.drivetrain.ChaseApriltag;
import frc.robot.commands.auto.drivetrain.ChaseBestApriltag;
import frc.robot.commands.base.DriveCommand;
import frc.robot.commands.teleop.algea.IntakeAlgea;
import frc.robot.commands.teleop.algea.OuttakeAlgea;
import frc.robot.commands.teleop.climb.ClimbBackward;
import frc.robot.commands.teleop.climb.ClimbForward;
import frc.robot.commands.teleop.coral.AngleCoralIntake;
import frc.robot.commands.teleop.coral.IntakeCoral;
import frc.robot.commands.teleop.coral.MaintainAngle;
import frc.robot.commands.teleop.coral.OuttakeCoral;
import frc.robot.commands.teleop.elevator.MaintainHeight;
import frc.robot.commands.teleop.elevator.MoveElevator;
import frc.robot.constants.AlgeaIntakeConstants;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.ControllerConstants;
import frc.robot.constants.CoralIntakeConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.RobotConstants;
import frc.robot.subsystems.algea.AlgaeIntakeSubsystem;
import frc.robot.subsystems.climb.ClimbSubsystem;
import frc.robot.subsystems.coral.CoralIntakeSimSubsystem;
import frc.robot.subsystems.coral.CoralIntakeSubsystem;
import frc.robot.subsystems.drivetrain.DefaultSwerve;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.subsystems.elevator.ElevatorSimSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.units.VisionProcessingUnit;
import frc.robot.utils.ArticulationHelper;
import frc.robot.utils.CameraPosition;
import frc.robot.utils.DriveType;
import frc.robot.utils.Logger;
import frc.robot.utils.MechansimSim;

@SuppressWarnings("unused")
public class RobotContainer {

  /* <--------------------------------------------------------------------------------------------------------------------> */

  CommandXboxController controller = new CommandXboxController(ControllerConstants.controllerPort);

  Trigger x = controller.x();
  Trigger a = controller.a();
  Trigger b = controller.b();
  Trigger y = controller.y();
  Trigger rb = controller.rightBumper();
  Trigger rt = controller.rightTrigger(0.1);
  Trigger lb = controller.leftBumper();
  Trigger lt = controller.leftTrigger(0.1);

  /* <--------------------------------------------------------------------------------------------------------------------> */

  VisionProcessingUnit frontUnit = VisionProcessingUnit.getUnit(CameraPosition.Front);
  VisionProcessingUnit leftUnit = VisionProcessingUnit.getUnit(CameraPosition.Left);
  VisionProcessingUnit rightUnit = VisionProcessingUnit.getUnit(CameraPosition.Right);

  VisionSystemSim visionSimulation = VisionProcessingUnit.getSimulation();

  /* <--------------------------------------------------------------------------------------------------------------------> */

  DrivetrainSubsystem drivetrainSubsystem = new DefaultSwerve();

  DriveCommand teleopDriveCommand = new DriveCommand(
    drivetrainSubsystem,
    DriveType.FieldRelative,
    () -> Math.abs(controller.getLeftY()) > ControllerConstants.deadband ? controller.getLeftY() * -3 : 0,
    () -> Math.abs(controller.getLeftX()) > ControllerConstants.deadband ? controller.getLeftX() * -3 : 0,
    () -> Math.abs(controller.getRightX()) > ControllerConstants.deadband ? controller.getRightX() * 3 : 0 
  );

  // 0.70
  // -0.03
  
  // 0.70
  // 0.3

  // 0.60
  // 0.20

  InstantCommand resetOdometry = new InstantCommand(() -> drivetrainSubsystem.resetOdometry(RobotConstants.initialPose));

  InstantCommand fixOdometry = new InstantCommand(() -> {
    this.disableEstimation(false);
    drivetrainSubsystem.updateOdometer();
    this.disableEstimation(true);
  }); 

  InstantCommand cancelCommands = new InstantCommand(() -> CommandScheduler.getInstance().cancelAll());

  /* <--------------------------------------------------------------------------------------------------------------------> */

  CoralIntakeSubsystem coralIntakeSubsystem = new CoralIntakeSimSubsystem();

  AngleCoralIntake toRestAngle = new AngleCoralIntake(coralIntakeSubsystem, CoralIntakeConstants.restAngle);
  AngleCoralIntake toFeedAngle = new AngleCoralIntake(coralIntakeSubsystem, CoralIntakeConstants.feedAngle);
  AngleCoralIntake toL1Angle = new AngleCoralIntake(coralIntakeSubsystem, CoralIntakeConstants.L1Angle);
  AngleCoralIntake toL2Angle = new AngleCoralIntake(coralIntakeSubsystem, CoralIntakeConstants.L2Angle);
  AngleCoralIntake toL3Angle = new AngleCoralIntake(coralIntakeSubsystem, CoralIntakeConstants.L2Angle);
  AngleCoralIntake toL4Angle = new AngleCoralIntake(coralIntakeSubsystem, CoralIntakeConstants.L4Angle);
  
  IntakeCoral intakeCoral = new IntakeCoral(coralIntakeSubsystem);
  OuttakeCoral outtakeCoral = new OuttakeCoral(coralIntakeSubsystem);

  /* <--------------------------------------------------------------------------------------------------------------------> */

  ElevatorSubsystem elevatorSubsystem = new ElevatorSimSubsystem();
  MoveElevator toRestHeight = new MoveElevator(elevatorSubsystem, ElevatorConstants.restHeight);

  MoveElevator toCoralFeedHeight = new MoveElevator(elevatorSubsystem, ElevatorConstants.coralFeedHeight);
  MoveElevator toL1CoralHeight = new MoveElevator(elevatorSubsystem, ElevatorConstants.coralL1Height);
  MoveElevator toL2CoralHeight = new MoveElevator(elevatorSubsystem, ElevatorConstants.coralL2Height);
  MoveElevator toL3CoralHeight = new MoveElevator(elevatorSubsystem, ElevatorConstants.coralL3Height);
  MoveElevator toL4CoralHeight = new MoveElevator(elevatorSubsystem, ElevatorConstants.coralL4Height);

  MoveElevator toAlgeaStartHeight = new MoveElevator(elevatorSubsystem, ElevatorConstants.algeaStartHeight);
  MoveElevator toAlgeaOutputHeight = new MoveElevator(elevatorSubsystem, ElevatorConstants.algeaOutputHeight);
  MoveElevator toAlgeaStage1Height = new MoveElevator(elevatorSubsystem, ElevatorConstants.algeaStage1Height);
  MoveElevator toAlgeaStage2Height = new MoveElevator(elevatorSubsystem, ElevatorConstants.algeaStage2Height);

  /* <--------------------------------------------------------------------------------------------------------------------> */

  /*AlgaeIntakeSubsystem algaeIntakeSubsystem = new AlgaeIntakeSubsystem();

  IntakeAlgea intakeAlgea = new IntakeAlgea(algaeIntakeSubsystem);
  OuttakeAlgea outtakeAlgea = new OuttakeAlgea(algaeIntakeSubsystem);

  /* <--------------------------------------------------------------------------------------------------------------------> */
   
  /*ClimbSubsystem climbSubsystem = new ClimbSubsystem();

  ClimbForward climbForward = new ClimbForward(climbSubsystem);
  ClimbBackward climbBackward = new ClimbBackward(climbSubsystem);

  /* <--------------------------------------------------------------------------------------------------------------------> */
  
  MechansimSim mechansimSim = new MechansimSim(coralIntakeSubsystem, elevatorSubsystem);

  ArticulationHelper articulationHelper = new ArticulationHelper(drivetrainSubsystem, coralIntakeSubsystem, elevatorSubsystem);

  /* <--------------------------------------------------------------------------------------------------------------------> */

  public RobotContainer() {
    this.disableEstimation(true);
  }

  private void configureBindings() {
    drivetrainSubsystem.setDefaultCommand(teleopDriveCommand);
    coralIntakeSubsystem.setDefaultCommand(new AngleCoralIntake(coralIntakeSubsystem, CoralIntakeConstants.restAngle));
    elevatorSubsystem.setDefaultCommand(new MoveElevator(elevatorSubsystem, ElevatorConstants.restHeight));

    lb.and(lt).onTrue(fixOdometry);

    rb.and(rt).onTrue(cancelCommands);

    rb.and(x).onTrue(
      new ChaseBestApriltag(
        drivetrainSubsystem, 
        0.70, 
        0, 
        0
      )
    );

    rb.and(b).onTrue(
      new ChaseBestApriltag(
        drivetrainSubsystem, 
        0.50, 
        0.31, 
        0
      )
    );


    lb.and(x).onTrue(
      new ParallelCommandGroup(
        new MoveElevator(elevatorSubsystem, ElevatorConstants.coralL3Height),
        new AngleCoralIntake(coralIntakeSubsystem, CoralIntakeConstants.L2Angle)
      )
    );

    lb.and(b).onTrue(
      new ParallelCommandGroup(
        new MoveElevator(elevatorSubsystem, ElevatorConstants.coralFeedHeight),
        new AngleCoralIntake(coralIntakeSubsystem, CoralIntakeConstants.feedAngle)
      )
    );
  }

  public void initialize() {
    RobotConstants.alliance = DriverStation.getAlliance().orElse(Alliance.Blue);
    
    RobotConstants.updateInitialPose();
    AutoConstants.updatePoses();
    AutoConstants.logPoses();
    
    this.configureBindings();
    this.namedCommands();

    drivetrainSubsystem.resetOdometry(RobotConstants.initialPose);
  }

  public void periodic() {
    frontUnit.update();
    leftUnit.update();
    rightUnit.update();

    visionSimulation.update(drivetrainSubsystem.getSimPose());

    mechansimSim.update();
    articulationHelper.update();

    RobotConstants.alliance = DriverStation.getAlliance().orElse(Alliance.Blue);

    Logger.log("Robot/Alliance", RobotConstants.alliance);
  }

  public void namedCommands() {
    NamedCommands.registerCommand("CoralDrop1Pose", AutoBuilder.pathfindToPose(AutoConstants.coralDrop1Pose, AutoConstants.pathConstraints));
    NamedCommands.registerCommand("CoralDrop2Pose", AutoBuilder.pathfindToPose(AutoConstants.coralDrop2Pose, AutoConstants.pathConstraints));
    NamedCommands.registerCommand("CoralDrop3Pose", AutoBuilder.pathfindToPose(AutoConstants.coralDrop3Pose, AutoConstants.pathConstraints));
    NamedCommands.registerCommand("CoralDrop4Pose", AutoBuilder.pathfindToPose(AutoConstants.coralDrop4Pose, AutoConstants.pathConstraints));
    NamedCommands.registerCommand("CoralDrop5Pose", AutoBuilder.pathfindToPose(AutoConstants.coralDrop5Pose, AutoConstants.pathConstraints));
    NamedCommands.registerCommand("CoralDrop6Pose", AutoBuilder.pathfindToPose(AutoConstants.coralDrop6Pose, AutoConstants.pathConstraints));

    NamedCommands.registerCommand("LeftFeederPose", AutoBuilder.pathfindToPose(AutoConstants.leftFeederPose, AutoConstants.pathConstraints));
    NamedCommands.registerCommand("RightFeederPose", AutoBuilder.pathfindToPose(AutoConstants.rightFeederPose, AutoConstants.pathConstraints));

    NamedCommands.registerCommand("AlgeaOuttakePose", AutoBuilder.pathfindToPose(AutoConstants.algeaOuttakePose, AutoConstants.pathConstraints));

    NamedCommands.registerCommand("ChaseBestApriltagCoralLeft", new ChaseBestApriltag(drivetrainSubsystem, 0.70, -0.05, 0));
    NamedCommands.registerCommand("ChaseBestApriltagCoralRight", new ChaseBestApriltag(drivetrainSubsystem, 0.70, 0.30, 0));

    NamedCommands.registerCommand("L1Position", new ParallelCommandGroup(
      new MoveElevator(elevatorSubsystem, ElevatorConstants.coralL1Height),
      new AngleCoralIntake(coralIntakeSubsystem, CoralIntakeConstants.L1Angle)
    ).raceWith(new WaitCommand(2)));

    NamedCommands.registerCommand("L2Position", new ParallelCommandGroup(
      new MoveElevator(elevatorSubsystem, ElevatorConstants.coralL2Height),
      new AngleCoralIntake(coralIntakeSubsystem, CoralIntakeConstants.L2Angle)
    ).raceWith(new WaitCommand(2)));

    NamedCommands.registerCommand("L3Position", new ParallelCommandGroup(
      new MoveElevator(elevatorSubsystem, ElevatorConstants.coralL3Height),
      new AngleCoralIntake(coralIntakeSubsystem, CoralIntakeConstants.L2Angle)
    ).raceWith(new WaitCommand(2)));

    NamedCommands.registerCommand("L4Position", new ParallelCommandGroup(
      new MoveElevator(elevatorSubsystem, ElevatorConstants.coralL4Height),
      new AngleCoralIntake(coralIntakeSubsystem, CoralIntakeConstants.L4Angle)
    ).raceWith(new WaitCommand(2)));

    NamedCommands.registerCommand("FeedPosition", new ParallelCommandGroup(
      new MoveElevator(elevatorSubsystem, ElevatorConstants.coralFeedHeight),
      new AngleCoralIntake(coralIntakeSubsystem, CoralIntakeConstants.feedAngle)
    ).raceWith(new WaitCommand(2)));

    NamedCommands.registerCommand("RestPosition", new ParallelCommandGroup(
      new MoveElevator(elevatorSubsystem, ElevatorConstants.restHeight),
      new AngleCoralIntake(coralIntakeSubsystem, CoralIntakeConstants.restAngle)
    ).raceWith(new WaitCommand(0.25)));

    NamedCommands.registerCommand("FixOdometry", fixOdometry);
  }

  public void disableEstimation(boolean disable) {
    frontUnit.disableVisionEstimation(disable);
    leftUnit.disableVisionEstimation(disable);
    rightUnit.disableVisionEstimation(disable);
  }

  public Command getAutonomousCommand() {
    return AutoBuilder.buildAuto(AutoConstants.autoName);
  }
}
