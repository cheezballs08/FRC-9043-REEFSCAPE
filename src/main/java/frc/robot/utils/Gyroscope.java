package frc.robot.utils;

import com.studica.frc.AHRS;

public class Gyroscope {
    private AHRS ahrs;
    private boolean isInverted;

    public Gyroscope(AHRS ahrs, boolean isInverted) {
        this.ahrs = ahrs;
        this.isInverted = isInverted;
    }

    public double getAngle() {
        return isInverted ? -ahrs.getAngle() : ahrs.getAngle();
    }

    public void reset() {
        ahrs.reset();
    }

    public boolean isCalibrating() {
        return ahrs.isCalibrating();
    }
}
