package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.IndexerSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;

public class OuttakeCommand extends CommandBase {

    IntakeSubsystem intakeSubsystem;
    IndexerSubsystem indexerSubsystem;
    public OuttakeCommand(IntakeSubsystem intakeSubsystem, IndexerSubsystem indexerSubsystem) {
        this.intakeSubsystem = intakeSubsystem;

        addRequirements(intakeSubsystem, indexerSubsystem);
    }

    @Override
    public void initialize() { // runs the command
        intakeSubsystem.reverseIntake();
        indexerSubsystem.reverseIndexer();
    }

    @Override
    public void execute() {


    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.stopIntake();
        indexerSubsystem.stopIndexer();
    }
}