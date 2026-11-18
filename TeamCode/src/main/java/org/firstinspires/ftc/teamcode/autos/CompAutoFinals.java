package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "FinalsAuto", group = "Comp")
public class CompAutoFinals extends AutoBase {

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(31.995, 128.7831, 135);
    private final Pose point1 = poseFactory.of(13.7176, 118.6378, 90);

    public Path path1() {
        return Paths.line(start, point1).linear(start, point1);
    }


    @Override
    protected void scheduleAuto() {
        drivetrain.setPose(start);
        states.put("State1", new AutoState(
                sequential(
                        startFlywheel(),
                        pause(6000),
                        shoot(),
                        follow(path1()),
                        stopCommand()
                ),
                () -> END_STATE
        ));
        currentState = "State1";
    }
}