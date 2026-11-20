package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Down Side Wait Shoot And Park", group = "Comp")
@Disabled
public class DownSideWaitShootAndPark extends AutoBase{
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
                sequential(
                        pause(16000),
                        startFlywheel(),
                        pause(4000),
                        shoot(),
                        follow(path1()),
                        stopCommand()
                ),
                () -> END_STATE
        ));
        currentState = "State1";
    }
}
