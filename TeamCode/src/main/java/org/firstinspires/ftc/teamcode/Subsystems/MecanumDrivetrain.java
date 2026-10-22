package org.firstinspires.ftc.teamcode.Subsystems;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class MecanumDrivetrain extends SubsystemBase {

    private final Follower follower;

    public MecanumDrivetrain(Follower follower) {
        this.follower = follower;
    }

    public void setPose(Pose pose) {
        follower.setPose(pose);
    }

    public void driveFieldCentric(double forward, double strafe, double turn) {
        DrivePowers powers = ManualDrive.fieldCentric(
                forward,
                strafe,
                turn,
                -follower.pose().heading()
        );
        follower.manual(powers);
        follower.update();
    }

    public void stop(){
      follower.stop();
      follower.update();
    }

    public void startFollowing(Path path) {
        follower.follow(path);
    }

    public boolean followCurrentPath() {
        follower.update();
        return follower.isBusy();
    }

}