package org.firstinspires.ftc.teamcode.Commands;

import com.pedropathing.paths.Path;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;

import java.util.function.DoubleSupplier;

/**
 * Continuously drives the SwerveSubsystem, field-centric, from three stick suppliers.
 * This is meant to be set as the SwerveSubsystem's default command, so it's always
 * running whenever no other command needs the subsystem.
 */
public class FollowPathCommand extends CommandBase {
    private final MecanumDrivetrain drivetrain;
    private final Path path;
    private boolean stillRunning;

    public FollowPathCommand(MecanumDrivetrain drivetrain, Path path) {
        this.drivetrain = drivetrain;
        this.path = path;
        addRequirements(this.drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.startFollowing(path);
    }

    @Override
    public void execute() {
        stillRunning = drivetrain.followCurrentPath();
    }

    @Override
    public boolean isFinished() {
        return !stillRunning;
    }
}