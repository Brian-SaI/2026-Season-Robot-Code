package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.SwerveDrivetrain;

import java.util.function.DoubleSupplier;

/**
 * Continuously drives the SwerveSubsystem, field-centric, from three stick suppliers.
 * This is meant to be set as the SwerveSubsystem's default command, so it's always
 * running whenever no other command needs the subsystem.
 */
public class CommandSwerveDrivetrain extends CommandBase {

    private final SwerveDrivetrain swerve;
    private final DoubleSupplier forward;
    private final DoubleSupplier strafe;
    private final DoubleSupplier turn;

    // EMA (exponential moving average) smoothing for stick inputs.
    // alpha closer to 1.0 = less smoothing (more responsive, more jitter passes through)
    // alpha closer to 0.0 = more smoothing (less jitter, but more input lag)
    private static final double SMOOTHING_ALPHA = 0.3;
    private double smoothedForward = 0;
    private double smoothedStrafe = 0;
    private double smoothedTurn = 0;

    public CommandSwerveDrivetrain(SwerveDrivetrain swerve, DoubleSupplier forward,
                              DoubleSupplier strafe, DoubleSupplier turn) {
        this.swerve = swerve;
        this.forward = forward;
        this.strafe = strafe;
        this.turn = turn;
        addRequirements(swerve);
    }

    private double smooth(double previous, double target) {
        return previous + SMOOTHING_ALPHA * (target - previous);
    }

    @Override
    public void execute() {
        swerve.driveFieldCentric(forward.getAsDouble(), strafe.getAsDouble(), turn.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return false; // default command: runs until interrupted by another command
    }
}