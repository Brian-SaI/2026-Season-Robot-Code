package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.paths.Path;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.Commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.pedro.Constants;

public abstract class AutoBase extends CommandOpMode {

    protected MecanumDrivetrain drivetrain;
    protected IntakeSubsystem intake;
    protected ShooterSubsystem shooter;

    @Override
    public void initialize() {
        drivetrain = new MecanumDrivetrain(Constants.create(hardwareMap));
        scheduleAuto();
    }

    protected abstract void scheduleAuto();

    protected Command follow(Path path) {
        return new FollowPathCommand(drivetrain, path);
    }

    protected Command sequencial(Command ...commands) {
        return new SequentialCommandGroup(commands);
    }

}
