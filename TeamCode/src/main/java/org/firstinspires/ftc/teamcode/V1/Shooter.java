package org.firstinspires.ftc.teamcode.V1;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import com.qualcomm.robotcore.util.ElapsedTime;
@Configurable
public class Shooter {
    private final DcMotorEx shooter;
    private final Servo hood;
    private final Servo stopper;
    private final ElapsedTime timer;

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



    private double integral = 0;
    private double lastError = 0;

    //Variables
    public static double shooterTargetTPS; // current speed for P controller

    public Shooter(HardwareMap hardwareMap) {
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        hood = hardwareMap.get(Servo.class, "hood");
        stopper = hardwareMap.get(Servo.class, "stopper");
        timer = new ElapsedTime();

        shooterTargetTPS = closePosTPS; //reset tps

        integral = 0;
        lastError = 0;

        in();
        close();
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

        integral = 0;
        lastError = 0;
        timer.reset();
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
        double deltaTime = timer.seconds();
        timer.reset();

        double error = shooterTargetTPS - shooter.getVelocity();

        integral += error * deltaTime;
        double derivative = (error - lastError) / deltaTime;

        // pid calculation
        double power = kP * error
                + kI * integral
                + kD * derivative;

        power = Range.clip(power, 0, 1);

        lastError = error;
        shooter.setPower(power);
    }

}
