package org.firstinspires.ftc.teamcode.V1.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.V1.Robot;

@TeleOp
public class TransferTest extends OpMode {
    private Robot r;

    @Override
    public void init() {
        r = new Robot(this); //init robot
        r.shooter.out();
    }

    @Override
    public void loop() {
        if (gamepad2.xWasPressed()) {
            r.transfer.run();
        } else if (gamepad2.yWasPressed()) {
            r.transfer.back();
        } else if (!gamepad2.x && !gamepad2.a && !gamepad2.y){
            r.transfer.off();
        }

    }

}
