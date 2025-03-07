package frc.robot.constants;

import com.pathplanner.lib.config.RobotConfig;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.utils.AllianceFlipUtility;
import frc.robot.utils.StartPosition;

public class RobotConstants {

  public static final Pose2d origin = new Pose2d();
  
  public static final Pose2d blueStart1 = new Pose2d(8.150, 7.270, Rotation2d.fromDegrees(180));
  public static final Pose2d blueStart2 = new Pose2d(8.150, 6.180, Rotation2d.fromDegrees(180));
  public static final Pose2d blueStart3 = new Pose2d(8.150, 5.085, Rotation2d.fromDegrees(180));

  public static final Pose2d redStart1 = AllianceFlipUtility.apply(blueStart1);
  public static final Pose2d redStart2 = AllianceFlipUtility.apply(blueStart2);
  public static final Pose2d redStart3 = AllianceFlipUtility.apply(blueStart3);

  public static StartPosition startPosition = StartPosition.Center;

  public static Pose2d initialPose = origin;

  public static final double robotMass = 74.088;
  public static final double robotMomentOfInertia = 0.883;

  public static Alliance alliance;

  public static RobotConfig config;

  public static double robotVoltage = 12;

  public static double mechansimXPosition = 1.15;

  public static double mechansimYPosition = 0.25;
  
  static {
    alliance = DriverStation.getAlliance().orElse(Alliance.Blue);

    try{
      config = RobotConfig.fromGUISettings();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void updateInitialPose() {
    switch (startPosition) {
      case Left:
        initialPose = alliance == Alliance.Blue ? blueStart3 : redStart3;
        break;
      case Center:
        initialPose = alliance == Alliance.Blue ? blueStart2 : redStart2;
        break;
      case Right:
        initialPose = alliance == Alliance.Blue ? blueStart1 : redStart1;
        break;
    }
  }
}
