package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.IndexerSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

public class ShootCommand extends CommandBase {

    ShooterSubsystem shooterSubsystem;
    IndexerSubsystem indexerSubsystem;

    public ShootCommand(ShooterSubsystem shooterSubsystem, IndexerSubsystem indexerSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        this.indexerSubsystem = indexerSubsystem;

        addRequirements(shooterSubsystem, indexerSubsystem);

    }

    @Override
    public void initialize() { // runs the command

    }


    @Override
    public void execute() {
        if (shooterSubsystem.isTargetVelocity()) {
            shooterSubsystem.openGate();
            indexerSubsystem.runIndexer();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.closeGate();
        indexerSubsystem.stopIndexer();
    }
}