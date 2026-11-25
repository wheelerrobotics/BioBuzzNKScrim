package org.firstinspires.ftc.teamcode.V1;

import static org.firstinspires.ftc.teamcode.V1.pid.PID.Filters.clip;
import static org.firstinspires.ftc.teamcode.V1.pid.PID.pid;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.V1.pid.PID;

@Configurable
public class Shooter {
    private final DcMotorEx shooter;
    private final Servo hood;
    private final Servo stopper;
    private final PID pid;

    //Configurables
    public static double closePosTPS = 1390;
    public static double closePosHood = 0.08; //0.37
    public static double farPosTPS = 1510;
    public static double farPosHood = 0.21; //0.5
    public static double inPos = 0.8;
    public static double outPos = 0.5;
    public static double kP = 0.04; //strength of motor compensation
    public static double kI = 0.0000;
    public static double kD = 0;
    public static double autoTPS = 1470;

    //Variables
    public static double shooterTargetTPS; // current speed for P controller

    public Shooter(HardwareMap hardwareMap) {
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        hood = hardwareMap.get(Servo.class, "hood");
        stopper = hardwareMap.get(Servo.class, "stopper");

        shooterTargetTPS = closePosTPS; //reset tps

        in();
        close();
        pid = pid().kP(kP).addFilters(clip()).setHardware(shooter).build();
    }

    public void close() {
        setSpeed(closePosTPS); //set for close shoot pos
        hood.setPosition(closePosHood);
    }

    public void far() {
        setSpeed(farPosTPS); //set for far shoot pos
        hood.setPosition(farPosHood);
    }

    public void auto() {
        setSpeed(autoTPS);
        hood.setPosition(farPosHood);
    }

    public void in() {
        stopper.setPosition(inPos); //stopper in
    }

    public void out() {
        stopper.setPosition(outPos); //stopper out
    }

    public void off() {
        shooterTargetTPS = 0;
        shooter.setPower(0);
    }

    public void telemetry(Telemetry t) {
        t.addData("velocity", shooter.getVelocity());
        t.addData("setpoint", shooterTargetTPS);
        t.addData("error", shooterTargetTPS - shooter.getVelocity());
        t.addData("power", shooter.getPower());
        t.addData("current", shooter.getCurrent(CurrentUnit.AMPS));
    }

    private static void setSpeed(double nSpeed) {
        shooterTargetTPS = nSpeed;
    }

    public void update() {
        pid.update(shooterTargetTPS, DcMotorEx::getVelocity);
    }


}
