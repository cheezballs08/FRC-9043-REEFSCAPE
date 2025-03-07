package frc.robot.utils;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class PhotoelectricSensor {
  
  DigitalInput input;

  boolean inverted = false;
  
  public PhotoelectricSensor(int id) {
    this.input = new DigitalInput(id);
  }

  public PhotoelectricSensor(int id, boolean inverted) {
    this.input = new DigitalInput(id);
    this.inverted = inverted;
  }

  public boolean isActivated() {
    return inverted ? !input.get() : input.get();
  }

  public boolean isDeactivated() {
    return inverted ? input.get() : !input.get();
  }

  public Trigger asTrigger() {
    return new Trigger(this::isActivated);
  }
}
