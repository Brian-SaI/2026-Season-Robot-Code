package org.firstinspires.ftc.teamcode.Commands;

import com.acmerobotics.dashboard.config.Config;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.controller.PIDController;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.VisionSubsystem;

public class PointToAprilTagCommand extends CommandBase {

    @Config
    public static class AimParams {
        public static double AIM_P = 0.025;
        public static double AIM_D = 0.002;

        public static boolean RESET_AIM_PID = false;
    }

    private final MecanumDrivetrain mecanumDrivetrain;
    private final VisionSubsystem visionSubsystem;

    PIDController autoAimController;

    public PointToAprilTagCommand(VisionSubsystem visionSubsystem, MecanumDrivetrain mecanumDrivetrain) {
        this.mecanumDrivetrain = mecanumDrivetrain;
        this.visionSubsystem = visionSubsystem;
        autoAimController = new PIDController(AimParams.AIM_P, 0.0, AimParams.AIM_D);
        addRequirements(mecanumDrivetrain);
    }

    @Override
    public void initialize() {
        autoAimController.setPID(AimParams.AIM_P, 0.0, AimParams.AIM_D);
        //visionSubsystem.enableProcessing();
    }

    @Override
    public void execute() {
        Double targetBearing = visionSubsystem.getTargetBearing();
        if (targetBearing != null) {
            double turnSpeed = autoAimController.calculate(targetBearing, 0);
            mecanumDrivetrain.driveFieldCentric(0, 0, -turnSpeed);
        } else {
            mecanumDrivetrain.driveFieldCentric(0, 0, 0.0);
        }
        if (AimParams.RESET_AIM_PID) {
            AimParams.RESET_AIM_PID = false;
            autoAimController.setPID(AimParams.AIM_P, 0.0, AimParams.AIM_D);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        //visionSubsystem.disableProcessing();
    }
}
