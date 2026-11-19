package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.FlowerSubsystem;

public class FlowerDownCommand extends CommandBase {
    public FlowerSubsystem flowerSubsystem;


    public FlowerDownCommand(FlowerSubsystem flowerSubsystem) {
        this.flowerSubsystem = flowerSubsystem;


        addRequirements(flowerSubsystem);

    }
    @Override
    public void initialize() {

        flowerSubsystem.down();
    }

    @Override
    public boolean isFinished() {
        return true;

    }
}
