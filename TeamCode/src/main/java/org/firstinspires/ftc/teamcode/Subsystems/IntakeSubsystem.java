package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

//import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
@Config
public class IntakeSubsystem extends SubsystemBase {

    public static double SPEED=0.5;


    private final DcMotorEx intake;

    public IntakeSubsystem(final HardwareMap hMap) {
        intake =  hMap.get(DcMotorEx.class, "Intake");
    }

    @Override
    public void periodic() {
    }


    public void runIntake() {
        intake.setPower(SPEED);
    }
    public void reverseIntake() {
        intake.setPower(-SPEED);
    }
public void  stopIntake() {
        intake.setPower(0);
}
}