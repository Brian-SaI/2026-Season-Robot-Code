package org.firstinspires.ftc.teamcode.Subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

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

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(0, 0, 0));
        follower.useCentripetal = false; // disabled per teleop testing -- revisit once path-following is tuned
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
        follower.startTeleopDrive(brakeMode);
    }

    /**
     * Drives the swerve base, field-centric.
     *
     * @param forward -1 to 1, field-forward
     * @param strafe  -1 to 1, field-left-positive (Pedro convention)
     * @param turn    -1 to 1, CCW-positive
     */
    public void driveFieldCentric(double forward, double strafe, double turn) {
        follower.setTeleOpDrive(forward, strafe, turn, false);
    }

    /**
     * Drives the swerve base, robot-centric.
     */
    public void driveRobotCentric(double forward, double strafe, double turn) {
        follower.setTeleOpDrive(forward, strafe, turn, true);
    }

    public Follower getFollower() {
        return follower;
    }

    @Override
    public void periodic() {
        follower.update();

        telemetry.addData("pose", follower.getPose());
        telemetry.addData("heading (deg)", Math.toDegrees(follower.getPose().getHeading()));

        telemetry.addData("velocity X", follower.getVelocity().getXComponent());
        telemetry.addData("velocity Y", follower.getVelocity().getYComponent());

        telemetry.addLine("--- Drive Motor Power ---");
        telemetry.addData("FL_Drive power", flDrive.getPower());
        telemetry.addData("FR_Drive power", frDrive.getPower());
        telemetry.addData("BL_Drive power", blDrive.getPower());
        telemetry.addData("BR_Drive power", brDrive.getPower());
    }
}