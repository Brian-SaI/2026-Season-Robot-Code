package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.interpolator.Interpolator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name= "Up Shoot Garden Park", group= "Comp" )
public class UpShootGardenPark extends AutoBase {
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56.5367, 10.1466, -90);
    private final Pose point1 = poseFactory.of(9.0733, 8, 180);
    private final Pose point1Control1 = poseFactory.of(56.8862, 23.9709, 0);
    private final Pose point1Control2 = poseFactory.of(44.543, 9.481, 0);
    private final Pose point1Segment1Start = poseFactory.of(9.0733, 8, -90);
    private final Pose point1Segment1End = poseFactory.of(9.0733, 8, 180);
    private final Pose point1Segment2Heading = poseFactory.of(9.0733, 8, 180);
    private final Pose point2 = poseFactory.of(10.154, 91.7176, 180);
    private final Pose point2Control1 = poseFactory.of(30.9869, 34.6937, 0);

    public Path path1() {
        return Paths.curve(start, point1Control1, point1Control2, point1).heading(Interpolator.piecewise().until(0.5018, Interpolator.linear(point1Segment1Start, point1Segment1End)).until(1, Interpolator.constant(point1Segment2Heading)));
    }

    public Path path2() {
        return Paths.curve(point1, point2Control1, point2).constant(point2);
    }

    @Override
    protected void scheduleAuto() {
        drivetrain.setPose(start);
        schedule(
                sequential(
                    startFlywheel(),
                    pause(3000),
                    shoot(),
                    parallelRace(
                            intake(),
                            sequential(
                                    follow(path1(), 1.0),
                                    pause(1000))),
                    follow(path2()),
                    stopCommand()
                )
        );
    }
}