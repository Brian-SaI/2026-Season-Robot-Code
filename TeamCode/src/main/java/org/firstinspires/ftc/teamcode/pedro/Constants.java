package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.follower.Follower;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
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
                c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
                c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
                c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
            }
    );

    // ------------------------------------------------------------------
    // Swerve drivetrain config
    // ------------------------------------------------------------------
    public static MecanumConfig DriveConfig = new MecanumConfig(
         config -> {
             config.frontLeftName.set("FL Drive");
             config.frontRightName.set("FR Drive");
             config.backLeftName.set("BL Drive");
             config.backRightName.set("BR Drive");

             config.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
             config.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
             config.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
             config.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
             config.manualBrakeMode.set(true);
         }
    );

    public static ForesightConfig foresightConfig;  // TODO

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
                new Foresight(foresightConfig)
        );

    }
}