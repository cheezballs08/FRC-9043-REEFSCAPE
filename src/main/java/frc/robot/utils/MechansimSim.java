package frc.robot.utils;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import frc.robot.constants.RobotConstants;
import frc.robot.subsystems.coral.CoralIntakeSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem;

public class MechansimSim {
  Mechanism2d ui;

  MechanismRoot2d root;

  MechanismLigament2d coralLigament;

  MechanismLigament2d algeaLigament;

  MechanismLigament2d elevatorLigament;

  Mechanism2d mechanism2d;

  public MechansimSim(CoralIntakeSubsystem coralIntakeSubsystem, ElevatorSubsystem elevatorSubsystem) {
    this.ui = new Mechanism2d(2, 2);
    
    this.root = ui.getRoot(
      "MechansimRoot", 
      RobotConstants.mechansimXPosition, 
      RobotConstants.mechansimYPosition
    );

    this.coralLigament = coralIntakeSubsystem.getLigament();

    this.elevatorLigament = elevatorSubsystem.getLigament();

    root.append(elevatorLigament);
    elevatorLigament.append(coralLigament);
  }

  public void update() {
    Logger.log("Mechansim", ui);
  }

}
