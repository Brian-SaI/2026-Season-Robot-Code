package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.interpolator.Interpolator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name= "Up Shoot Garden Shoot", group= "Comp" )
public class UpShootGardenShootAuto extends AutoBase {
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56.5367, 10.1466, -90);
    private final Pose point1 = poseFactory.of(9.0733, 8, 180);
    private final Pose point1Control1 = poseFactory.of(56.8862, 23.9709, 0);
    private final Pose point1Control2 = poseFactory.of(44.543, 9.481, 0);
    private final Pose point1Segment1Start = poseFactory.of(9.0733, 8, -90);
    private final Pose point1Segment1End = poseFactory.of(9.0733, 8, 180);
    private final Pose point1Segment2Heading = poseFactory.of(9.0733, 8, 180);
    private final Pose point2Start = poseFactory.of(9.0733, 8, 180);
    private final Pose point2 = poseFactory.of(58.5881, 8.9311, -90);
    private final Pose point2Control1 = poseFactory.of(12.1643, 18.7832, 0);
    private final Pose point2Control2 = poseFactory.of(27.9064, 27.1909, 0);
    private final Pose point2Control3 = poseFactory.of(59.9273, 29.8742, 0);
    private final Pose point3 = poseFactory.of(13.7644, 94.3559, -90);
    private final Pose point3Control1 = poseFactory.of(16.1287, 52.3292, 0);

    public Path path1() {
        return Paths.curve(start, point1Control1, point1Control2, point1).heading(Interpolator.piecewise().until(0.5018, Interpolator.linear(point1Segment1Start, point1Segment1End)).until(1, Interpolator.constant(point1Segment2Heading)));
    }

    public Path path2() {
        return Paths.curve(point2Start, point2Control1, point2Control2, point2Control3, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.curve(point2, point3Control1, point3).constant(point3);
    }

    @Override
    protected void scheduleAuto() {
        drivetrain.setPose(start);
        schedule(
            sequential(
                startFlywheel(),
                pause(2000),
                shoot(),
                parallelRace(
                        intake(),
                        follow(path1(), 2.0)),
                follow(path2()),
                shoot(),
                follow(path3()),
                stopCommand()
            )
        );
    }
}