package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Commands.CommandDrivetrain;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.OuttakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ShootCommand;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp(name = "2026-Season-Robot-Code (SolversLib)", group = "Final")
public class RobotContainer extends CommandOpMode {

    // Subsystems
    private Follower follower;

    private MecanumDrivetrain drivetrain;
    private IntakeSubsystem intakeSubsystem;
    private ShooterSubsystem shooterSubsystem;

    // Commands
    private CommandDrivetrain driveCommand;
    private IntakeCommand intakeCommand;
    private OuttakeCommand outtakeCommand;

    private ShootCommand shootCommand;

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
                () -> -driverController.getLeftY(),
                () -> -driverController.getLeftX(),
                () -> -driverController.getRightX());

        drivetrain.setDefaultCommand(driveCommand);
        intakeCommand = new IntakeCommand(intakeSubsystem);
        outtakeCommand = new OuttakeCommand(intakeSubsystem);
        shootCommand = new ShootCommand(shooterSubsystem);

        // Intake Commands
        manipulatorController.getGamepadButton(GamepadKeys.Button.Y).whileHeld(intakeCommand);
        manipulatorController.getGamepadButton(GamepadKeys.Button.B).whileHeld(outtakeCommand);
        manipulatorController.getGamepadButton(GamepadKeys.Button.X).whileHeld(shootCommand);
    }

    @Override
    public void run() {
        super.run(); // runs the CommandScheduler
        telemetry.update();
    }
}