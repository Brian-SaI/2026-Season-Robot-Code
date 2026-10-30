package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.VisionSubsystem;

@TeleOp(name = "Camera Test", group = "Tuning")
public class TeleopCameraTest extends OpMode {

    VisionSubsystem subsystem;

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), telemetry);
        subsystem = new VisionSubsystem(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        subsystem.periodic();
    }
}
