package org.firstinspires.ftc.teamcode.autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.ParallelRaceGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.Commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ShootCommand;
import org.firstinspires.ftc.teamcode.Commands.StartFlyWheelCommand;
import org.firstinspires.ftc.teamcode.Commands.TimerCommand;
import org.firstinspires.ftc.teamcode.Subsystems.IndexerSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.VisionSubsystem;
import org.firstinspires.ftc.teamcode.pedro.Constants;

public abstract class AutoBase extends CommandOpMode {

    protected MecanumDrivetrain drivetrain;
    protected IntakeSubsystem intake;

    protected IndexerSubsystem indexer;
    protected ShooterSubsystem shooter;

    protected VisionSubsystem vision;
    @Override
    public void initialize() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        drivetrain = new MecanumDrivetrain(Constants.create(hardwareMap));
        intake = new IntakeSubsystem(hardwareMap);
        indexer = new IndexerSubsystem(hardwareMap);

        shooter = new ShooterSubsystem(hardwareMap,telemetry, null);
    }

    @Override
    public void preRun() {
        scheduleAuto();
    }

    @Override
    public void run() {
        super.run();
        telemetry.update();
    }

    protected abstract void scheduleAuto();

    protected Command follow(Path path) {
        return new FollowPathCommand(drivetrain, path);
    }

    protected Command startFlywheel(){return new StartFlyWheelCommand(shooter);}

    protected Command shoot(){
        return  parallelRace(
                new ShootCommand(shooter, indexer),
                new TimerCommand(2000));
    }

    protected Command intake(){return  new IntakeCommand(intake, indexer);
    }
    protected Command sequencial(Command ...commands) {
        return new SequentialCommandGroup(commands);
    }

    protected Command parallelRace  (Command ...commands) {
        return new ParallelRaceGroup(commands);
    }

}
