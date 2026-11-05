package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.interpolator.Interpolator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name= "Up Shoot Garden Down Shoot", group= "Comp" )
public class UpShootGardenDownShoot extends AutoBase {
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56.5367, 10.1466, 90);
    private final Pose point1 = poseFactory.of(9.0733, 8, 180);
    private final Pose point1Control1 = poseFactory.of(56.8862, 23.9709, 0);
    private final Pose point1Control2 = poseFactory.of(44.543, 9.481, 0);
    private final Pose point1Segment1Start = poseFactory.of(9.0733, 8, -90);
    private final Pose point1Segment1End = poseFactory.of(9.0733, 8, 180);
    private final Pose point1Segment2Heading = poseFactory.of(9.0733, 8, 180);
    private final Pose point2Start = poseFactory.of(9.0733, 8, 180);
    private final Pose point2 = poseFactory.of(30.3239, 130.2168, 120);
    private final Pose point2Control1 = poseFactory.of(28.622, 27.012, 0);
    private final Pose point2Control2 = poseFactory.of(15.2054, 91.4115, 0);
    private final Pose point3Start = poseFactory.of(30.3239, 130.2168, 120);
    private final Pose point3 = poseFactory.of(13.0489, 93.8193, 90);
    private final Pose point3Control1 = poseFactory.of(37.5952, 100.8077, 0);

    public Path path1() {
        return Paths.curve(start, point1Control1, point1Control2, point1).heading(Interpolator.piecewise().until(0.5018, Interpolator.linear(point1Segment1Start, point1Segment1End)).until(1, Interpolator.constant(point1Segment2Heading)));
    }

    public Path path2() {
        return Paths.curve(point2Start, point2Control1, point2Control2, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.curve(point3Start, point3Control1, point3).linear(point3Start, point3);
    }
    @Override
    protected void scheduleAuto() {
        drivetrain.setPose(start);
        schedule(
            startFlywheel(),
            shoot(),
            parallelRace(
                    intake(),
                    follow(path1())),
            follow(path2()),
            shoot(),
            follow(path3())
        );
    }
}