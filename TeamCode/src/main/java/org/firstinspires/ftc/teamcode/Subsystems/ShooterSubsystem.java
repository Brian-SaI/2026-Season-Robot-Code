package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

//import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Config
public class ShooterSubsystem extends SubsystemBase {

    public static double VELOCITY_THRESHOLD = 50;
    private final DcMotorEx shooter;
    private final DcMotorEx kicker;
    private double targetVelocity = 0.0;

    public static double SPEED=0.5;

    public ShooterSubsystem(final HardwareMap hMap) {
        shooter =  hMap.get(DcMotorEx.class, "Shooter");
        kicker = hMap.get(DcMotorEx.class, "kicker");
    }

    @Override
    public void periodic() {
        // shooter.getVelocity()
    }

    public void runShooter(double velocity) {
        shooter.setVelocity(velocity, AngleUnit.DEGREES);
        targetVelocity = velocity;
    }
    public boolean isTargetVelocity() {
        return Math.abs(targetVelocity-shooter.getVelocity(AngleUnit.DEGREES)) < VELOCITY_THRESHOLD;
    }

    public void runKicker() {
        kicker.setPower(SPEED);
    }


    public void stopKicker() {
        kicker.setPower(0);
    }
}