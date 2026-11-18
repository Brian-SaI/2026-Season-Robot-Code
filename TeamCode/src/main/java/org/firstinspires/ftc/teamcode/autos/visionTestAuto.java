package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Vision Test Auto", group = "Comp")
public class visionTestAuto extends AutoBase {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(8, 0, -90);
    private final Pose point1 = poseFactory.of(13.0, 12.0, -90);
    private final Pose point2 = poseFactory.of(0.0, 12.0, -90);
    private final Pose point3 = poseFactory.of(26.0, 12.0, -90);



    public Path path1() {
        return Paths.line(start, point1).constant(point1);
    }
    public Path path2() {
        return Paths.line(point1, point2).constant(point2);
    }
    public Path path3() {
        return Paths.line(point1, point3).constant(point3);
    }


    @Override
    protected void scheduleAuto() {
        drivetrain.setPose(start);
        states.put("State1", new AutoState(
                follow(path1()),
                visionStateChange("State2", "State3")
        ));
        states.put("State3",new AutoState(
                follow(path3()),
                () -> END_STATE
        ));

        states.put("State2", new AutoState(
                follow(path2()),
                () -> END_STATE));

        currentState = "State1";

    }
}