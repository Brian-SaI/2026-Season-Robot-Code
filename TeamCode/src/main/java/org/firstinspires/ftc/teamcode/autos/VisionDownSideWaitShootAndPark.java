package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Alliance;

public class VisionDownSideWaitShootAndPark extends AutoBase{

    @Autonomous(
            name = "[RED] Vision Down Side Wait Shoot And Park",
            group = "Comp",
            preselectTeleOp = "[RED] BioBuzz Teleop"
    )
    public static class RedVisionDSWPSP extends VisionDownSideWaitShootAndPark {
        public RedVisionDSWPSP() {
            super(Alliance.RED);
        }
    }

    @Autonomous(
            name = "[BLUE] Vision Down Side Wait Shoot And Park",
            group = "Comp",
            preselectTeleOp = "[BLUE] BioBuzz Teleop"
    )
    public static class BlueVisionDSWPSP extends VisionDownSideWaitShootAndPark {
        public BlueVisionDSWPSP() {
            super(Alliance.BLUE);
        }
    }

    public VisionDownSideWaitShootAndPark(Alliance alliance) {
        super(alliance);
    }

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(60.0382, 133.3167, 90);
    private final Pose point1 = poseFactory.of(13.0791, 120.4182, 90);
    private final Pose point1Control1 = poseFactory.of(62.879, 95.8837, 0);

    public Path path1() {
        return Paths.curve(start, point1Control1, point1).constant(point1);
        }

    @Override
    protected void scheduleAuto() {
        drivetrain.setPose(start);
        states.put("State1", new AutoState(
                parallelRace(
                    pause(20_000),
                    waitForTarget()
                ),
                visionStateChange("shoot", "park")
        ));
        currentState = "State1";
        states.put("shoot",new AutoState(
                sequential(
                        startFlywheel(),
                        pause(1000),
                        shoot()
                ),
                () -> "park"
        ));
        states.put("park",new AutoState(
                sequential(
                        follow(path1()),
                        stopCommand()
                ),
                () -> END_STATE
        ));





    }

}
