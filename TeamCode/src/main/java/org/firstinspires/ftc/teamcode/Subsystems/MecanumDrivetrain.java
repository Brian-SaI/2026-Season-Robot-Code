package org.firstinspires.ftc.teamcode.Subsystems;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class MecanumDrivetrain extends SubsystemBase {

    private final Follower follower;

    public MecanumDrivetrain(Follower follower) {
        this.follower = follower;
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

}