package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.IndexerSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

public class ShootCommand extends CommandBase {

    ShooterSubsystem shooterSubsystem;
    IndexerSubsystem indexerSubsystem;
    Telemetry telemetry;

    public ShootCommand(ShooterSubsystem shooterSubsystem, IndexerSubsystem indexerSubsystem, Telemetry telemetry) {
        this.shooterSubsystem = shooterSubsystem;
        this.indexerSubsystem = indexerSubsystem;
        this.telemetry = telemetry;

        addRequirements(shooterSubsystem);

    }

    public ShootCommand(ShooterSubsystem shooterSubsystem, IndexerSubsystem indexerSubsystem) {

    }

    @Override
    public void initialize() { // runs the command

    }


    @Override
    public void execute() {
        if (shooterSubsystem.isTargetVelocity()) {
            shooterSubsystem.openGate();
            indexerSubsystem.runIndexerFast();
        } else {
            telemetry.addData("Shoot", "Waiting On Velocity");
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.closeGate();
        indexerSubsystem.stopIndexerFast();
    }
}