package frc.robot.utils;

import com.ctre.phoenix6.hardware.CANcoder;

// TODO: Bu doğru yapılmış mı bak
// amacını tam anlayamadım ama cancoder bunları kendi yapabiliyor, pehonix6 api doc lardan cancoderconfigurator a bak. invert gibi şeyleri genelde pehonix tunerdan yapmak gerekiyo
public class CANCoderWrapper {
  CANcoder encoder;

  boolean inverted;

  double positionConversionFactor;
  double velocityConversionFactor;

  public CANCoderWrapper(CANcoder encoder) {
    this.encoder = encoder;
    this.inverted = false;

    this.positionConversionFactor = 1.0;
    this.velocityConversionFactor = 1.0;
  }

  public double getPosition() {
    // ! Özellike bu.
    return (inverted ? -1.0 : 1.0) * encoder.getPosition().getValueAsDouble() * positionConversionFactor;
  }

  public double getVelocity() {
    // ! Ve bu.
    return (inverted ? -1.0 : 1.0) * encoder.getVelocity().getValueAsDouble() * velocityConversionFactor;
  }

  public void setInverted(boolean inverted) {
    this.inverted = inverted;
  }

  public double getRawPosition() {
    return encoder.getPosition().getValueAsDouble();
  }

  public double getRawVelocity() {
    return encoder.getVelocity().getValueAsDouble();
  }

  public void setPositionConversionFactor(double positionConversionFactor) {
    this.positionConversionFactor = positionConversionFactor;
  }

  public void setVelocityConversionFactor(double velocityConversionFactor) {
    this.velocityConversionFactor = velocityConversionFactor;
  }
}
