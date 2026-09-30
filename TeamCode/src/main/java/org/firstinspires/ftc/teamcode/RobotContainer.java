package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Commands.CommandSwerveDrivetrain;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.OuttakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ShootCommand;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.SwerveDrivetrain;

@TeleOp(name = "2026-Season-Robot-Code (SolversLib)", group = "Final")
public class RobotContainer extends CommandOpMode {

    // Subsystems
    private SwerveDrivetrain swerve;
    private IntakeSubsystem intakeSubsystem;
    private ShooterSubsystem shooterSubsystem;

    // Commands
    private CommandSwerveDrivetrain driveCommand;
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
        swerve = new SwerveDrivetrain(hardwareMap, telemetry);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem(hardwareMap);

        // Commands
        driveCommand = new CommandSwerveDrivetrain(
                swerve,
                () -> driverController.getLeftY(),
                () -> driverController.getLeftX(),
                () -> -driverController.getRightX()
        );

        intakeCommand = new IntakeCommand(intakeSubsystem);
        outtakeCommand = new OuttakeCommand(intakeSubsystem);
        shootCommand = new ShootCommand(shooterSubsystem);


        // Swerve Drive
        swerve.setDefaultCommand(driveCommand);
        //swerve.driveFieldCentric(-driverController.getLeftY(),-driverController.getLeftX(),-driverController.getRightX());

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