package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Down Side Shoot Flower Shoot", group = "Comp")
@Disabled
public class DownSideShootFlowerShoot extends AutoBase {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(60.0256, 133.3588, 90);
    private final Pose path1 = poseFactory.of(48, 120, 90);
    private final Pose path1Control1 = poseFactory.of(60.3006, 113.2799, 0);
    private final Pose point2 = poseFactory.of(48, 132, 90);
    private final Pose point3 = poseFactory.of(48, 120, 90);
    private final Pose path1_2 = poseFactory.of(60, 133, 90);
    private final Pose path1_2Control1 = poseFactory.of(60.3006, 113.2799, 0);
    private final Pose point5 = poseFactory.of(13.769, 121.7228, 90);
    private final Pose point5Control1 = poseFactory.of(62.8297, 120.8674, 0);

    public Path path1() {
        return Paths.curve(start, path1Control1, path1).constant(path1);
    }

    public Path path2() {
        return Paths.line(path1, point2).constant(point2);
    }

    public Path path3() {
        return Paths.line(point2, point3).reverseTangent();
    }

    public Path path1_2() {
        return Paths.curve(point3, path1_2Control1, path1_2).constant(path1_2);
    }

    public Path path5() {
        return Paths.curve(path1_2, point5Control1, point5).constant(point5);
    }

    @Override
    protected void scheduleAuto() {
        drivetrain.setPose(start);
        states.put("State1", new AutoState(
                sequential(
                        startFlywheel(),
                        pause(2000),
                        shoot(),
                        follow(path1()),
                        flowerDown(),
                        parallelRace(
                                sequential(
                                    follow(path2()),
                                        pause(2000)
                                ),
                                intake()),
                        follow(path3()),
                        flowerUp(),
                        follow(path1_2()),
                        shoot(),
                        follow(path5()),
                        stopCommand()
                ),
                () -> END_STATE
        ));
        currentState = "State1";

    }
}
