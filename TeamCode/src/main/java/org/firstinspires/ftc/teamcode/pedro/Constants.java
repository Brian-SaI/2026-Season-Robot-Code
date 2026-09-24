package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.CoaxialPod;
import com.pedropathing.revhub.drivetrains.CoaxialPodConfig;
import com.pedropathing.revhub.drivetrains.Swerve;
import com.pedropathing.revhub.drivetrains.SwerveConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {

    // ------------------------------------------------------------------
    // Localizer (goBILDA Pinpoint)
    // ------------------------------------------------------------------
    public static PinpointConfig localizerConfig = new PinpointConfig(
            c -> {
                c.name.set("pinpoint");
                c.xPodOffset.set(7.09); // forward pod offset, inches from center
                c.yPodOffset.set(8.91); // strafe pod offset, inches from center
                c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
                c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
            }
    );

    // ------------------------------------------------------------------
    // Swerve drivetrain config
    // ------------------------------------------------------------------
    public static SwerveConfig driveConfig = new SwerveConfig(
            c -> {
                // Disables x-locking, useful while tuning pod offsets. Switch to X_LOCK
                // once swerve tuning is fully complete (recommended per the docs).
                c.zeroPowerBehavior.set(SwerveConfig.ZeroPowerBehavior.IGNORE_ANGLE_CHANGES);
                c.manualBrakeMode.set(true);
                c.voltageCompensation.set(false);
            }
    );

    // Measured robot dimensions -- not tuned, just measure your drivetrain.
    // Distance is measured from the CENTER of the robot to each pod.
    private static double dtLength = 6.29; // inches, center to front/back pod
    private static double dtWidth  = 5.39; // inches, center to left/right pod

    // Pod PIDF coefficients (kP, kD tuned; kF split front/back for weight distribution)
    private static double kP = 0.3;
    private static double kD = 0.0005;
    private static double kFFront = 0;
    private static double kFBack = 0;

    public static CoaxialPodConfig leftFront = new CoaxialPodConfig(
            c -> {
                c.name.set("leftFront");
                c.motorName.set("FL_Drive");
                c.servoName.set("FL_Steer");
                c.servoEncoderName.set("FL_Position");
                c.turnController.set(Controller.pid(kP, 0, kD)
                        .plus(Controller.proportionalFeedforward(kFFront)));

                c.driveDirection.set(DcMotorSimple.Direction.FORWARD);
                c.servoDirection.set(DcMotorSimple.Direction.FORWARD);
                // NOTE: unverified against current docs -- confirm this field still
                // exists/compiles against your installed com.pedropathing:revhub version.
                c.encoderReversed.set(true);

                c.angleOffsetRad.set(0.24999888910144346);
                c.podOffset.set(Vector2D.cartesian(dtLength, dtWidth));

                c.analogMinVoltage.set(0.019);
                c.analogMaxVoltage.set(3.211);
            }
    );

    public static CoaxialPodConfig rightFront = new CoaxialPodConfig(
            c -> {
                c.name.set("rightFront");
                c.motorName.set("FR_Drive");
                c.servoName.set("FR_Steer");
                c.servoEncoderName.set("FR_Position");
                c.turnController.set(Controller.pid(kP, 0, kD)
                        .plus(Controller.proportionalFeedforward(kFFront)));

                c.driveDirection.set(DcMotorSimple.Direction.FORWARD);
                c.servoDirection.set(DcMotorSimple.Direction.FORWARD);
                c.encoderReversed.set(true);

                c.angleOffsetRad.set(2.987794697052968);
                c.podOffset.set(Vector2D.cartesian(dtLength, -dtWidth));

                c.analogMinVoltage.set(0.006);
                c.analogMaxVoltage.set(3.213);
            }
    );

    public static CoaxialPodConfig leftBack = new CoaxialPodConfig(
            c -> {
                c.name.set("leftBack");
                c.motorName.set("BL_Drive");
                c.servoName.set("BL_Steer");
                c.servoEncoderName.set("BL_Position");
                c.turnController.set(Controller.pid(kP, 0, kD)
                        .plus(Controller.proportionalFeedforward(kFBack)));

                c.driveDirection.set(DcMotorSimple.Direction.FORWARD);
                c.servoDirection.set(DcMotorSimple.Direction.FORWARD);
                c.encoderReversed.set(true);

                c.angleOffsetRad.set(2.8300865944130758);
                c.podOffset.set(Vector2D.cartesian(-dtLength, dtWidth));

                c.analogMinVoltage.set(0.004);
                c.analogMaxVoltage.set(3.201);
            }
    );

    public static CoaxialPodConfig rightBack = new CoaxialPodConfig(
            c -> {
                c.name.set("rightBack");
                c.motorName.set("BR_Drive");
                c.servoName.set("BR_Steer");
                c.servoEncoderName.set("BR_Position");
                c.turnController.set(Controller.pid(kP, 0, kD)
                        .plus(Controller.proportionalFeedforward(kFBack)));

                c.driveDirection.set(DcMotorSimple.Direction.FORWARD);
                c.servoDirection.set(DcMotorSimple.Direction.FORWARD);
                c.encoderReversed.set(true);

                c.angleOffsetRad.set(4.359082143018361);
                c.podOffset.set(Vector2D.cartesian(-dtLength, -dtWidth));

                c.analogMinVoltage.set(0.006);
                c.analogMaxVoltage.set(3.216);
            }
    );

    // ------------------------------------------------------------------
    // Follower factory
    // NOTE: algorithm is null for now (no Foresight config). This matches
    // the docs' Localization Test setup, which explicitly runs with the
    // algorithm null while you're still tuning drivetrain/localizer.
    // Once Foresight tuning is done, add a ForesightConfig back in and
    // pass `new Foresight(foresightConfig)` as the third argument here.
    // ------------------------------------------------------------------
    public static Follower create(HardwareMap h) {
        CoaxialPod leftFrontPod = new CoaxialPod(h, leftFront);
        CoaxialPod rightFrontPod = new CoaxialPod(h, rightFront);
        CoaxialPod leftBackPod = new CoaxialPod(h, leftBack);
        CoaxialPod rightBackPod = new CoaxialPod(h, rightBack);
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Swerve(h, driveConfig, leftBackPod, leftFrontPod, rightBackPod, rightFrontPod),
                null
        );
    }
}