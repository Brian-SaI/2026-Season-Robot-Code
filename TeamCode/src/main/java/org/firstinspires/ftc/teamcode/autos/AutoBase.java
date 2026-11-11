package org.firstinspires.ftc.teamcode.autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.ParallelRaceGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.Alliance;
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
        drivetrain.calibratePinpoint();
        intake = new IntakeSubsystem(hardwareMap);
        indexer = new IndexerSubsystem(hardwareMap);
        vision = new VisionSubsystem(hardwareMap, telemetry, Alliance.RED, this);
        shooter = new ShooterSubsystem(hardwareMap,telemetry, vision);
    }

    @Override
    public void preRun() {
        scheduleAuto();
    }

    @Override
    public void run() {
        super.run();
        drawRobot(drivetrain.getPose());
        telemetry.addData("Pose", drivetrain.getPose());
        telemetry.update();
    }
    @Override
    public void end() {
        blackboard.put("pose", drivetrain.getPose());
    }

    public void drawRobot(Pose pose) {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        TelemetryPacket packet = new TelemetryPacket();
        Canvas canvas = packet.fieldOverlay();

        double xDashboard = pose.y() - 72;
        double yDashboard = 72 - pose.x();
        double headingDashboard = pose.heading() - (Math.PI / 2.0);

        // 1. Set the color and draw the robot's main chassis (circle)
        canvas.setStroke("blue");
        canvas.strokeCircle(xDashboard, yDashboard, 9.0);

        // 2. Draw a line pointing in the direction the robot is facing (Heading)
        double headX = xDashboard + 9.0 * Math.cos(headingDashboard);
        double headY = yDashboard + 9.0 * Math.sin(headingDashboard);

        canvas.setStroke("red");
        canvas.strokeLine(xDashboard, yDashboard, headX, headY);

        // 3. Send the packet to the dashboard
        dashboard.sendTelemetryPacket(packet);
    }

    protected abstract void scheduleAuto();

    protected Command follow(Path path) {
        return new FollowPathCommand(drivetrain, path);
    }
    protected Command follow(Path path, double tolarance) {
        return new FollowPathCommand(drivetrain, path, tolarance);
    }

    protected Command stopCommand() {
        return drivetrain.stopCommand();
    }

    protected Command startFlywheel(){return new StartFlyWheelCommand(shooter);}

    protected Command shoot(){
        return  parallelRace(
                new ShootCommand(shooter, indexer),
                new TimerCommand(3000));
    }

    protected Command pause(long timeInMs){
            return new TimerCommand(timeInMs);
    }
    protected Command intake(){return  new IntakeCommand(intake, indexer);
    }
    protected Command sequential(Command ...commands) {
        return new SequentialCommandGroup(commands);
    }

    protected Command parallelRace  (Command ...commands) {
        return new ParallelRaceGroup(commands);
    }

}
