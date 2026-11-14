package org.firstinspires.ftc.teamcode.V1;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Configurable
public class Shooter {
    private final DcMotorEx shooter;
    private final Servo hood;
    private final Servo stopper;
    public static double closePosPower = 0.75; // TODO: 11/14/26
    public static double closePosHood = 0; // TODO: 11/14/26
    public static double farPosPower = 0.75; // TODO: 11/14/26
    public static double farPosHood = 0; // TODO: 11/14/26
    public static double inPos = 0.5; // TODO: 11/14/26
    public static double outPos = 0; // TODO: 11/14/26


    public Shooter(HardwareMap hardwareMap) {
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        hood = hardwareMap.get(Servo.class, "hood");
        stopper = hardwareMap.get(Servo.class, "stopper");
        in();
        close();
    }

    public void close() {
        shooter.setPower(closePosPower); //set powers for close shoot pos
        hood.setPosition(closePosHood);
    }

    public void far() {
        shooter.setPower(farPosPower); //set powers for far shoot pos
        hood.setPosition(farPosHood);
    }

    public void in() {
        stopper.setPosition(inPos); //stopper in
    }

    public void out() {
        stopper.setPosition(outPos); //stopper out
    }

}
