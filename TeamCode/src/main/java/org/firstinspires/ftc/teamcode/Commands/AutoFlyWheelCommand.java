package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

public class AutoFlyWheelCommand extends CommandBase {

    ShooterSubsystem shooterSubsystem;

    public AutoFlyWheelCommand(ShooterSubsystem shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;

        addRequirements(shooterSubsystem);
    }

    @Override
    public void initialize() { // runs the command
        shooterSubsystem.runShooter(true);
    }


    @Override
    public void execute() {


    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }

}