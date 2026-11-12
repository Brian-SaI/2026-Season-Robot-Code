package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Config
@Autonomous(name = "Turn Test", group = "Tuning")
@Disabled
public class TurnTest extends OpMode {

    public static double TURN_POWER = 0.0;
    MecanumDrivetrain drivetrain;
    /**
     * User-defined init method
     * <p>
     * This method will be called once, when the INIT button is pressed.
     */
    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        drivetrain = new MecanumDrivetrain(Constants.create(hardwareMap));
        drivetrain.calibratePinpoint();
        drivetrain.setPose(new Pose(0,0,0));
    }

    /**
     * User-defined loop method
     * <p>
     * This method will be called repeatedly during the period between when
     * the play button is pressed and when the OpMode is stopped.
     */
    @Override
    public void loop() {
        drivetrain.driveFieldCentric(0, 0, TURN_POWER);
        telemetry.addData("Heading", drivetrain.getPose().heading());
    }
}
