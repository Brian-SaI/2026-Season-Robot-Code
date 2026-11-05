package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class GobildaIndicatorLight {

    public enum Color {
        YELLOW_GREEN(0.4),
        RED(0.287),
        YELLOW(0.388),
        GREEN(0.5),
        BLUE(0.611),
        OFF(0.0);

        double pwmValue;

        Color(double pwmValue) {
            this.pwmValue = pwmValue;
        }
    }

    Servo indicatorLight;

    public GobildaIndicatorLight(HardwareMap hardwareMap, String deviceName) {
        indicatorLight = hardwareMap.get(Servo.class, deviceName);
    }

    public void setColor(double pwmValue) {
        indicatorLight.setPosition(pwmValue);
    }

    public void setColor(Color color) {
            indicatorLight.setPosition(color.pwmValue);
        }

}
