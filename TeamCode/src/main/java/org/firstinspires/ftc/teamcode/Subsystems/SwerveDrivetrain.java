package org.firstinspires.ftc.teamcode.Subsystems;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
//import com.pedropathing.
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedro.Constants;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;

/**
 * Owns the Pedro Pathing Follower and exposes swerve drive as a subsystem.
 * All Follower lifecycle calls (creation, starting teleop mode, update()) live here --
 * commands should only ever call driveFieldCentric()/driveRobotCentric() and periodic()
 * handles the rest.
 */
public class SwerveDrivetrain extends SubsystemBase {

    private final Follower follower;
    private final Telemetry telemetry;

    private final DcMotor flDrive, frDrive, blDrive, brDrive;

    public SwerveDrivetrain(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        follower = Constants.create(hardwareMap);
        follower.setPose(new Pose(0, 0, 0));
        follower.update();

        // Names must match exactly what's in Constants.java's hardware map calls.
        flDrive = hardwareMap.get(DcMotor.class, "FL_Drive");
        frDrive = hardwareMap.get(DcMotor.class, "FR_Drive");
        blDrive = hardwareMap.get(DcMotor.class, "BL_Drive");
        brDrive = hardwareMap.get(DcMotor.class, "BR_Drive");
    }

    /**
     * Call once, after the driver hits play (i.e. from your CommandOpMode's start-equivalent).
     * @param brakeMode true to hold position under zero power, false to float
     */
    public void startTeleopDrive(boolean brakeMode) {
        follower.manual();
    }

    /**
     * Drives the swerve base, field-centric.
     *
     * @param forward -1 to 1, field-forward
     * @param strafe  -1 to 1, field-left-positive (Pedro convention)
     * @param turn    -1 to 1, CCW-positive
     */
    public void driveFieldCentric(double forward, double strafe, double turn) {
//        double heading = Math.toRadians(follower.pose().heading());
//
//        double robotForward = forward * Math.cos(heading) - strafe * Math.sin(heading);
//        double robotStrafe  = forward * Math.sin(heading) + strafe * Math.cos(heading);

        DrivePowers powers = ManualDrive.fieldCentric(
                forward,
                strafe,
                turn,
                follower.pose().heading()
        );
        follower.manual(powers);
        follower.update();
    }

    public void driveRobotCentric(double forward, double strafe, double turn) {
        follower.manual(forward, strafe, turn);
    }

    public Follower getFollower() {
        return follower;
    }

    @Override
    public void periodic() {
        follower.update();

        telemetry.addData("pose", follower.pose());
        telemetry.addData("heading (deg)", Math.toDegrees(follower.pose().heading()));

//        telemetry.addData("velocity X", follower.tangentialVelocity().getXComponent());
//        telemetry.addData("velocity Y", follower.getVelocity().getYComponent());

        telemetry.addLine("--- Drive Motor Power ---");
        telemetry.addData("FL_Drive power", flDrive.getPower());
        telemetry.addData("FR_Drive power", frDrive.getPower());
        telemetry.addData("BL_Drive power", blDrive.getPower());
        telemetry.addData("BR_Drive power", brDrive.getPower());
    }
}