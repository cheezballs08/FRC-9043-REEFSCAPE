package frc.robot.utils;

import com.revrobotics.RelativeEncoder;

// TODO: Bu doğru yapılmış mı bak
// eğer quadrature encoderi sparka takmak içinse iş görebilir gibi, eğer thrifty encoderleri sparka takmak içinse yanlış çünkü onlar absolute encoder ve analoglar
public class SparkEncoder {
  RelativeEncoder encoder;

  boolean inverted;

  double positionConversionFactor;
  double velocityConversionFactor;

  public SparkEncoder(RelativeEncoder encoder) {
    this.encoder = encoder;
    this.inverted = false;

    this.positionConversionFactor = 1.0;
    this.velocityConversionFactor = 1.0;
  }

  public double getPosition() {
    // ! Özellike bu.
    return (inverted ? -1.0 : 1.0) * encoder.getPosition() * positionConversionFactor;
  }

  public double getVelocity() {
    // ! Ve bu.
    return (inverted ? -1.0 : 1.0) * encoder.getVelocity() * velocityConversionFactor;
  }

  public void setInverted(boolean inverted) {
    this.inverted = inverted;
  }

  public void setPositionConversionFactor(double positionConversionFactor) {
    this.positionConversionFactor = positionConversionFactor;
  }

  public void setVelocityConversionFactor(double velocityConversionFactor) {
    this.velocityConversionFactor = velocityConversionFactor;
  }
}
