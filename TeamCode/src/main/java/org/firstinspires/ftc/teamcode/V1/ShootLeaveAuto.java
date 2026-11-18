package org.firstinspires.ftc.teamcode.V1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class ShootLeaveAuto extends OpMode {

    private Robot r;
    private ElapsedTime shootTimer;
    public static int shootTime = 5;
    private ElapsedTime actionTimer;
    public static int moveTime = 300;
    public static int waitTime = 2000;

    @Override
    public void init() {
        r = new Robot(this);
        shootTimer = new ElapsedTime();
        actionTimer = new ElapsedTime();

    }

    public void start() {
        r.shooter.far();
        r.shooter.in();

        r.shooter.update();

        actionTimer.reset();
        while (actionTimer.milliseconds()<waitTime);


        shootTimer.reset();
        while (shootTimer.seconds() < shootTime) {
            r.shooter.out();
            r.transfer.run();
            r.shooter.update();
        }

        r.transfer.off();
        r.shooter.in();

        actionTimer.reset();
        while(actionTimer.seconds() < moveTime) {
            r.follower.manual(1, 0, 0);
        }
        r.follower.manual(0,0,0);
    }

    public void loop() {

    }
}
