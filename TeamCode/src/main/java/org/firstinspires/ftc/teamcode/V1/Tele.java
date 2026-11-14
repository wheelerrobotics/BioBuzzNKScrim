package org.firstinspires.ftc.teamcode.V1;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Tele extends OpMode {
    private Robot r;

    @Override
    public void init() {
        r = new Robot(this); //init robot
    }

    @Override
    public void loop() {
        // FIXME: 11/14/26 FIX THE CONSTANTS.CREATE() RETURN ONCE MECANUM TUNER DONE

        //tele drive
        r.follower.manual(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        telemetry.addData("pose", r.follower.pose().toString());
        r.shooter.telemetry(telemetry);

        //intake and shoot logic
        if (gamepad2.xWasPressed()) {
            r.transfer.on(); //shoot
            r.shooter.out();
        } else if (gamepad2.aWasPressed()) { //else if creates priority stack
            r.shooter.in(); //intake
            r.transfer.on();
        } else if (gamepad2.yWasPressed()) {
            r.shooter.out(); //outtake (yes stopper goes out)
            r.transfer.out();
        } else {
            r.shooter.in(); //default; I don't care about loop times
            r.transfer.off();
        }

        if (gamepad2.dpadUpWasPressed()) { //set shooter and hood to close or far
            r.shooter.close();
        } else if(gamepad2.dpadDownWasPressed()) {
            r.shooter.far();
        }

    }
}
