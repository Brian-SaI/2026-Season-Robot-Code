package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Alliance;
import org.firstinspires.ftc.teamcode.Commands.CommandDrivetrain;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ShootCommand;
import org.firstinspires.ftc.teamcode.Commands.OuttakeCommand;
import org.firstinspires.ftc.teamcode.Commands.PointToAprilTagCommand;
import org.firstinspires.ftc.teamcode.Commands.StartFlyWheelCommand;
import org.firstinspires.ftc.teamcode.Commands.StopFlyWheelCommand;
import org.firstinspires.ftc.teamcode.GobildaIndicatorLight;
import org.firstinspires.ftc.teamcode.Subsystems.IndexerSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.VisionSubsystem;
import org.firstinspires.ftc.teamcode.pedro.Constants;


public class RobotContainer extends CommandOpMode {

    @TeleOp(name = "[BLUE] BioBuzz Teleop", group = "Comp")
    public static class BlueTeleop extends RobotContainer {
        public BlueTeleop() {
            super(Alliance.BLUE);
        }
    }

    @TeleOp(name = "[RED] BioBuzz Teleop", group = "Comp")
    public static class RedTeleop extends RobotContainer {
        public RedTeleop() {
            super(Alliance.RED);
        }
    }


    private Alliance alliance;
    // Subsystems
    private Follower follower;
    private IndexerSubsystem indexerSubsystem;

    private MecanumDrivetrain drivetrain;
    private IntakeSubsystem intakeSubsystem;
    private ShooterSubsystem shooterSubsystem;

    // Commands
    private CommandDrivetrain driveCommand;
    private IntakeCommand intakeCommand;

    private ShootCommand shootCommand;
    private OuttakeCommand outtakeCommand;

    private StartFlyWheelCommand startFlyWheelCommand;
    private StopFlyWheelCommand stopFlyWheelCommand;
    private PointToAprilTagCommand pointToAprilTagCommand;
    private VisionSubsystem visionSubsystem;
    private GobildaIndicatorLight blinky;

    // Controllers
    private GamepadEx driverController;
    private GamepadEx manipulatorController;

    protected RobotContainer(Alliance alliance) {
        this.alliance = alliance;
    }

    @Override
    public void initialize() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        // Driver
        driverController = new GamepadEx(gamepad1);
        // Manipulator
        manipulatorController = new GamepadEx(gamepad2);

        // Subsystems
        follower = Constants.create(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        indexerSubsystem = new IndexerSubsystem(hardwareMap);
        visionSubsystem = new VisionSubsystem(hardwareMap, telemetry, alliance);
        shooterSubsystem = new ShooterSubsystem(hardwareMap, telemetry, visionSubsystem);
        drivetrain = new MecanumDrivetrain(follower);
        pointToAprilTagCommand= new PointToAprilTagCommand(visionSubsystem, drivetrain);
        blinky = new GobildaIndicatorLight(hardwareMap, "beacon");


        // Commands
        driveCommand = new CommandDrivetrain(drivetrain,
                () -> driverController.getLeftY()*driveSpeed(),
                () -> -driverController.getLeftX()*driveSpeed(),
                () -> -driverController.getRightX()*driveSpeed());

        driverController.getGamepadButton(GamepadKeys.Button.A).whileHeld(pointToAprilTagCommand);

        driverController.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .whenPressed(() -> drivetrain.setPose(new Pose(0, 0, 0)));

        drivetrain.setDefaultCommand(driveCommand);
        shooterSubsystem.setDefaultCommand(shooterSubsystem.defaultCommand());

        intakeCommand = new IntakeCommand(intakeSubsystem, indexerSubsystem);
        outtakeCommand = new OuttakeCommand(intakeSubsystem, indexerSubsystem);
        startFlyWheelCommand = new StartFlyWheelCommand(shooterSubsystem);
        stopFlyWheelCommand = new StopFlyWheelCommand(shooterSubsystem);
        shootCommand = new ShootCommand(shooterSubsystem, indexerSubsystem);

        // Intake Commands
        manipulatorController.getGamepadButton(GamepadKeys.Button.SQUARE).whenHeld(intakeCommand);
        manipulatorController.getGamepadButton(GamepadKeys.Button.TRIANGLE).whenHeld(shootCommand);
        manipulatorController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenHeld(outtakeCommand);
        new Trigger(() -> manipulatorController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5)
                .whenActive(startFlyWheelCommand)
                .whenInactive(stopFlyWheelCommand);
//        manipulatorController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
//                .whenHeld(startFlyWheelCommand)
//                .whenReleased(stopFlyWheelCommand);
        drivetrain.calibratePinpoint();
        drivetrain.setPose(new Pose(0,0,0));
    }
    private double driveSpeed(){
        if (driverController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) >=0.5){
            return 0.35;
        }

        if (driverController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) >= 0.5){
            return 0.9;

        }
        return 0.75;
    }

    @Override
    public void run() {
        super.run(); // runs the CommandScheduler
         Double bearing = visionSubsystem.getTargetBearing();
         if (bearing == null) {
            blinky.setColor(GobildaIndicatorLight.Color.OFF);
         } else if (bearing <= 5.0 && bearing >= -5.0) {
             blinky.setColor(GobildaIndicatorLight.Color.GREEN);

         } else {
             blinky.setColor(GobildaIndicatorLight.Color.RED);

         }
        telemetry.addData("Heading",drivetrain.getPose().heading());
         telemetry.addData("Pose", drivetrain.getPose());
        telemetry.update();
    }
}