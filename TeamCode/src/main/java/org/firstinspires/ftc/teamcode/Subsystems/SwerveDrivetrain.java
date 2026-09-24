package org.firstinspires.ftc.teamcode.Subsystems;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedro.Constants;

public class SwerveDrivetrain extends SubsystemBase {

    private final Follower follower;
    private final Telemetry telemetry;

    private final DcMotorEx flDrive, frDrive, blDrive, brDrive;

    // --- Power-management tuning knobs ---
    private static final double DEADBAND = 0.05;
    private static final double MAX_POWER = 0.85;   // global cap, tune to taste
    private static final double SLEW_RATE = 0.08;   // max change in power per periodic() call

    private double curForward = 0, curStrafe = 0, curTurn = 0;

    public SwerveDrivetrain(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        follower = Constants.create(hardwareMap);
        follower.setPose(new Pose(75, 75, Math.toRadians(180)));
        follower.update();

        flDrive = hardwareMap.get(DcMotorEx.class, "FL_Drive");
        frDrive = hardwareMap.get(DcMotorEx.class, "FR_Drive");
        blDrive = hardwareMap.get(DcMotorEx.class, "BL_Drive");
        brDrive = hardwareMap.get(DcMotorEx.class, "BR_Drive");
    }

    /**
     * Call once, after the driver hits play.
     * @param brakeMode true to hold position under zero power, false to float (saves current)
     */
    public void startTeleopDrive(boolean brakeMode) {
        DcMotor.ZeroPowerBehavior behavior = brakeMode
                ? DcMotor.ZeroPowerBehavior.BRAKE
                : DcMotor.ZeroPowerBehavior.FLOAT;

        flDrive.setZeroPowerBehavior(behavior);
        frDrive.setZeroPowerBehavior(behavior);
        blDrive.setZeroPowerBehavior(behavior);
        brDrive.setZeroPowerBehavior(behavior);

        follower.manual();
    }

//    public void driveFieldCentric(double forward, double strafe, double turn) {
//        forward = shape(forward);
//        strafe = shape(strafe);
//        turn = shape(turn);
//
//        curForward = slew(curForward, forward);
//        curStrafe  = slew(curStrafe, strafe);
//        curTurn    = slew(curTurn, turn);
//
//        DrivePowers powers = ManualDrive.fieldCentric(
//                curForward,
//                curStrafe,
//                curTurn,
//                -follower.pose().heading()
//        );
//
//        follower.manual(powers);
//    }

    private static final double CURRENT_LIMIT_AMPS = 8.0; // tune to your motor's rated max

    public void driveFieldCentric(double forward, double strafe, double turn) {
        if (isOverCurrentLimit()) {
            follower.manual(0, 0, 0); // stop the drivetrain rather than freezing at a stall power
            return;
        }

        forward = shape(forward);
        strafe = shape(strafe);
        turn = shape(turn);

        curForward = slew(curForward, forward);
        curStrafe  = slew(curStrafe, strafe);
        curTurn    = slew(curTurn, turn);

        DrivePowers powers = ManualDrive.fieldCentric(
                curForward,
                curStrafe,
                curTurn,
                -follower.pose().heading()
        );

        follower.manual(powers);
    }

    private boolean isOverCurrentLimit() {
        return flDrive.getCurrent(CurrentUnit.AMPS) > CURRENT_LIMIT_AMPS
                || frDrive.getCurrent(CurrentUnit.AMPS) > CURRENT_LIMIT_AMPS
                || blDrive.getCurrent(CurrentUnit.AMPS) > CURRENT_LIMIT_AMPS
                || brDrive.getCurrent(CurrentUnit.AMPS) > CURRENT_LIMIT_AMPS;
    }

    public void driveRobotCentric(double forward, double strafe, double turn) {
        follower.manual(shape(forward), shape(strafe), shape(turn));
    }

    /**
     * Deadband + cap + slew-limit a single axis of input.
     * Cuts stall current from stick noise, prevents full-power commands,
     * and prevents instantaneous direction-reversal current spikes.
     */
    private double shape(double input) {
        if (Math.abs(input) < DEADBAND) input = 0;
        input = Math.max(-MAX_POWER, Math.min(MAX_POWER, input));
        return input;
    }

    // Optional: apply slew limiting per-axis if you want smoother ramps
    // (call this from driveFieldCentric/driveRobotCentric instead of shape()
    //  if snap-direction-changes are your main concern)
    private double slew(double current, double target) {
        double delta = target - current;
        if (Math.abs(delta) > SLEW_RATE) {
            return current + Math.signum(delta) * SLEW_RATE;
        }
        return target;
    }

    public Follower getFollower() {
        return follower;
    }

    @Override
    public void periodic() {
        follower.update();

        telemetry.addData("pose", follower.pose());
        telemetry.addData("heading (deg)", Math.toDegrees(follower.pose().heading()));

        telemetry.addLine("--- Drive Motor Power ---");
        telemetry.addData("FL_Drive power", flDrive.getPower());
        telemetry.addData("FR_Drive power", frDrive.getPower());
        telemetry.addData("BL_Drive power", blDrive.getPower());
        telemetry.addData("BR_Drive power", brDrive.getPower());

        telemetry.addLine("--- Drive Motor Current (A) ---");
        telemetry.addData("FL_Drive current", flDrive.getCurrent(CurrentUnit.AMPS));
        telemetry.addData("FR_Drive current", frDrive.getCurrent(CurrentUnit.AMPS));
        telemetry.addData("BL_Drive current", blDrive.getCurrent(CurrentUnit.AMPS));
        telemetry.addData("BR_Drive current", brDrive.getCurrent(CurrentUnit.AMPS));
    }
}