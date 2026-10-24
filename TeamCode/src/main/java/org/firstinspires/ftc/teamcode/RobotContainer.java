package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Commands.CommandDrivetrain;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.KickerCommand;
import org.firstinspires.ftc.teamcode.Commands.OuttakeCommand;
import org.firstinspires.ftc.teamcode.Commands.StartFlyWheelCommand;
import org.firstinspires.ftc.teamcode.Commands.StopFlyWheelCommand;
import org.firstinspires.ftc.teamcode.Subsystems.IndexerSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp(name = "2026-Season-Robot-Code (SolversLib)", group = "Final")
public class RobotContainer extends CommandOpMode {

    // Subsystems
    private Follower follower;
    private IndexerSubsystem indexerSubsystem;

    private MecanumDrivetrain drivetrain;
    private IntakeSubsystem intakeSubsystem;
    private ShooterSubsystem shooterSubsystem;

    // Commands
    private CommandDrivetrain driveCommand;
    private IntakeCommand intakeCommand;

    private KickerCommand kickerCommand;
    private OuttakeCommand outtakeCommand;

    private StartFlyWheelCommand startFlyWheelCommand;
    private StopFlyWheelCommand stopFlyWheelCommand;

    // Controllers
    private GamepadEx driverController;
    private GamepadEx manipulatorController;

    @Override
    public void initialize() {
        // Driver
        driverController = new GamepadEx(gamepad1);
        // Manipulator
        manipulatorController = new GamepadEx(gamepad2);

        // Subsystems
        follower = Constants.create(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem(hardwareMap);
        drivetrain = new MecanumDrivetrain(follower);

        // Commands
        driveCommand = new CommandDrivetrain(drivetrain,
                () -> -driverController.getLeftY()*driveSpeed(),
                () -> -driverController.getLeftX()*driveSpeed(),
                () -> -driverController.getRightX()*driveSpeed());


        drivetrain.setDefaultCommand(driveCommand);
        intakeCommand = new IntakeCommand(intakeSubsystem, indexerSubsystem);
        outtakeCommand = new OuttakeCommand(intakeSubsystem, indexerSubsystem);
        startFlyWheelCommand = new StartFlyWheelCommand(shooterSubsystem);

        // Intake Commands
        manipulatorController.getGamepadButton(GamepadKeys.Button.B).whileHeld(intakeCommand);
        manipulatorController.getGamepadButton(GamepadKeys.Button.Y).whileHeld(outtakeCommand);

        manipulatorController.getGamepadButton(GamepadKeys.Button.X).whileHeld(kickerCommand);

        new Trigger(() -> manipulatorController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5)
                .whenActive(startFlyWheelCommand)
                .whenInactive(stopFlyWheelCommand);
    }
    private double driveSpeed(){
        if (manipulatorController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) >=0.5){
            return 0.35;
        }

        if (manipulatorController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) >= 0.5){
            return 0.75;

        }
        return 0.5;
    }

    @Override
    public void run() {
        super.run(); // runs the CommandScheduler
        telemetry.update();
    }
}