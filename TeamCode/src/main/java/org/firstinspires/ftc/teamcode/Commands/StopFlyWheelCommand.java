package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

public class StopFlyWheelCommand extends CommandBase {

    ShooterSubsystem shooterSubsystem;

    public StopFlyWheelCommand(ShooterSubsystem shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;

        addRequirements(shooterSubsystem);
    }

    @Override
    public void initialize() { // runs the command
        shooterSubsystem.runShooter(0,0);
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