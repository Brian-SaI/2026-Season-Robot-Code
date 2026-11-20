package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Alliance;

//@Disabled
public class VisionDownSideShootFlowerShoot extends AutoBase {

    @Autonomous(
            name = "[BLUE] Vision Down Shoot Flower Shoot",
            group = "Comp",
            preselectTeleOp = "[BLUE] BioBuzz Teleop"
    )
    public static class BlueDSSFS extends VisionDownSideShootFlowerShoot {
        public BlueDSSFS() {
            super(Alliance.BLUE);
        }
    }

    @Autonomous(
            name = "[RED] Vision Down Shoot Flower Shoot",
            group = "Comp",
            preselectTeleOp = "[RED] BioBuzz Teleop"
    )
    public static class RedDSSFS extends VisionDownSideShootFlowerShoot {
        public RedDSSFS() {
            super(Alliance.RED);
        }
    }

    public VisionDownSideShootFlowerShoot(Alliance alliance) {
        super(alliance);
    }

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(60.0256, 133.3588, 90);
    private final Pose path1 = poseFactory.of(48, 120, 90);
    private final Pose path1Control1 = poseFactory.of(60.3006, 113.2799, 0);
    private final Pose point2 = poseFactory.of(48, 127.5, 90);
    private final Pose point2Shimmy = poseFactory.of(45, 127.5, 90);
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

    public Path shimmyOne() {
        return Paths.line(point2, point2Shimmy).constant(point2Shimmy);
    }

    public Path shimmyTwo() {
        return Paths.line(point2Shimmy, point2).constant(point2);
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
                        parallelRace(
                                pause(15_000),
                                waitForTarget()
                        ),
                visionStateChange("shoot", "park")
        ));
        currentState = "State1";
        states.put("park", new AutoState(
                sequential(
                        follow(path5()),
                        stopCommand()
                ),
                () -> END_STATE
        ));
        states.put("shoot",new AutoState(
                sequential(
                        startFlywheel(),
                        pause(1000),
                        shoot(),
                        follow(path1()),
                        flowerDown(),
                        parallelRace(
                                sequential(
                                        follow(path2(),1.0),
                                        follow(shimmyOne(),1.0),
                                        follow(shimmyTwo(),1.0),
                                        pause(1000),
                                        follow(path3())
                                ),
                                intake()),
                        flowerUp(),
                        follow(path1_2()),
                        shoot()),
                () ->"park" ));
    }
}
