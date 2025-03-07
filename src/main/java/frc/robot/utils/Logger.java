package frc.robot.utils;

import dev.doglog.DogLog;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Logger extends DogLog {

  public static void log(String key, Mechanism2d field) {
    SmartDashboard.putData(key, field);
  }
}
