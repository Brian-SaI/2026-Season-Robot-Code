package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.VisionSubsystem;

public class PointToAprilTagCommand extends CommandBase {
    private MecanumDrivetrain mecanumDrivetrain;
    private VisionSubsystem visionSubsystem;

    public PointToAprilTagCommand(VisionSubsystem visionSubsystem, MecanumDrivetrain mecanumDrivetrain) {
        this.mecanumDrivetrain=mecanumDrivetrain;
        this.visionSubsystem=visionSubsystem;

        addRequirements(mecanumDrivetrain);

    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute()  {
        Double targetBearing = visionSubsystem.getTargetBearing();
        if (targetBearing != null) {
            mecanumDrivetrain.driveFieldCentric(0, 0, 0 );
        }
    }

    @Override
    public boolean isFinished() {return false;}


    @Override
    public void end(boolean interrupted) {

    }

}
