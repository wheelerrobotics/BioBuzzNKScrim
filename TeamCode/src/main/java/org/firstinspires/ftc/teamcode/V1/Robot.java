package org.firstinspires.ftc.teamcode.V1;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

public class Robot {
    public final Follower follower;
    public final Shooter shooter;
    public final Transfer transfer;
    public Robot(OpMode opMode) { //ts basic
        transfer = new Transfer(opMode.hardwareMap);
        shooter = new Shooter(opMode.hardwareMap);
        follower = Constants.create(opMode.hardwareMap);
    }

}
