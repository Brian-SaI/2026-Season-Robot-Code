package org.firstinspires.ftc.teamcode.Commands;

import com.acmerobotics.dashboard.config.Config;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.controller.PIDController;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.VisionSubsystem;

@Config
public class PointToAprilTagCommand extends CommandBase {

    public static double AIM_P = 0.0;
    public static double AIM_D = 0.0;

    private final MecanumDrivetrain mecanumDrivetrain;
    private final VisionSubsystem visionSubsystem;

    PIDController autoAimController;

    public PointToAprilTagCommand(VisionSubsystem visionSubsystem, MecanumDrivetrain mecanumDrivetrain) {
        this.mecanumDrivetrain=mecanumDrivetrain;
        this.visionSubsystem=visionSubsystem;
        autoAimController=new PIDController(AIM_P, 0.0, AIM_D);
        addRequirements(mecanumDrivetrain);

    }

    @Override
    public void initialize() {
        autoAimController.setPID(AIM_P, 0.0, AIM_D);
        visionSubsystem.enableProcessing();
    }

    @Override
    public void execute()  {
        Double targetBearing = visionSubsystem.getTargetBearing();
        if (targetBearing != null) {
            double turnSpeed = autoAimController.calculate(targetBearing,0);
            mecanumDrivetrain.driveFieldCentric(0, 0, turnSpeed);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interrupted) {
        visionSubsystem.disableProcessing();
    }

}
