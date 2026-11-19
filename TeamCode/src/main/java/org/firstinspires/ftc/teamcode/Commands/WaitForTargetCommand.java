package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.FlowerSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.VisionSubsystem;

public class WaitForTargetCommand extends CommandBase {
    public VisionSubsystem visionSubsystem;


    public WaitForTargetCommand(VisionSubsystem visionSubsystem) {
        this.visionSubsystem = visionSubsystem;


        addRequirements(visionSubsystem);

    }

    @Override
    public void initialize(){
    }

    @Override
    public boolean isFinished() {
        return visionSubsystem.isTargetVisible();
    }
}
