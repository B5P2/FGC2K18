package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
/**
 * Created by tjones on 4/3/2017.

 FIRST Global Java SDK Startup Guide - Rev 1 Copyright 2017 REV Robotics, LLC 16
 */
@TeleOp(name="My Testing Op Mode", group="Practice-Bot")
public class TestingOpMode extends LinearOpMode {
    private DcMotor leftMotor1;
    private DcMotor leftMotor2;
    private DcMotor rightMotor1;
    private DcMotor rightMotor2;
    private ElapsedTime period = new ElapsedTime();
    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome
     * with a regular periodic tick. This is used to compensate for varying
     * processing times for each cycle. The function looks at the elapsed cycle time,
     * and sleeps for the remaining time interval.
     *
     * @param periodMs Length of wait cycle in mSec.
     */
    private void waitForTick(long periodMs) throws java.lang.InterruptedException {
        long remaining = periodMs - (long)period.milliseconds();
// sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            Thread.sleep(remaining);
        }
// Reset the cycle clock for the next pass.
        period.reset();
    }
    @Override
    public void runOpMode() {
        double side = 0.0;
        double sideSlow = 0.0;
        double up = 0.0;
        double upSlow = 0.0;
        leftMotor1 = hardwareMap.dcMotor.get("left_drive1");
        leftMotor2 = hardwareMap.dcMotor.get("left_drive2");
        rightMotor1 = hardwareMap.dcMotor.get("right_drive1");
        rightMotor2 = hardwareMap.dcMotor.get("right_drive2");
        rightMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor2.setDirection(DcMotorSimple.Direction.FORWARD);
        leftMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor1.setDirection(DcMotorSimple.Direction.FORWARD);
// Set all motors to zero power
        leftMotor1.setPower(0);
        leftMotor2.setPower(0);
        rightMotor1.setPower(0);
        rightMotor2.setPower(0);
// Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver"); //
        telemetry.update();
// Wait for the game to start (driver presses PLAY)
        waitForStart();
        try {
// run until the end of the match (driver presses STOP)
            while (opModeIsActive()) {
// Run wheels in tank mode (note: The joystick goes negative when pushed forwards,so negate it)
                side = gamepad1.left_stick_x;
                up = -gamepad1.left_stick_y;
                sideSlow = gamepad1.right_stick_x/3;
                upSlow = -gamepad1.right_stick_y/3;
                leftMotor1.setPower(up+upSlow);
                leftMotor2.setPower(up+upSlow);
                rightMotor1.setPower(side+sideSlow);
                rightMotor2.setPower(side+sideSlow);
                if(gamepad1.right_bumper){
                    leftMotor1.setPower(-1);
                    leftMotor2.setPower(1);
                    rightMotor1.setPower(1);
                    rightMotor2.setPower(-1);
                }
                if(gamepad1.left_bumper){
                    leftMotor1.setPower(1);
                    leftMotor2.setPower(-1);
                    rightMotor1.setPower(-1);
                    rightMotor2.setPower(1);
                }

// Send telemetry message to signify robot running;

                telemetry.addData("x_axis", "%.2f", side);
                telemetry.addData("y_axis", "%.2f", up);
                telemetry.update();
// Pause for metronome tick. 40 mS each cycle = update 25 times a second.

                waitForTick(40);

            }
        }
        catch (java.lang.InterruptedException exc) {
            return;
        }
        finally {
            leftMotor1.setPower(0);
            leftMotor2.setPower(0);
            rightMotor1.setPower(0);
            rightMotor2.setPower(0);
        }
    }
}