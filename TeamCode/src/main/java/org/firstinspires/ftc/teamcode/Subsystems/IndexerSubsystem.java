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

    public static double SLOW_SPEED=0.45;
    public static double FAST_SPEED= 0.75;

    private boolean isSlowRequested = false;
    private boolean isFastRequested = false;

    public IndexerSubsystem(final HardwareMap hardwareMap) {
        indexer = hardwareMap.get(DcMotorEx.class,"Indexer");
        indexer.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        indexer.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void runIndexer() {
        isSlowRequested = true;
        if (!isFastRequested) {
            indexer.setPower(SLOW_SPEED);
        }
    }

    public void runIndexerFast() {
        isFastRequested = true;
        indexer.setPower(FAST_SPEED);
    }
    public void reverseIndexer() {
        isSlowRequested = false;
        isFastRequested = false;
        indexer.setPower(-SLOW_SPEED);
    }

    public void stopIndexer() {
        isSlowRequested = false;
        if (!isFastRequested) {
            indexer.setPower(0);
        }
    }

    public void stopIndexerFast() {
        isFastRequested = false;
        if (isSlowRequested) {
            runIndexer();
        } else {
            stopIndexer();
        }
    }
}

