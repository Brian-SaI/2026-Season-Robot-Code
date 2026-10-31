package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

public class DownSideWaitShootAndPark extends AutoBase{


        private final PoseFactory poseFactory = PoseFactory.degrees();

        private final Pose start = poseFactory.of(58.4282, 138.6833, 90);
        private final Pose path1 = poseFactory.of(58.5692, 109.665, 90);
        private final Pose point2 = poseFactory.of(8.6069, 105.3917, 4.8887);

        public Path path1() {
            return Paths.line(start, path1).linear(start, path1);
        }

        public Path path2() {
        return Paths.line(path1, point2).reverseTangent();
        }

    @Override
    protected void scheduleAuto() {
            schedule(
                    startFlywheel(),
                    follow(path1()),
                    shoot()
            );
        
    }
}
