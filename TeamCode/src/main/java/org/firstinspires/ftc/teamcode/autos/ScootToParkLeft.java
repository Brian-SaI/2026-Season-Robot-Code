package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Scoot To Park - Left Side", group = "Comp")
public class ScootToParkLeft extends AutoBase {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(8.2612, 136, 90);
    private final Pose point1 = poseFactory.of(8.8114, 122, 90);

    public Path path1() {
        return Paths.line(start, point1).constant(point1);
    }


    @Override
    protected void scheduleAuto() {
        drivetrain.setPose(start);
        schedule(
                sequential(
                    follow(path1())
                )
        );
    }
}