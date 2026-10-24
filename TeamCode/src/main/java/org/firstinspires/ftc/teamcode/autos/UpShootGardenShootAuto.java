package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name= "Up Shoot Garden Shoot", group= "Comp" )
public class UpShootGardenShootAuto extends AutoBase{
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56, 8, 90);
    private final Pose path1 = poseFactory.of(56, 36, 90);
    private final Pose point2 = poseFactory.of(8, 8, 30.2564);
    private final Pose point3 = poseFactory.of(59.2711, 129.4002, 6.6248);
    private final Pose point3Control1 = poseFactory.of(24.7598, 125.975, 0);
    private final Pose point4 = poseFactory.of(58.9204, 103.4524, 89.2258);

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }

    public Path path2() {
        return Paths.line(path1, point2).reverseTangent();
    }

    public Path path3() {
        return Paths.curve(point2, point3Control1, point3).tangent();
    }

    public Path path4() {
        return Paths.line(point3, point4).reverseTangent();
    }

    @Override
    protected void scheduleAuto() {
        schedule(
                sequencial(
                        follow(path1()),
                        follow(path2()),
                        follow(path3()),
                        follow(path4())
                )
        );
    }
}
