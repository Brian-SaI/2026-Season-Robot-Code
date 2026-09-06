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
    private final DoubleSupplier forwardSupplier;
    private final DoubleSupplier strafeSupplier;
    private final DoubleSupplier turnSupplier;

    // EMA (exponential moving average) smoothing for stick inputs.
    // alpha closer to 1.0 = less smoothing (more responsive, more jitter passes through)
    // alpha closer to 0.0 = more smoothing (less jitter, but more input lag)
    private static final double SMOOTHING_ALPHA = 0.3;
    private double smoothedForward = 0;
    private double smoothedStrafe = 0;
    private double smoothedTurn = 0;

    public CommandSwerveDrivetrain(SwerveDrivetrain swerve, DoubleSupplier forwardSupplier,
                              DoubleSupplier strafeSupplier, DoubleSupplier turnSupplier) {
        this.swerve = swerve;
        this.forwardSupplier = forwardSupplier;
        this.strafeSupplier = strafeSupplier;
        this.turnSupplier = turnSupplier;
        addRequirements(swerve);
    }

    private double smooth(double previous, double target) {
        return previous + SMOOTHING_ALPHA * (target - previous);
    }

    @Override
    public void execute() {
        smoothedForward = smooth(smoothedForward, forwardSupplier.getAsDouble());
        smoothedStrafe = smooth(smoothedStrafe, strafeSupplier.getAsDouble());
        smoothedTurn = smooth(smoothedTurn, turnSupplier.getAsDouble());

        swerve.driveFieldCentric(smoothedForward, smoothedStrafe, smoothedTurn);
    }

    @Override
    public boolean isFinished() {
        return false; // default command: runs until interrupted by another command
    }
}