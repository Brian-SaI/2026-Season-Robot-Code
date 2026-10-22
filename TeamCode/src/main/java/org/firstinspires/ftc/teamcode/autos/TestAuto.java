package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.Commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class TestAuto extends CommandOpMode {

    private MecanumDrivetrain drivetrain;

    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose start = poseFactory.of(56, 8, 90);
    private final Pose path1 = poseFactory.of(10, 90, 91);
    private final Pose path1Control1 = poseFactory.of(56, 35, 0);
    private final Pose path1Control2 = poseFactory.of(10, 64, 0);

    private Command follow(Path path) {
        return new FollowPathCommand(drivetrain, path);
    }

    private Path path1() {
        return Paths.curve(start, path1Control1, path1Control2, path1).tangent();
    }

    @Override
    public void initialize() {
        drivetrain = new MecanumDrivetrain(Constants.create(hardwareMap));
        schedule(
                follow(path1())
        );
    }

}
