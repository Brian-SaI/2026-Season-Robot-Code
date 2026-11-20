package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.interpolator.Interpolator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Alliance;

public class VisionUpShootGardenShootAuto extends AutoBase {
    private final PoseFactory poseFactory = PoseFactory.degrees();

    @Autonomous (
            name= "[RED] Vision Up Shoot Garden Shoot",
            group= "Comp",
            preselectTeleOp = "[RED] BioBuzz Teleop"
    )
    public static class RedVisionUSGS extends VisionUpShootGardenShootAuto {
        public RedVisionUSGS() {
            super(Alliance.RED);
        }
    }

    @Autonomous (
            name= "[BLUE] Vision Up Shoot Garden Shoot",
            group= "Comp",
            preselectTeleOp = "[BLUE] BioBuzz Teleop"
    )
    public static class BlueVisionUSGS extends VisionUpShootGardenShootAuto {
        public BlueVisionUSGS() {
            super(Alliance.BLUE);
        }
    }

    public VisionUpShootGardenShootAuto(Alliance alliance) {
        super(alliance);
    }

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
    private final Pose driveFlower = poseFactory.of(22.5064, 47.4472, 180);
    private final Pose driveFlowerControl1 = poseFactory.of(58.7139, 33.827, 0);
    private final Pose intakeflower = poseFactory.of(12.0, 47.569, 180);
    private final Pose flowerToShootStart = poseFactory.of(12.0962, 47.569, 180);
    private final Pose flowerShimmy = poseFactory.of(12.0962, 50.0, 180);
    private final Pose flowerToShoot = poseFactory.of(58.9282, 8.0445, -90);

    public Path driveFlower() {
        return Paths.curve(start, driveFlowerControl1, driveFlower).linear(start, driveFlower);
    }

    public Path intakeflower() {
        return Paths.line(driveFlower, intakeflower).constant(intakeflower);
    }

    public Path flowerToShoot() {
        return Paths.line(flowerToShootStart, flowerToShoot).linear(flowerToShootStart, flowerToShoot);
    }

    public Path shimmyLeft() {
        return Paths.line(intakeflower, flowerShimmy).constant(intakeflower);
    }

    public Path shimmyRight() {
        return Paths.line(flowerShimmy, intakeflower).constant(flowerShimmy);
    }


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
        states.put("State1", new AutoState(
                sequential(
                        startFlywheel(),
                        pause(1000),
                        shoot(),
                        parallelRace(
                                intake(),
                                sequential(
                                        follow(path1(), 1.0),
                                        pause(1000))),
                        follow(path2(), 1.0),
                        stopCommand()),
                visionStateChange("shoot", "go park")
        ));

        currentState = "State1";
        states.put("shoot", new AutoState(
                shoot(),
                ()-> "intake flower" ));

        states.put("shoot flower", new AutoState(
                shoot(),
                ()-> "go park" ));

        states.put("go park", new AutoState(
                sequential(
                        follow(path3()),
                        stopCommand()
                ), () -> END_STATE
        ));

        states.put("intake flower", new AutoState(
                sequential(
                        parallel(
                                sequential(
                                        pause(500),
                                        flowerDown()
                                ),
                                follow(driveFlower())
                        ),
                        parallelRace(
                                sequential(
                                        follow(intakeflower(), 0.5),
                                        follow(shimmyLeft(), 1.0),
                                        follow(shimmyRight(), 1.0),
                                        pause(1000)
                                ),
                                intake()
                        ),
                        parallel(
                                follow(flowerToShoot(), 3.0),
                                sequential(
                                        parallelRace(
                                                pause(500),
                                                intake()
                                        ),
                                        flowerUp()
                                )
                        ),
                        stopCommand()

                ),
                visionStateChange("shoot flower", "go park")
        ));
    }

}