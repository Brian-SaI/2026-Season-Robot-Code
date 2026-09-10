package org.firstinspires.ftc.teamcode.Subsystems;

import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

//import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class IntakeSubsystem extends SubsystemBase {

    private final DcMotorEx intake;

    public IntakeSubsystem(final HardwareMap hMap) {
        intake =  hMap.get(DcMotorEx.class, "Intake");
    }

    @Override
    public void periodic() {
    }

    public void runIntake(double power) {
        intake.setPower(power);
    }
}