package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class FlowerSubsystem extends SubsystemBase {
    public Servo leftFlower;
    public Servo rightFlower;
    public FlowerSubsystem(HardwareMap hMap) {
        leftFlower = hMap.get(Servo.class, "LeftFlower");
        rightFlower = hMap.get(Servo.class, "RightFlower");
        up();
        down();


    }
    public void up() {
        leftFlower.setPosition(1.0);
        rightFlower.setPosition(0.035);

    }
    public void down() {
        leftFlower.setPosition(0.61);
        rightFlower.setPosition(0.46);

    }
}

