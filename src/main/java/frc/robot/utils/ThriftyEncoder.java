package frc.robot.utils;

import com.revrobotics.spark.SparkAnalogSensor;

// TODO: Bu sınıf doğru tanımlanmış mı bak
public class ThriftyEncoder {
	
  private SparkAnalogSensor analogSensor;

  private boolean inverted;

	private double positionConversionFactor;

	private double velocityConversionFactor;

  private double positionOffset;

	public ThriftyEncoder(SparkAnalogSensor sensor) {
		this.analogSensor = sensor;
		this.inverted = false;
		this.positionOffset = 0.0;
	}

	public double getPosition() {
		// ! Özellike bu.
		//average filtreleme uygular değere kendi içinde, amaca göre ya oaky ya yanlış
		// return (inverted ? -1.0 : 1.0) * ((analogSensor.getVoltage() / RobotController.getVoltage5V()) * (Math.PI * 2) - Math.PI);
		
		return (inverted ? -1.0 : 1.0) * analogSensor.getPosition() * positionConversionFactor; 
	}

	public double getVelocity() {
		return (inverted ? -1.0 : 1.0) * analogSensor.getVelocity() * velocityConversionFactor;
	}

	public void setPositionConversionFactor(double factor) {
		this.positionConversionFactor = factor;
	}

	public void setVelocityConversionFactor(double factor) {
		this.velocityConversionFactor = factor;
	}

	public void setInverted(boolean inverted) {
		this.inverted = inverted;       
	}

	public void setPositionOffset(double offset) {
		positionOffset = offset;
	}

	public double getPositionOffset() {
		return positionOffset;
	}

	public double getVirtualPosition() {
		return this.getPosition() - positionOffset;
	}

	public void resetVirtualPosition() {
		positionOffset = this.getPosition();
	}

}