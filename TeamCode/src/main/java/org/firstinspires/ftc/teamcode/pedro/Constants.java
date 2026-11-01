package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    // ------------------------------------------------------------------
    // Localizer (goBILDA Pinpoint)
    // ------------------------------------------------------------------
    public static PinpointConfig localizerConfig = new PinpointConfig(
            c -> {
                c.name.set("Pinpoint");
                c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
                c.xPodOffset.set(5.0);
                c.yPodOffset.set(0.0);
                c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
                c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
                c.globalDistanceUnit.set(DistanceUnit.INCH);
                c.offsetUnits.set(DistanceUnit.INCH);
            });

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
             config.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
             config.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
             config.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
             config.manualBrakeMode.set(true);
         }
    );



    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.2976142218866183);
                Controller secondaryTranslationalForward = Controller.proportional(0.10996050258863525);
                Controller primaryTranslationalLateral = Controller.proportional(0.5750248276784266);
                Controller secondaryTranslationalLateral = Controller.proportional(0.21245630888080277);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.01723358570072716));
                c.brake.set(Controller.proportionalFeedforward(0.014648547845618086));

                c.headingFeedback.set(Controller.proportional(6.712618713669538));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.06295772998355874, 0.0071452731483920215));

                c.linearBrakeCoefficients.set(Matrix.diag(0.1022920855426264, 0.05966218215957485));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0016051454922211626, 0.002266273021635695));

                c.maxAchievableForwardVelocity.set(72.93091312368202);
                c.maxAchievableStrafeVelocity.set(53.01298166122062);
                c.naturalForwardDeceleration.set(27.694851977583866);
                c.naturalStrafeDeceleration.set(66.80468205342298);
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
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, DriveConfig),
                new Foresight(foresightConfig)
        );

    }
}