package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class IndexerSubsystem extends SubsystemBase {
    private final DcMotorEx indexer;

    public static double SPEED=0.5;

    public IndexerSubsystem(final HardwareMap hardwareMap) {
        indexer = hardwareMap.get(DcMotorEx.class,"Indexer");
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

