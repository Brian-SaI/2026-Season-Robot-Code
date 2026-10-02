package org.firstinspires.ftc.teamcode.Commands;

import com.pedropathing.follower.Follower;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.SwerveDrivetrain;

import java.util.function.DoubleSupplier;

/**
 * Continuously drives the SwerveSubsystem, field-centric, from three stick suppliers.
 * This is meant to be set as the SwerveSubsystem's default command, so it's always
 * running whenever no other command needs the subsystem.
 */
public class CommandDrivetrain extends CommandBase {

    private final MecanumDrivetrain drivetrain;
    private final DoubleSupplier forward;
    private final DoubleSupplier strafe;
    private final DoubleSupplier turn;

    public CommandDrivetrain(MecanumDrivetrain drivetrain, DoubleSupplier forward,
                             DoubleSupplier strafe, DoubleSupplier turn) {
        this.drivetrain = drivetrain;
        this.forward = forward;
        this.strafe = strafe;
        this.turn = turn;
        addRequirements(this.drivetrain);
    }


    @Override
    public void execute() {
        drivetrain.driveFieldCentric(forward.getAsDouble(), strafe.getAsDouble(), turn.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return false; // default command: runs until interrupted by another command
    }
}