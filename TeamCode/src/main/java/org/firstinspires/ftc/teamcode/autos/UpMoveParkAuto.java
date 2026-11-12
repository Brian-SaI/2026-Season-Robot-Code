package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Up Move Park", group = "Comp")
public class UpMoveParkAuto extends AutoBase{

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56, 8, -90);
    private final Pose path1 = poseFactory.of(6.773, 101.1209, 180);

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }

    @Override
    protected void scheduleAuto(){
    drivetrain.setPose(start);
        schedule(
                sequential(
                follow(path1())
                )
        );
    }
}
