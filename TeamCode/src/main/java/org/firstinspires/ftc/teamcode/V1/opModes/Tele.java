package org.firstinspires.ftc.teamcode.V1.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.V1.Robot;

@TeleOp
public class Tele extends OpMode {
    private Robot r;

    @Override
    public void init() {
        r = new Robot(this); //init robot
        r.shooter.in();
        r.transfer.flowerIn();
    }

    @Override
    public void start() {
        r.shooter.far();
        r.shooter.in();
    }

    @Override
    public void loop() {

        //tele drive
        r.follower.manual(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x);
        r.follower.update();
        telemetry.addData("pose", r.follower.pose().toString());
        r.shooter.telemetry(telemetry);

        r.shooter.update(); //update the shooter PID controller

        if (gamepad2.x) {
            r.transfer.shoot(); //shoot
        }

        //intake and shoot logic
        if (gamepad2.xWasPressed()) {
            r.shooter.out();//shoot
        } else if (gamepad2.aWasPressed()) { //else if creates priority stack
            r.shooter.in(); //intake
            r.transfer.run();
        } else if (gamepad2.yWasPressed()) {
            r.shooter.out(); //outtake (yes stopper goes out)
            r.transfer.back();
        } else if (gamepad2.bWasPressed()) {
            r.transfer.intakeBack(); //intake out
        } else if (!gamepad2.x && !gamepad2.a && !gamepad2.y && !gamepad2.b){
            r.shooter.in(); //default; I don't care about loop times
            r.transfer.off();
        }

        if (gamepad2.dpadUpWasPressed()) { //set shooter and hood to close or far
            r.shooter.close();
        } else if(gamepad2.dpadDownWasPressed()) {
            r.shooter.far();
        }

        //flower
        if (gamepad2.right_bumper) {
            r.transfer.flowerOut();
        } else if (gamepad2.left_bumper) {
            r.transfer.flowerIn();
        }

    }

    @Override
    public void stop() {
        r.shooter.off();
        r.transfer.off();
    }
}
