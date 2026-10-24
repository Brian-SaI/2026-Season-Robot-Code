package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

public class KickerCommand extends CommandBase {

    ShooterSubsystem shooterSubsystem;

    public KickerCommand(ShooterSubsystem shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;

        addRequirements(shooterSubsystem);
    }

    @Override
    public void initialize() { // runs the command

    }


    @Override
    public void execute() {
        if (shooterSubsystem.isTargetVelocity()) {
            shooterSubsystem.runKicker();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.stopKicker();
    }
}