package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

//import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Config
public class ShooterSubsystem extends SubsystemBase {

    public static double VELOCITY_THRESHOLD = 50;

    public static double SHOOTER_P=0.0;
    public static double SHOOTER_D=0.0;
    public static double SHOOTER_F=0.0;

    private final DcMotorEx pShooter;
    private final DcMotorEx nShooter;
    //private final DcMotorEx kicker;
    private double targetPVelocity = 0.0;
    private double targetNVelocity = 0.0;

    public static double SPEED=0.5;

    public ShooterSubsystem(final HardwareMap hMap) {
        pShooter =  hMap.get(DcMotorEx.class, "PollenShooter");
        nShooter = hMap.get(DcMotorEx.class,"NectorShooter");
       // kicker = hMap.get(DcMotorEx.class, "kicker");

    pShooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(SHOOTER_P, 0, SHOOTER_D, SHOOTER_F));
    }
    public void setPID() {
        pShooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(SHOOTER_P, 0, SHOOTER_D, SHOOTER_F));
        
    }
    }
    @Override
    public void periodic() {
        // shooter.getVelocity()
    }

    public void runShooter(double nVelocity, double pVelocity ) {
        pShooter.setVelocity(pVelocity, AngleUnit.DEGREES);
        nShooter.setVelocity(nVelocity, AngleUnit.DEGREES);

        targetPVelocity = pVelocity;
        targetNVelocity = nVelocity;
    }
    public boolean isTargetVelocity() {
        return Math.abs(targetPVelocity- pShooter.getVelocity(AngleUnit.DEGREES)) < VELOCITY_THRESHOLD
            && Math.abs(targetNVelocity- nShooter.getVelocity(AngleUnit.DEGREES)) < VELOCITY_THRESHOLD;
    }

    public void runKicker() {
        //kicker.setPower(SPEED);
    }


    public void stopKicker() {
        //kicker.setPower(0);
    }
}