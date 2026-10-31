package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

//import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Config
public class ShooterSubsystem extends SubsystemBase {
    VisionSubsystem visionSubsystem;

    public static double VELOCITY_THRESHOLD = 50;

    public static double CLOSE_DISTANCE=0.0;
    public static double FAR_DISTANCE=0.0;
    //public static double

    public static double SHOOTER_P=0.0;
    public static double SHOOTER_D=0.0;
    public static double SHOOTER_F=0.0;
    public static boolean RESET_PID = false;

    public static double pTargetRPM = 0.0;
    public static double nTargetRPM = 0.0;

    private static final double P_GEAR_RATIO = 20.0 / 40.0;
    private static final double P_TICKS_PER_REVOLUTION = 28;
    private static final double P_TICKS_PER_SECOND_TO_RPM = P_GEAR_RATIO * 60.0 / P_TICKS_PER_REVOLUTION;

    private static final double N_GEAR_RATIO = 24.0 / 48.0;
    private static final double N_TICKS_PER_REVOLUTION = 28;
    private static final double N_TICKS_PER_SECOND_TO_RPM = N_GEAR_RATIO * 60.0 / N_TICKS_PER_REVOLUTION;

    private final DcMotorEx pShooter;
    private final DcMotorEx nShooter;
    //private final DcMotorEx kicker;

    private final Telemetry telemetry;

    public ShooterSubsystem(final HardwareMap hMap, final Telemetry telemetry, VisionSubsystem visionSubsystem) {
        this.telemetry = telemetry;
        pShooter = hMap.get(DcMotorEx.class, "PollenShoot");
        nShooter = hMap.get(DcMotorEx.class,"NectarShoot");
        this.visionSubsystem = visionSubsystem;

        pShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        nShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        pShooter.setVelocity(0);
        nShooter.setVelocity(0);

       // kicker = hMap.get(DcMotorEx.class, "kicker");
    }

    public void setPID() {
        pShooter.setVelocityPIDFCoefficients(SHOOTER_P, 0, SHOOTER_D, SHOOTER_F);
        nShooter.setVelocityPIDFCoefficients(SHOOTER_P, 0, SHOOTER_D, SHOOTER_F);
    }

    @Override
    public void periodic() {
        Double distanceToTarget = visionSubsystem.getDistanceToTarget();
        telemetry.addData("Target Distance", distanceToTarget == null ? 0 : distanceToTarget);

        if (RESET_PID) {
            setPID();
        }

        double pRPM = pShooter.getVelocity() * P_TICKS_PER_SECOND_TO_RPM;
        double nRPM = nShooter.getVelocity() * N_TICKS_PER_SECOND_TO_RPM;
        telemetry.addData("Pollen RPM", pRPM);
        telemetry.addData("Pollen RPM Target", pTargetRPM);

        telemetry.addData("Nectar RPM", nRPM);
        telemetry.addData("Nectar RPM Target", nTargetRPM);

        runShooter(); // Run this every loop for now so we can tune
    }

    // Do nothing command to keep the periodic function being called.
    public Command defaultCommand() {
        return run(() -> {});
    }

    public void runShooter(double nRPM, double pRPM ) {
        pTargetRPM = pRPM;
        nTargetRPM = nRPM;
        runShooter();
    }

    public void runShooter() {
        pShooter.setVelocity(pTargetRPM / P_TICKS_PER_SECOND_TO_RPM);
        nShooter.setVelocity(nTargetRPM / N_TICKS_PER_SECOND_TO_RPM);
    }

    public boolean isTargetVelocity() {
        double pRPM = pShooter.getVelocity() * P_TICKS_PER_SECOND_TO_RPM;
        double nRPM = nShooter.getVelocity() * N_TICKS_PER_SECOND_TO_RPM;
        return Math.abs(pTargetRPM - pRPM) < VELOCITY_THRESHOLD
            && Math.abs(nTargetRPM - nRPM) < VELOCITY_THRESHOLD;
    }

    public double calculatePollenRpm(double distance) {
        return 0.0;
    }
    public double calculateNectarRpm(double distance) {
        return 0.0;
    }
    public double interpolate(double x, double x1, double y1, double x2, double y2) {
        double slope = (y2 - y1) / (x2 - x1);
        double intercept = y1 - slope * x1;
        return slope * x + intercept;
    }
    public void runKicker() {
        //kicker.setPower(SPEED);
    }

    public void stopKicker() {
        //kicker.setPower(0);
    }
}