package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@TeleOp(name = "Field Centric Swerve Test")
public class FieldCentricTeleOp extends OpMode {

    private Follower follower;

    // Direct motor references so we can read back actual commanded power,
    // independent of whatever Pedro's own debug string is reporting.
    private DcMotor flDrive, frDrive, blDrive, brDrive;

    // EMA (exponential moving average) smoothing for stick inputs.
    // alpha closer to 1.0 = less smoothing (more responsive, more jitter passes through)
    // alpha closer to 0.0 = more smoothing (less jitter, but more input lag)
    // Start at 0.3 and adjust to taste -- this is the main knob to tweak.
    private static final double SMOOTHING_ALPHA = 0.3;
    private double smoothedForward = 0;
    private double smoothedStrafe = 0;
    private double smoothedTurn = 0;

    private double smooth(double previous, double target) {
        return previous + SMOOTHING_ALPHA * (target - previous);
    }

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        // Starting pose matters for field-centric -- heading here is what "forward" means
        // to the field-centric math. (0,0,0) is fine just for feel-testing drive.
        follower.setStartingPose(new Pose(0, 0, 0));
        follower.update();

        // Names must match exactly what's in Constants.java's hardware map calls.
        flDrive = hardwareMap.get(DcMotor.class, "FL_Drive");
        frDrive = hardwareMap.get(DcMotor.class, "FR_Drive");
        blDrive = hardwareMap.get(DcMotor.class, "BL_Drive");
        brDrive = hardwareMap.get(DcMotor.class, "BR_Drive");

        follower.useCentripetal = false;
    }

    @Override
    public void start() {
        // true = use brake mode on the drive motors while driver-controlled
        follower.startTeleopDrive(true);
    }

    @Override
    public void loop() {
        smoothedForward = smooth(smoothedForward, -gamepad1.left_stick_y);
        smoothedStrafe = smooth(smoothedStrafe, -gamepad1.left_stick_x);
        smoothedTurn = smooth(smoothedTurn, -gamepad1.right_stick_x);

        follower.setTeleOpDrive(
                smoothedForward,
                smoothedStrafe,
                smoothedTurn,
                false                    // false = FIELD CENTRIC
        );

        follower.update();

        telemetry.addData("pose", follower.getPose());
        telemetry.addData("heading (deg)", Math.toDegrees(follower.getPose().getHeading()));

        telemetry.addData("velocity X", follower.getVelocity().getXComponent());
        telemetry.addData("velocity Y", follower.getVelocity().getYComponent());
        telemetry.addData("velocity magnitude", follower.getVelocity().getMagnitude());

        com.pedropathing.math.Vector robotFrameVel = follower.getVelocity().copy();
        robotFrameVel.rotateVector(-follower.getPose().getHeading());
        telemetry.addData("robot-frame vel X", robotFrameVel.getXComponent());
        telemetry.addData("robot-frame vel Y", robotFrameVel.getYComponent());

        telemetry.addLine("--- Drive Motor Power (actual, read from hardware) ---");
        telemetry.addData("FL_Drive power", flDrive.getPower());
        telemetry.addData("FR_Drive power", frDrive.getPower());
        telemetry.addData("BL_Drive power", blDrive.getPower());
        telemetry.addData("BR_Drive power", brDrive.getPower());

        telemetry.update();
    }
}