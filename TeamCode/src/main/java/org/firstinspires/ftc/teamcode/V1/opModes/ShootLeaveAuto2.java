package org.firstinspires.ftc.teamcode.V1.opModes;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.V1.Robot;

@Autonomous
@Configurable
public class ShootLeaveAuto2 extends OpMode {

    private Robot r;
    private ElapsedTime shootTimer;
    public int shootTime = 1000;
    private ElapsedTime actionTimer;
    public static int leaveMoveTime = 300;
    public static int leaveWaitTime = 2000;

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
        while (actionTimer.milliseconds()<leaveWaitTime) {
            r.shooter.update();
        }

        r.shooter.update();
        shootTimer.reset();
        while (shootTimer.milliseconds() < shootTime) {
            r.shooter.update();
            r.shooter.out();
            r.transfer.run();
        }
        r.shooter.update();
        shootTimer.reset();
        while (shootTimer.milliseconds() < shootTime) {
            r.shooter.update();
            r.transfer.off();
        }
        r.shooter.update();
        shootTimer.reset();
        while (shootTimer.milliseconds() < shootTime) {
            r.shooter.update();
            r.transfer.run();
        }


        r.transfer.off();
        r.shooter.in();

        actionTimer.reset();
        while(actionTimer.milliseconds() < leaveMoveTime) {
            r.follower.manual(.4, 0, 0);
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
