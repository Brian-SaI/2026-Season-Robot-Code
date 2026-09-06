package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Commands.CommandSwerveDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.SwerveDrivetrain;

@TeleOp(name = "2026 Field Centric Swerve (SolversLib)", group = "Final")
public class RobotContainer extends CommandOpMode {

    // 1. Declare Subsystems (do not instantiate yet)
    private SwerveDrivetrain swerve;

    // 2. Declare Commands
    private CommandSwerveDrivetrain driveCommand;

    // 3. Declare Controllers
    private GamepadEx driverController;

    @Override
    public void initialize() {
        // Initialize Gamepads
        driverController = new GamepadEx(gamepad1);

        // 4. Initialize Subsystems (pass hardwareMap/telemetry here)
        swerve = new SwerveDrivetrain(hardwareMap, telemetry);

        // 5. Initialize Commands (safe now that subsystems are no longer null)
        driveCommand = new CommandSwerveDrivetrain(
                swerve,
                () -> driverController.getLeftY(),
                () -> -driverController.getLeftX(),
                () -> -driverController.getRightX()
        );

        // 6. Assign default command / button bindings
        swerve.setDefaultCommand(driveCommand);

        swerve.startTeleopDrive(true);
    }

    @Override
    public void run() {
        super.run(); // runs the CommandScheduler, which drives driveCommand's execute()
        telemetry.update();
    }
}