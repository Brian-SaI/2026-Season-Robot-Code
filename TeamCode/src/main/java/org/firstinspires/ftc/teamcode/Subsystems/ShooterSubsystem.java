package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

//import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class ShooterSubsystem extends SubsystemBase {

    private final DcMotorEx shooter;

    public ShooterSubsystem(final HardwareMap hMap) {
        shooter =  hMap.get(DcMotorEx.class, "Shooter");
    }

    @Override
    public void periodic() {
        // shooter.getVelocity()
    }

    public void runShooter(double velocity) {
        shooter.setVelocity(velocity, AngleUnit.DEGREES);
    }
}