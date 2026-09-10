/* Copyright (c) 2025 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.firstinspires.ftc.teamcode.ArchivedOpModes;

//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.config.Config;
//import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

/*
 * This OpMode illustrates how to program your robot to drive field relative.  This means
 * that the robot drives the direction you push the joystick regardless of the current orientation
 * of the robot.
 *
 * This OpMode assumes that you have four mecanum wheels each on its own motor named:
 *   front_left_motor, front_right_motor, back_left_motor, back_right_motor
 *
 *   and that the left motors are flipped such that when they turn clockwise the wheel moves backwards
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 *
 */
@TeleOp(name = "[RED] TELEOP FTC-Decode", group = "Competition-Ready")
@Disabled
public class FTCDecode_SDK extends OpMode {
    // This declares the four motors needed
    DcMotor frontLeftDrive;
    DcMotor frontRightDrive;
    DcMotor backLeftDrive;
    DcMotor backRightDrive;

    DcMotorEx LShooter;

    DcMotorEx RShooter;

    DcMotor Intake;

    Servo PivotServo;

    CRServo TopRServo;

    CRServo BottomRServo;

    CRServo TopLServo;

    CRServo BottomLServo;

    Limelight3A limelight;

    //Blinky!
    Servo blinky;
    //todo: uncomment blinkies
    Servo llBlinky;

    float limelightSpeedR;
    float limelightSpeedL;

    public static float limelightShooterSpeed;

    //drive speeds
    float high;
    float mid;
    float low;
    float driveSpeed;

    float minPower;
    float maxPower;
    double targetTy;

    double distance;

    //public statics are for the dashboard!
    public static double tester = 0.0;

    //shooting spots
    public static double closeShoot = 1025.0;
    public static double semiFarShoot = 1090.0;
    public static double farShoot = 1365.0;

    public static double extraFarShoot = 1365.0;
    public static double aprilTagID = 20;

    //limelight info
    public static double limelightPosClose = -6;
    public static double limelightPosFar = -3;

    public static double txFar = 8.25;
    public static double txExtraFar = 7.25;
    public static double txSemiFar = 11;
    public static double txClose = 13.5;

    //pid info
    public static double kP = 40;
    public static double kI = 0.05;
    public static double kD = 0;
    public static double kF = 11.65;

    //FtcDashboard dashboard = FtcDashboard.getInstance();



    // This declares the IMU needed to get the current direction the robot is facing
    IMU imu;

    @Override
    public void init() {
        frontLeftDrive = hardwareMap.get(DcMotor.class, "FLdrive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "FRdrive");
        backLeftDrive = hardwareMap.get(DcMotor.class, "BLdrive");
        backRightDrive = hardwareMap.get(DcMotor.class, "BRdrive");
        LShooter = hardwareMap.get(DcMotorEx.class,    "LShooter");
        RShooter = hardwareMap.get(DcMotorEx.class,    "RShooter");
        Intake = hardwareMap.get(DcMotor.class,        "Intake");
        PivotServo = hardwareMap.get(Servo.class,"PivotServo");
        TopLServo = hardwareMap.get(CRServo.class, "TopLServo");
        TopRServo = hardwareMap.get(CRServo.class, "TopRServo");
        BottomLServo = hardwareMap.get(CRServo.class, "BottomLServo");
        BottomRServo = hardwareMap.get(CRServo.class, "BottomRServo");
        //blinky
        blinky = hardwareMap.get(Servo.class, "Blinky");
        //todo: uncomment
        llBlinky = hardwareMap.get(Servo.class, "llBlinky");

        limelightSpeedR = 0;
        limelightSpeedL = 0;

        limelightShooterSpeed = 1200;

        aprilTagID = 24;

        //drive speeds
        high = 1;
        mid = (float) 0.6;
        low = (float) 0.35;



        driveSpeed = (float) mid;

        //PivotServo.setPosition(0.91);

        // We set the left motors in reverse which is needed for drive trains where the left
        // motors are opposite to the right ones.
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);

        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        LShooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        RShooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        RShooter.setDirection(DcMotorEx.Direction.REVERSE);

        LShooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        RShooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        LShooter.setVelocityPIDFCoefficients(kP, kI, kD, kF);
        RShooter.setVelocityPIDFCoefficients(kP, kI, kD, kF);

        //backLeftDrive.setDirection();

        // This uses RUN_USING_ENCODER to be more accurate.   If you don't have the encoder
        // wires, you should remove these
//        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hardwareMap.get(IMU.class, "imu");
        // This needs to be changed to match the orientation on your robot
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection =
                RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection =
                RevHubOrientationOnRobot.UsbFacingDirection.LEFT;

        RevHubOrientationOnRobot orientationOnRobot = new
                RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));

        //limelight
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");

        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(0);
//        limelight.setPollRateHz(100);   // ask LL for data @100 Hz
        limelight.start();              // begin streaming results

        minPower = 0.125f; // minimum correction to overcome drivetrain friction
        maxPower = 1f; // maximum correction speed


        //brady's goofy gyro reset
        imu.resetYaw();
    }

    @Override
    public void loop() {
//        telemetry.addLine("Press A to reset Yaw");
//        telemetry.addLine("Hold left bumper to drive in robot relative");
//        telemetry.addLine("The left joystick sets the robot direction");
//        telemetry.addLine("Moving the right joystick left and right turns the robot");
//
//        telemetry.addLine("Bahhhhhhhhh! You stupid! No IM NOT! WHATS NINE PLUS TEN? TWENTY ONE");

        //ahh
        //telemetry.addData("ty", result.getTy());

        //PID re-setup :/
        LShooter.setVelocityPIDFCoefficients(kP, kI, kD, kF);
        RShooter.setVelocityPIDFCoefficients(kP, kI, kD, kF);


        //new
        if (gamepad1.right_bumper){
            targetTy = limelightPosClose;
        }
        else if (gamepad1.left_bumper){
            targetTy = limelightPosFar;
        }
        telemetry.addData("theta", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        telemetry.addData("drivespeedLL", limelightSpeedL);

        telemetry.addLine("Limelight ready");

        LLResult result = limelight.getLatestResult();
        //if (result != null) {
        // if (result.isValid()) {
//        Pose3D botpose = result.getFiducialResults().get(0).getCameraPoseTargetSpace();
//        telemetry.addData("tx", result.getTx());
//        telemetry.addData("ty", result.getTy());
        //telemetry.addData("Botpose", botpose.toString());
        //}
        //}

        if (result.isValid() && result.getFiducialResults().size() > 0) {
            // Get the pose of the main AprilTag relative to the camera
            Pose3D targetPose = result.getFiducialResults().get(0).getCameraPoseTargetSpace();

            // Extracted X, Y, and Z distances (usually in meters or inches depending on LL setup)
            double x = targetPose.getPosition().x;
            double y = targetPose.getPosition().y;
            double z = targetPose.getPosition().z;

            // 3D Pythagorean theorem to get absolute straight-line distance
            double straightLineDistance = Math.sqrt(x*x + y*y + z*z);

            distance = straightLineDistance;

            telemetry.addData("Distance to Tag", straightLineDistance);
        }

        // Only run lock when right/left bumper pressed and tag valid
        if ((gamepad1.right_bumper || gamepad1.left_bumper) && result.isValid()) {
            if (result.isValid() == false) {
                limelightSpeedL = 0.5f;
                limelightSpeedR = 0.5f;
            }
            else if (result.getTy() < (targetTy-2) || result.getTy() > (targetTy+2)) {
                if (result.getFiducialResults().size() > 0 && result.getFiducialResults().get(0).getFiducialId() == aprilTagID) {

                    double error = targetTy - result.getTy();     // positive = need to turn right

                    double travel = Math.abs(error);              // distance left
                    double scale = Math.sqrt(Math.min(1, travel / 20.0));

                    double turn = error * -0.03 * scale;           // 0.03 = proportional gain


                    turn = Math.max(-maxPower, Math.min(maxPower, turn));

                    if (Math.abs(turn) > 0 && Math.abs(turn) < minPower) {
                        turn = Math.signum(turn) * minPower;
                    }


                        limelightSpeedL = (float) turn;
                        limelightSpeedR = -(float) turn;

                    telemetry.addData("LL ERROR", error);
                    telemetry.addData("LL SCALE", scale);
                    telemetry.addData("LL TURN", turn);


                } else {
                    limelightSpeedL = 0;
                    limelightSpeedR = 0;
                    telemetry.addLine("Wrong tag");
                }
            }
            else {
                limelightSpeedL = 0;
                limelightSpeedR = 0;
            }

        } else {
            limelightSpeedL = 0;
            limelightSpeedR = 0;
        }



        if (result.isValid()) {
            if (distance<=1 && result.isValid()) {
                telemetry.addData("shooting: close!", limelightShooterSpeed);
                PivotServo.setPosition(0.75);
                limelightShooterSpeed = (float) closeShoot;
            }
            else if (distance<=1.45 && result.isValid()){
                telemetry.addData("shooting: semi-far!", limelightShooterSpeed);
                PivotServo.setPosition(0.75);
                limelightShooterSpeed = (float) semiFarShoot;
            }
            else if (distance>=1.75 && result.isValid()){
                telemetry.addData("shooting: extra-far!", limelightShooterSpeed);
                PivotServo.setPosition(0.675);
                limelightShooterSpeed = (float) extraFarShoot;
            }
        }

        LLResult results = limelight.getLatestResult();

        // if (results == null) {
        //telemetry.addLine("no apriltags!");
        if (result.isValid()) {
            telemetry.addLine("Visible Apriltag!");
            //telemetry.addData("Distance", results.getFiducialResults().);
//                Pose3D botpose = result.getBotpose_MT2();
//                // Use botpose data
        }
        else{
            telemetry.addLine("no apriltags!");
        }
        //}




        // If you press the A button, then you reset the Yaw to be zero from the way
        // the robot is currently pointing
        if (gamepad1.cross) {
            imu.resetYaw();
        }
        // If you press the left bumper, you get a drive from the point of view of the robot
        // (much like driving an RC vehicle)
        if (gamepad1.left_bumper){
            //drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, -gamepad1.right_stick_x);
            driveFieldRelative(-gamepad1.left_stick_y, gamepad1.left_stick_x, -gamepad1.right_stick_x);
        } else {
            driveFieldRelative(-gamepad1.left_stick_y, gamepad1.left_stick_x, -gamepad1.right_stick_x);
        }

        if (gamepad1.dpad_down) {
            driveSpeed = low;
        }
        else if (gamepad1.dpad_up) {
            driveSpeed = mid;
        }
        else {
            driveSpeed = high;
        }

        //shooter stuff
        if (gamepad2.right_bumper || gamepad2.dpad_up || gamepad1.right_trigger_pressed){
            //PivotServo.setPosition(0.905);
            LShooter.setVelocity(limelightShooterSpeed);
            RShooter.setVelocity(limelightShooterSpeed); //perfect for wall shoot
        }
//        else if (gamepad2.dpad_up){
//            //PivotServo.setPosition(0.9);
//            LShooter.setVelocity(limelightShooterSpeed);
//            RShooter.setVelocity(limelightShooterSpeed); //perfect for wall shoot
//        }
        //outake?
        else if (gamepad2.dpad_down) {
            //PivotServo.setPosition(0.905);
            LShooter.setVelocity(-1450);
            RShooter.setVelocity(1400);
        }
        else {
            //PivotServo.setPosition(0.905);
            LShooter.setVelocity(0);
            RShooter.setVelocity(0);
        }
        if (LShooter.getVelocity() >= (limelightShooterSpeed-50) && RShooter.getVelocity() >= (limelightShooterSpeed-50)) {
            blinky.setPosition(0.5);
        } else {
            blinky.setPosition(0);
        }
        //todo: uncomment code
        if (result.getTy() > (targetTy-2) && result.getTy() < (targetTy+2) && (result.getFiducialResults().size() > 0 && result.getFiducialResults().get(0).getFiducialId() == aprilTagID)) {
            llBlinky.setPosition(0.61);
        } else {
            llBlinky.setPosition(0);
        }

        //intake stuff
        if (gamepad2.square || gamepad1.left_trigger_pressed) {
            Intake.setPower(-1);
            TopLServo.setPower(-1);
            TopRServo.setPower(1);
            BottomLServo.setPower(-1);
            BottomRServo.setPower(1);
        }
        else if (gamepad2.circle || gamepad1.x) {
            Intake.setPower(0);
            TopLServo.setPower(1);
            TopRServo.setPower(-1);
            BottomLServo.setPower(1);
            BottomRServo.setPower(-1);
        }
        else if (gamepad2.triangle || gamepad1.square) {
            Intake.setPower(1);
            TopLServo.setPower(1);
            TopRServo.setPower(-1);
            BottomLServo.setPower(1);
            BottomRServo.setPower(-1);
        }
        else {
            Intake.setPower(0);
            TopLServo.setPower(0);
            TopRServo.setPower(0);
            BottomLServo.setPower(0);
            BottomRServo.setPower(0);
        }
//        if (gamepad2.dpad_up){
//            PivotServo.setPosition(0.89);
//        }
//        if (gamepad2.dpad_down){
//            PivotServo.setPosition(0.91);
//        }
//        TelemetryPacket packet = new TelemetryPacket();

        LShooter.setVelocityPIDFCoefficients(kP, kI, kD, kF);
        RShooter.setVelocityPIDFCoefficients(kP, kI, kD, kF);

//        packet.put("shooter velocity LEFT", LShooter.getVelocity());
//        packet.put("shooter velocity RIGHT", RShooter.getVelocity());
////        packet.put("kD", kD);
////        packet.put("kF", kF);
//
//        packet.put("AprilTagID", aprilTagID);
//
//        packet.put("closeShoot", closeShoot);
//        packet.put("limelightShootSpeed", limelightShooterSpeed);
//        packet.put("Tester", tester);
//
//        packet.put("tx", result.getTx());
//        packet.put("ty", result.getTy());
//        packet.put("Botpose", botpose.toString());
//        packet.put("Valid Target", result.isValid());
//        packet.put("Tag Count", result.getFiducialResults().size());
//
//        if (result.isValid()) {
//            packet.put("Tag ID", result.getFiducialResults().get(0).getFiducialId());
//        }
//
//        packet.put("LL Speed L", limelightSpeedL);
//        packet.put("LL Speed R", limelightSpeedR);
//        packet.put("Shooter Speed", limelightShooterSpeed);
//        packet.put("Drive Speed Setting", driveSpeed);
//
//        dashboard.sendTelemetryPacket(packet);
    }

    //limelight stupid stuff :[
    //LLResult result = limelight.getLatestResult();

    //double ty = result.getTy(); // up/down offset (deg)

    // This routine drives the robot field relative
    private void driveFieldRelative(double forward, double right, double rotate) {
        // First, convert direction being asked to drive to polar coordinates
        double theta = Math.atan2(forward, right);
        double r = Math.hypot(right, forward);

        // Second, rotate angle by the angle the robot is pointing
        theta = AngleUnit.normalizeRadians(theta -
                imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        // Third, convert back to cartesian
        double newForward = r * Math.sin(theta);
        double newRight = r * Math.cos(theta);

        // Finally, call the drive method with robot relative forward and right amounts
        drive(newForward, newRight, rotate);
    }

    // Thanks to FTC16072 for sharing this code!!
    public void drive(double forward, double right, double rotate) {
        // This calculates the power needed for each wheel based on the amount of forward,
        // strafe right, and rotate
        double frontLeftPower = forward + right + rotate;
        double frontRightPower = forward - right - rotate;
        double backRightPower = forward + right - rotate;
        double backLeftPower = forward - right + rotate;

        double maxPower = 1.0;
        double maxSpeed = 1.0;  // make this slower for outreaches

        // This is needed to make sure we don't pass > 1.0 to any wheel
        // It allows us to keep all of the motors in proportion to what they should
        // be and not get clipped
        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));

        // We multiply by maxSpeed so that it can be set lower for outreaches
        // When a young child is driving the robot, we may not want to allow full
        // speed.
        frontLeftDrive.setPower(((maxSpeed * (frontLeftPower / maxPower))*driveSpeed)+limelightSpeedL);
        frontRightDrive.setPower(((maxSpeed * (frontRightPower / maxPower))*driveSpeed)+limelightSpeedR);
        backLeftDrive.setPower(((maxSpeed * (backLeftPower / maxPower))*driveSpeed)+limelightSpeedL);
        backRightDrive.setPower(((maxSpeed * (backRightPower / maxPower))*driveSpeed)+limelightSpeedR);
    }
}
