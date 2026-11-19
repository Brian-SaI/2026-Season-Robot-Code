package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.FlowerSubsystem;

public class FlowerUpCommand extends CommandBase {
    public FlowerSubsystem flowerSubsystem;


    public FlowerUpCommand(FlowerSubsystem flowerSubsystem) {
        this.flowerSubsystem = flowerSubsystem;


        addRequirements(flowerSubsystem);

    }

    @Override
    public void initialize() {
        flowerSubsystem.up();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
