package org.firstinspires.ftc.teamcode.ArchivedOpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Commands.CommandSwerveDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.SwerveDrivetrain;

@TeleOp(name = "2026 Field Centric Swerve (SolversLib)", group = "Offseason")
public class SwerveTestContainer extends CommandOpMode {

    // Subsystems
    private SwerveDrivetrain swerve;

    // Commands
    private CommandSwerveDrivetrain driveCommand;

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

        // Commands
        driveCommand = new CommandSwerveDrivetrain(
                swerve,
                () -> driverController.getLeftY(),
                () -> -driverController.getLeftX(),
                () -> -driverController.getRightX()
        );

        // Swerve Drive
        swerve.setDefaultCommand(driveCommand);
        swerve.startTeleopDrive(true);
    }

    @Override
    public void run() {
        super.run(); // runs the CommandScheduler
        telemetry.update();
    }
}