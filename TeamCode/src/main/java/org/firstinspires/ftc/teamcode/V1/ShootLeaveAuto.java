package org.firstinspires.ftc.teamcode.V1;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
@Configurable
public class ShootLeaveAuto extends OpMode {

    private Robot r;
    private ElapsedTime shootTimer;
    public static int shootTime = 3000;
    private ElapsedTime actionTimer;
    public static int moveTime = 300;
    public static int waitTime = 2000;

    @Override
    public void init() {
        r = new Robot(this);
        shootTimer = new ElapsedTime();
        actionTimer = new ElapsedTime();

    }

    @Override
    public void start() {
        r.shooter.far();
        r.shooter.in();

        r.shooter.update();

        actionTimer.reset();
        while (actionTimer.milliseconds()<waitTime);


        shootTimer.reset();
        while (shootTimer.milliseconds() < shootTime) {
            r.shooter.out();
            r.transfer.run();
            r.shooter.update();
        }

        r.transfer.off();
        r.shooter.in();

        actionTimer.reset();
        while(actionTimer.milliseconds() < moveTime) {
            r.follower.manual(.4, 0, 0);
            r.follower.update();
        }
        r.follower.manual(0,0,0);
        r.follower.update();
        stop();
    }

    @Override
    public void loop() {

    }
}
