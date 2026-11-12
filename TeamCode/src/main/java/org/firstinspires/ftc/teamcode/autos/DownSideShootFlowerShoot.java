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

            private final Pose start = poseFactory.of(59.7873, 133.2145, 90);
            private final Pose path1 = poseFactory.of(59.8572, 105.4304, 90);
            private final Pose point2 = poseFactory.of(47.4656, 135.5882, 112.3375);
            private final Pose point3 = poseFactory.of(59.3228, 116.1822, 121.4253);
            private final Pose point4 = poseFactory.of(59.7059, 105.693, 92.0915);
            private final Pose point5 = poseFactory.of(7.1714, 115.4154, -10.4849);

            public Path path1() {
                return Paths.line(start, path1).linear(start, path1);
            }

            public Path path2() {
                return Paths.line(path1, point2).tangent();
            }

            public Path path3() {
                return Paths.line(point2, point3).reverseTangent();
            }

            public Path path4() {
                return Paths.line(point3, point4).reverseTangent();
            }

            public Path path5() {
                return Paths.line(point4, point5).reverseTangent();
            }

            @Override
            protected void scheduleAuto() {
                drivetrain.setPose(start);
                schedule(
                follow(path1()),
                follow(path2()),
                follow(path3()),
                follow(path4()),
                follow(path5())

                    );
    }
}
