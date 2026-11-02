package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ShootCommand;
import org.firstinspires.ftc.teamcode.Subsystems.IndexerSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.VisionSubsystem;

@TeleOp( name = "Shooter Tuning", group = "Tuning")
public class ShooterTuning extends CommandOpMode {

    ShooterSubsystem shooterSubsystem;
    VisionSubsystem visionSubsystem;

    IndexerSubsystem indexerSubsystem;

    IntakeSubsystem intakeSubsystem;
    private GamepadEx manipulatorController;


    @Override
    public void initialize() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        indexerSubsystem = new IndexerSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem(hardwareMap, telemetry, null);
        shooterSubsystem.setDefaultCommand(shooterSubsystem.defaultCommand());

        manipulatorController = new GamepadEx(gamepad2);
        manipulatorController.getGamepadButton(GamepadKeys.Button.CROSS).whileHeld(
                        new ShootCommand(shooterSubsystem, indexerSubsystem)
                );
        telemetry.update();
    }

    @Override
    public void run() {
        super.run(); // runs the CommandScheduler
        telemetry.update();

    }
}
