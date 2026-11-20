package org.firstinspires.ftc.teamcode.autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.ParallelRaceGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.Alliance;
import org.firstinspires.ftc.teamcode.Commands.FlowerDownCommand;
import org.firstinspires.ftc.teamcode.Commands.FlowerUpCommand;
import org.firstinspires.ftc.teamcode.Commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ShootCommand;
import org.firstinspires.ftc.teamcode.Commands.StartFlyWheelCommand;
import org.firstinspires.ftc.teamcode.Commands.StopFlyWheelCommand;
import org.firstinspires.ftc.teamcode.Commands.TimerCommand;
import org.firstinspires.ftc.teamcode.Commands.WaitForTargetCommand;
import org.firstinspires.ftc.teamcode.Subsystems.FlowerSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IndexerSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.VisionSubsystem;
import org.firstinspires.ftc.teamcode.pedro.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public abstract class AutoBase extends CommandOpMode {

    protected static final String END_STATE = "END_STATE";
    protected AutoState endState;

    protected MecanumDrivetrain drivetrain;
    protected IntakeSubsystem intake;

    protected IndexerSubsystem indexer;
    protected ShooterSubsystem shooter;
    protected FlowerSubsystem flower;

    protected VisionSubsystem vision;

    public static class AutoState {
        public Command behavior;
        public Supplier<String> nextState;
        public AutoState(Command behavior, Supplier<String> nextState) {
            this.behavior = behavior;
            this.nextState = nextState;
        }
    }

    Map<String, AutoState> states;
    String currentState;
    final Alliance alliance;

    protected AutoBase() {
        this(Alliance.RED);
    }

    protected AutoBase(Alliance alliance) {
        this.alliance = alliance;
    }

    @Override
    public void initialize() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        drivetrain = new MecanumDrivetrain(Constants.create(hardwareMap));
        drivetrain.calibratePinpoint();
        intake = new IntakeSubsystem(hardwareMap);
        indexer = new IndexerSubsystem(hardwareMap, telemetry);
        vision = new VisionSubsystem(hardwareMap, telemetry, alliance, this);
        shooter = new ShooterSubsystem(hardwareMap,telemetry, vision);
        flower = new FlowerSubsystem(hardwareMap);
        states = new HashMap<>();
        states.put(END_STATE, new AutoState(
                sequential(
                    stopFlywheel(),
                        drivetrain.stopCommand()
                ),
                () -> END_STATE));
    }

    @Override
    public void preRun() {
        scheduleAuto();
        schedule(
                Objects.requireNonNull(states.get(currentState)).behavior
        );
    }

    @Override
    public void run() {
        if (!CommandScheduler.getInstance().isScheduled(states.get(currentState).behavior)) {
            currentState = states.get(currentState).nextState.get();
            schedule(states.get(currentState).behavior);
        }
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
        return new FollowPathCommand(drivetrain, path, telemetry);
    }
    protected Command follow(Path path, double tolarance) {
        return new FollowPathCommand(drivetrain, path, tolarance, telemetry);
    }

    protected Command stopCommand() {
        return drivetrain.stopCommand();
    }

    protected Command startFlywheel(){return new StartFlyWheelCommand(shooter);}


    protected Command stopFlywheel(){return new StopFlyWheelCommand(shooter);}

    protected Command shoot(){
        return  parallelRace(
                new ShootCommand(shooter, indexer, telemetry),
                new TimerCommand(3000));
    }

    protected Command pause(long timeInMs){
            return new TimerCommand(timeInMs);
    }

    protected Command waitForTarget(){
        return new WaitForTargetCommand(vision);
    }
    protected Command intake(){
        return  new IntakeCommand(intake, indexer);
    }
    protected  Command flowerUp(){
        return parallel(
               new  FlowerUpCommand(flower),
                new TimerCommand(1000)
        );

    }
    protected  Command flowerDown(){
        return parallel(
                new FlowerDownCommand(flower),
                new TimerCommand(1000)
        );
    }
    protected Command sequential(Command ...commands) {
        return new SequentialCommandGroup(commands);
    }

    protected Command parallelRace(Command ...commands) {
        return new ParallelRaceGroup(commands);
    }

    protected Command parallel(Command ...commands) {
        return new ParallelCommandGroup(commands);
    }

    protected Supplier<String> visionStateChange(String stateWithTarget, String stateWithoutTarget) {
        return () -> vision.isTargetVisible() ?stateWithTarget  :stateWithoutTarget;
    }

}
