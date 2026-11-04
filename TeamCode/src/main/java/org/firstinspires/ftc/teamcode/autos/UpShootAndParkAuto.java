package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Commands.ShootCommand;
import org.firstinspires.ftc.teamcode.Commands.StartFlyWheelCommand;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

@Autonomous(name="Up Shoot and Park", group="Comp")
public class UpShootAndParkAuto extends AutoBase{
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56.203, 9.6241, 90);
    private final Pose point1 = poseFactory.of(13.8203, 94.0602, -90);
    private final Pose point1Control1 = poseFactory.of(4.4111, 41.7574, 0);

    public Path path1() {
        return Paths.curve(start, point1Control1, point1).constant(point1);
    }

    @Override
    protected void scheduleAuto() {
        drivetrain.setPose(start);
        schedule(
                sequencial(
                        startFlywheel(),
                        shoot(),
                        follow(path1())
                )
        );
    }
}
