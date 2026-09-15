package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
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
    public static PinpointConfig localizerConfig = new PinpointConfig(
            c -> {
                c.name.set("pinpoint");
                c.xPodOffset.set(7.09); // was strafePodX -- TODO: confirm this maps 1:1 to xPodOffset
                c.yPodOffset.set(8.91); // was forwardPodY -- TODO: confirm this maps 1:1 to yPodOffset
                c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED); // was strafeEncoderDirection
                c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED); // was forwardEncoderDirection
                // TODO: the old file also set encoderResolution(goBILDA_SWINGARM_POD) and
                // distanceUnit(INCH). Check the current PinpointConfig reference for the
                // equivalent fields (name may differ in Pedro 3) and re-add them here.
            }
    );
    public static SwerveConfig driveConfig = new SwerveConfig(
            c -> {
                // Disables x-locking, useful while tuning pod offsets. Switch to X_LOCK
                // (or just remove this line, since X_LOCK is likely the default) once
                // Angle Offset / Motor Direction / Encoder Direction tuning is done.
                c.zeroPowerBehavior.set(SwerveConfig.ZeroPowerBehavior.IGNORE_ANGLE_CHANGES);
                c.manualBrakeMode.set(true);
                c.voltageCompensation.set(false);
            }
    );

    // Measured robot dimensions -- not tuned, just measure your drivetrain.
    // Divide mm by 25.4 to get inches, OR divide by 2 if 159mm is total length
    // (Pedro needs distance from CENTER to pod).
    private static double dtLength = 6.29; // Inches from center to front/back pod
    private static double dtWidth  = 5.39; // Inches from center to left/right pod

    // Docs: placeholder pod PIDF coefficients before running the Swerve Offsets Tuner
    // (kP = 0.3, kI = 0, kD = 0.005, kF = 0). Carried over your previously-tuned kP/kD.
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
                c.encoderReversed.set(true);

                c.angleOffsetRad.set(0.24999888910144346);
                c.podOffset.set(Vector2D.cartesian(dtLength, dtWidth));

                c.analogMinVoltage.set(0.019);
                c.analogMaxVoltage.set(3.211);
                // equivalent CoaxialPodConfig field name (e.g. encoderInverted) and set it,
                // carried value was false.
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
    
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.3);
                Controller secondaryTranslationalForward = Controller.proportional(0.1);
                Controller primaryTranslationalLateral = Controller.proportional(0.3);
                Controller secondaryTranslationalLateral = Controller.proportional(0.1);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.01));
                c.brake.set(Controller.proportionalFeedforward(0.1));
                c.headingFeedback.set(Controller.proportional(0.01));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.01, 0.01));

                c.linearBrakeCoefficients.set(Matrix.diag(0.01, 0.01));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.01, 0.01));

                c.maxAchievableForwardVelocity.set((double) 0.01);
                c.maxAchievableStrafeVelocity.set((double) 0.01);
                c.naturalForwardDeceleration.set((double) 0.01);
                c.naturalStrafeDeceleration.set((double) 0.01);
                // Note: swerve makes forward/strafe equivalent, so the Forward and Strafe
                // variants of each AutoTune identification step can share the same values.
            }
    );

    public static Follower create(HardwareMap h) {
        CoaxialPod leftFrontPod = new CoaxialPod(h, leftFront);
        CoaxialPod rightFrontPod = new CoaxialPod(h, rightFront);
        CoaxialPod leftBackPod = new CoaxialPod(h, leftBack);
        CoaxialPod rightBackPod = new CoaxialPod(h, rightBack);
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Swerve(h, driveConfig, leftBackPod, leftFrontPod, rightBackPod, rightFrontPod),
                new Foresight(foresightConfig)
        );
    }
}