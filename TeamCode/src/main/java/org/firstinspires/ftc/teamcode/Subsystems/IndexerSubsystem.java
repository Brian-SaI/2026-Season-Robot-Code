package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

@Config
public class IndexerSubsystem extends SubsystemBase {
    private final DcMotorEx indexer;

    public static double SPEED=0.75;

    public IndexerSubsystem(final HardwareMap hardwareMap) {
        indexer = hardwareMap.get(DcMotorEx.class,"Indexer");
        indexer.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        indexer.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void runIndexer() {
        indexer.setPower(SPEED);
    }
    public void reverseIndexer() {
        indexer.setPower(-SPEED);
    }

    public void stopIndexer() {
        indexer.setPower(0);
    }
}

