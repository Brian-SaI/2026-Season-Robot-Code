package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Shoot and Park", group="Comp")
public class ShootAndParkAuto extends AutoBase{
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56, 8, 90);
    private final Pose path1 = poseFactory.of(56, 36, 90);
    private final Pose point2 = poseFactory.of(5.5039, 102.3838, -90.7874);
    private final Pose point2Control1 = poseFactory.of(4.4111, 41.7574, 0);

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }

    public Path path2() {
        return Paths.curve(path1, point2Control1, point2).reverseTangent();
    }


    @Override
    protected void scheduleAuto() {
        schedule(
                sequencial(
                        follow(path1()),
                        follow(path2())
                )
        );
    }
}
