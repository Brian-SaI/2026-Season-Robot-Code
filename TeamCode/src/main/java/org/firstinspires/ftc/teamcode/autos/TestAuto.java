package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class TestAuto extends OpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose startPose = poseFactory.of(56, 8, 90);
    private final Pose path1 = poseFactory.of(10,90,90);
    private final Pose startPose = poseFactory.of()


    @Override
    public void init(){
        follower = Constants.create(hardwareMap);

    }
    @Override
    public void start(){


    }
    @Override
    public void loop(){


    }

}
