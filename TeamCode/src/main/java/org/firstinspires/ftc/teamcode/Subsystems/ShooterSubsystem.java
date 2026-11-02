package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ShooterSubsystem extends SubsystemBase {
    VisionSubsystem visionSubsystem;

    @Config
    public static class Params {
        public static double VELOCITY_THRESHOLD = 25;

        public static double CLOSE_DISTANCE = 0.0;
        public static double FAR_DISTANCE = 0.0;

        public static double SHOOTER_P=120.0;
        public static double SHOOTER_D=15.0;
        public static double SHOOTER_F=12.6189;
        public static boolean RESET_PID = false;

        public static double pTargetRPM = 0.0;
        public static double nTargetRPM = 0.0;
    }

    static final double P_GEAR_RATIO = 20.0 / 21.0;
    private static final double P_TICKS_PER_REVOLUTION = 28;

    static final double N_GEAR_RATIO = 20.0 / 22.0;
    private static final double N_TICKS_PER_REVOLUTION = 28;

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

        pShooter.setDirection(DcMotorSimple.Direction.REVERSE);
        nShooter.setDirection(DcMotorSimple.Direction.FORWARD);

        pShooter.setVelocity(0);
        nShooter.setVelocity(0);

        PIDFCoefficients pidf = pShooter.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
        Params.SHOOTER_F = pidf.f;
        Params.SHOOTER_D = pidf.d;
        Params.SHOOTER_P = pidf.p;

       // kicker = hMap.get(DcMotorEx.class, "kicker");
    }

    public void setPID() {
        pShooter.setVelocityPIDFCoefficients(Params.SHOOTER_P, 0, Params.SHOOTER_D, Params.SHOOTER_F);
        nShooter.setVelocityPIDFCoefficients(Params.SHOOTER_P, 0, Params.SHOOTER_D, Params.SHOOTER_F);
    }

    @Override
    public void periodic() {
        Double distanceToTarget = null;//visionSubsystem.getDistanceToTarget();
        telemetry.addData("Target Distance", distanceToTarget == null ? 0 : distanceToTarget);

        if (Params.RESET_PID) {
            setPID();
            Params.RESET_PID = false;
        }

        telemetry.addData("Pollen RPM", getPollenRPM());
        telemetry.addData("Pollen RPM Target", Params.pTargetRPM);

        telemetry.addData("Nectar RPM", getNectarRPM());
        telemetry.addData("Nectar RPM Target", Params.nTargetRPM);

        telemetry.addData("Foo", System.currentTimeMillis() / 1000);

        runShooter(); // Run this every loop for now so we can tune
    }

    // Do nothing command to keep the periodic function being called.
    public Command defaultCommand() {
        return run(() -> {});
    }

    public void runShooter(double nRPM, double pRPM ) {
        Params.pTargetRPM = pRPM;
        Params.nTargetRPM = nRPM;
        runShooter();
    }

    public void runShooter() {
        setPollenRPM(Params.pTargetRPM);
        setNectarRPM(Params.nTargetRPM);
    }

    public boolean isTargetVelocity() {
        return Math.abs(Params.pTargetRPM - getPollenRPM()) < Params.VELOCITY_THRESHOLD
            && Math.abs(Params.nTargetRPM - getNectarRPM()) < Params.VELOCITY_THRESHOLD;
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
        telemetry.addLine("Shooting");
    }

    public void stopKicker() {
        //kicker.setPower(0);
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