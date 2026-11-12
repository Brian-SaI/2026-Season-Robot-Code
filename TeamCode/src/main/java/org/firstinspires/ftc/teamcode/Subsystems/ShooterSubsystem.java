package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ShooterSubsystem extends SubsystemBase {
    VisionSubsystem visionSubsystem;

    @Config
    public static class ShooterParams {
        public static double VELOCITY_THRESHOLD = 100.0;

        public static double CLOSE_DISTANCE_P = 53.0;
        public static double CLOSE_RPM_P = 3100.0;
        public static double FAR_DISTANCE_P = 69.0;
        public static double FAR_RPM_P = 3300.0;
        public static double CLOSE_DISTANCE_N = 57.0;
        public static double CLOSE_RPM_N = 3100.0;
        public static double FAR_DISTANCE_N = 69.0;
        public static double FAR_RPM_N = 3300.0;


        public static double SHOOTER_P=120.0;
        public static double SHOOTER_D=15.0;
        public static double SHOOTER_F=12.6189;
        public static boolean RESET_PID = false;

        public static double DEFAULT_RPM_P = 3200;
        public static double DEFALUT_RPM_N = 3275;
        public static double pTargetRPM = 0.0;
        public static double nTargetRPM = 0.0;
        public static boolean autoRPM = false;
        public static double targetDistance = 0.0;
        public static long targetDistanceAgeMs = 0;
        public static long targetDistanceLastMs = 0;

        public static double LEFT_GATE_OPEN = 1.0;
        public static double RIGHT_GATE_OPEN = 0.02;
        public static double LEFT_GATE_CLOSED = 0.75;
        public static double RIGHT_GATE_CLOSED = 0.26;
    }

    static final double P_GEAR_RATIO = 24.0 / 37.0;
    private static final double P_TICKS_PER_REVOLUTION = 28;

    static final double N_GEAR_RATIO = 28.0 / 44.0;
    private static final double N_TICKS_PER_REVOLUTION = 28;

    private final DcMotorEx pShooter;
    private final DcMotorEx nShooter;

    private final Servo leftGate;
    private final Servo rightGate;

    private final Telemetry telemetry;

    public ShooterSubsystem(final HardwareMap hMap, final Telemetry telemetry, VisionSubsystem visionSubsystem) {
        this.telemetry = telemetry;
        pShooter = hMap.get(DcMotorEx.class, "PollenShoot");
        nShooter = hMap.get(DcMotorEx.class,"NectarShoot");
        leftGate = hMap.get(Servo.class, "Left Gate");
        rightGate = hMap.get(Servo.class, "Right Gate");

        leftGate.setPosition(ShooterParams.LEFT_GATE_OPEN);
        rightGate.setPosition(ShooterParams.RIGHT_GATE_OPEN);

        this.visionSubsystem = visionSubsystem;

        pShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        nShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        pShooter.setDirection(DcMotorSimple.Direction.REVERSE);
        nShooter.setDirection(DcMotorSimple.Direction.FORWARD);

        pShooter.setVelocity(0);
        nShooter.setVelocity(0);

        setPID();
        closeGate();
    }

    public void setPID() {
        pShooter.setVelocityPIDFCoefficients(ShooterParams.SHOOTER_P, 0, ShooterParams.SHOOTER_D, ShooterParams.SHOOTER_F);
        nShooter.setVelocityPIDFCoefficients(ShooterParams.SHOOTER_P, 0, ShooterParams.SHOOTER_D, ShooterParams.SHOOTER_F);
    }

    @Override
    public void periodic() {
        long now = System.currentTimeMillis();
        Double distanceToTarget = visionSubsystem.getDistanceToTarget();
        telemetry.addData("Target Distance", distanceToTarget == null ? 0 : distanceToTarget);
        if (distanceToTarget != null) {
            ShooterParams.targetDistance = distanceToTarget;
            ShooterParams.targetDistanceLastMs = now;
            ShooterParams.targetDistanceAgeMs = 0;
        } else {
            ShooterParams.targetDistanceAgeMs = now - ShooterParams.targetDistanceLastMs;
        }

        if (ShooterParams.RESET_PID) {
            setPID();
            ShooterParams.RESET_PID = false;
        }

        runShooter(ShooterParams.autoRPM); // Run this every loop for now so we can tune

        telemetry.addData("Pollen RPM", getPollenRPM());
        telemetry.addData("Pollen RPM Target", ShooterParams.pTargetRPM);
        telemetry.addData("Nectar RPM", getNectarRPM());
        telemetry.addData("Nectar RPM Target", ShooterParams.nTargetRPM);
        telemetry.addData("Time", now / 1000);
    }

    // Do nothing command to keep the periodic function being called.
    public Command defaultCommand() {
        return run(() -> {});
    }

    public void runShooter(double nRPM, double pRPM ) {
        ShooterParams.pTargetRPM = pRPM;
        ShooterParams.nTargetRPM = nRPM;
        ShooterParams.autoRPM = false;
        runShooter(false);
    }

    public void runShooter(boolean autoRPM) {
        ShooterParams.autoRPM = autoRPM;
        if (autoRPM && ShooterParams.targetDistanceAgeMs <= 1000) {
            ShooterParams.pTargetRPM = calculatePollenRpm(ShooterParams.targetDistance);
            ShooterParams.nTargetRPM = calculateNectarRpm(ShooterParams.targetDistance);
        } else if (autoRPM) {
            ShooterParams.pTargetRPM = ShooterParams.DEFAULT_RPM_P;
            ShooterParams.nTargetRPM = ShooterParams.DEFALUT_RPM_N;
        }
        setPollenRPM(ShooterParams.pTargetRPM);
        setNectarRPM(ShooterParams.nTargetRPM);
    }

    public boolean isTargetVelocity() {
        return Math.abs(ShooterParams.pTargetRPM - getPollenRPM()) < ShooterParams.VELOCITY_THRESHOLD
            && Math.abs(ShooterParams.nTargetRPM - getNectarRPM()) < ShooterParams.VELOCITY_THRESHOLD;
    }

    public double calculatePollenRpm(double distance) {
        return interpolate(distance,
                ShooterParams.CLOSE_DISTANCE_P,
                ShooterParams.CLOSE_RPM_P,
                ShooterParams.FAR_DISTANCE_P,
                ShooterParams.FAR_RPM_P);
    }

    public double calculateNectarRpm(double distance) {
        return interpolate(distance,
                ShooterParams.CLOSE_DISTANCE_N,
                ShooterParams.CLOSE_RPM_N,
                ShooterParams.FAR_DISTANCE_N,
                ShooterParams.FAR_RPM_N);
    }

    public double interpolate(double x, double x1, double y1, double x2, double y2) {
        double slope = (y2 - y1) / (x2 - x1);
        double intercept = y1 - slope * x1;
        return slope * x + intercept;
    }
    public void openGate() {
        telemetry.addLine("Shooting");
        leftGate.setPosition(ShooterParams.LEFT_GATE_OPEN);
        rightGate.setPosition(ShooterParams.RIGHT_GATE_OPEN);
    }

    public void closeGate() {
        //kicker.setPower(0);
        leftGate.setPosition(ShooterParams.LEFT_GATE_CLOSED);
        rightGate.setPosition(ShooterParams.RIGHT_GATE_CLOSED);
    }

    void setPollenRPM(double rpm) {
        pShooter.setVelocity(pollenRPMToVelocity(rpm));
    }

    void setNectarRPM(double rpm) {
        nShooter.setVelocity(nectarRPMToVelocity(rpm));
    }

    double getPollenRPM() {
        return pollenVelocityToRPM(pShooter.getVelocity());
    }

    double getNectarRPM() {
        return nectarVelocityToRPM(nShooter.getVelocity());
    }

    double nectarVelocityToRPM(double ticksPerSecond) {
        return ticksPerSecond * N_GEAR_RATIO * 60.0 / N_TICKS_PER_REVOLUTION;
    }

    double pollenVelocityToRPM(double ticksPerSecond) {
        return ticksPerSecond * P_GEAR_RATIO * 60.0 / P_TICKS_PER_REVOLUTION;
    }

    double nectarRPMToVelocity(double rpm) {
        return N_TICKS_PER_REVOLUTION * (rpm / 60.0) / N_GEAR_RATIO;
    }

    double pollenRPMToVelocity(double rpm) {
        return P_TICKS_PER_REVOLUTION * (rpm / 60.0) / P_GEAR_RATIO;
    }
}