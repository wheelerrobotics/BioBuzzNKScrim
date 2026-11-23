package org.firstinspires.ftc.teamcode.V1;

import static org.firstinspires.ftc.teamcode.V1.ShootLeaveAuto.shootTime;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
@Configurable
public class ShootParkAuto extends OpMode {

    private Robot r;
    private ElapsedTime shootTimer;
    private ElapsedTime actionTimer;
    public static int parkMoveTime = 2500;
    public static int parkWaitTime = 16000;

    @Override
    public void init() {
        r = new Robot(this);
        shootTimer = new ElapsedTime();
        actionTimer = new ElapsedTime();
        r.shooter.in();
        r.transfer.flowerIn();

    }

    @Override
    public void start() {
        r.shooter.auto();
        r.shooter.in();

        r.shooter.update();

        actionTimer.reset();
        while (actionTimer.milliseconds()<parkWaitTime) {
            r.shooter.update();
        }

        r.shooter.update();
        shootTimer.reset();
        while (shootTimer.milliseconds() < shootTime) {
            r.shooter.update();
            r.shooter.out();
            r.transfer.run();
        }

        r.transfer.off();
        r.shooter.in();

        actionTimer.reset();
        while(actionTimer.milliseconds() < parkMoveTime) {
            r.follower.manual(0.32, -0.5, 0);
            r.follower.update();
        }
        r.follower.manual(0,0,0);
        r.follower.update();
        stop();
    }

    @Override
    public void loop() {
        r.shooter.update();
    }
}
