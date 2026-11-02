package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSubsystem extends SubsystemBase {

    @Config
    public static class Params {
        public static double SPEED = 0.75;
    }

    private final DcMotorEx intake;

    public IntakeSubsystem(final HardwareMap hMap) {
        intake =  hMap.get(DcMotorEx.class, "Intake");
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void periodic() {
    }

    public void runIntake() {
        intake.setPower(Params.SPEED);
    }

    public void reverseIntake() {
        intake.setPower(-Params.SPEED);
    }

    public void stopIntake() {
        intake.setPower(0);
    }
}
