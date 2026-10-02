package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.CoaxialPod;
import com.pedropathing.revhub.drivetrains.CoaxialPodConfig;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.drivetrains.Swerve;
import com.pedropathing.revhub.drivetrains.SwerveConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorEx;
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
    public static MecanumConfig DriveConfig = new MecanumConfig(
         config -> {
             config.frontLeftName.set("front Left");
             config.frontRightName.set("front right");
             config.backLeftName.set("back left");
             config.backRightName.set("back right");

             config.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
             config.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
             config.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
             config.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
             config.manualBrakeMode.set(true);
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

    // ------------------------------------------------------------------
    // Follower factory
    // NOTE: algorithm is null for now (no Foresight config). This matches
    // the docs' Localization Test setup, which explicitly runs with the
    // algorithm null while you're still tuning drivetrain/localizer.
    // Once Foresight tuning is done, add a ForesightConfig back in and
    // pass `new Foresight(foresightConfig)` as the third argument here.
    // ------------------------------------------------------------------
    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, DriveConfig),
                null
        );

    }
}