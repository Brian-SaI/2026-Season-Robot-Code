package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

@TeleOp( name = "Shooter Tuning", group = "Tuning")
public class ShooterTuning extends CommandOpMode {

    ShooterSubsystem shooterSubsystem;

    @Override
    public void initialize() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        shooterSubsystem = new ShooterSubsystem(hardwareMap, telemetry);
        shooterSubsystem.setDefaultCommand(shooterSubsystem.getDefaultCommand());
    }

    @Override
    public void run() {
        super.run(); // runs the CommandScheduler
        telemetry.update();
    }
}
