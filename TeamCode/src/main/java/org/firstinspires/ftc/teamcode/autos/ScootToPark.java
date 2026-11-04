package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Scoot To Park", group = "Comp")
public class ScootToPark extends AutoBase {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(8.2612, 72.5925, 90);
    private final Pose point1 = poseFactory.of(8.8114, 91.3769, -90);

    public Path path1() {
        return Paths.line(start, point1).constant(point1);
    }


    @Override
    protected void scheduleAuto() {
        drivetrain.setPose(start);
        schedule(
                follow(path1())
        );
    }
}