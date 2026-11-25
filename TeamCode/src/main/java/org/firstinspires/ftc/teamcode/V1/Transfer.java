package org.firstinspires.ftc.teamcode.V1;

import static org.firstinspires.ftc.teamcode.V1.pid.PID.pid;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.V1.pid.PID;

@Configurable
public class Transfer {
    private final DcMotorEx transferL;
    private final DcMotorEx transferR;
    private final CRServo transferServo; //melonbotics push up servo
    private final DcMotorEx intake;
    private final Servo flower;
    private final PID pid;
    public static double powerTarget = 2;
    public static double flowerIn = 0.15;
    public static double flowerOut = 0.66;
    public Transfer(HardwareMap hardwareMap) {
        //init hardware
        transferL = hardwareMap.get(DcMotorEx.class, "transferl");
        transferR = hardwareMap.get(DcMotorEx.class, "transferr");
        transferServo = hardwareMap.get(CRServo.class, "tservo");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        flower = hardwareMap.get(Servo.class, "flower");


        transferServo.setDirection(DcMotorSimple.Direction.REVERSE); //directions
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        transferR.setDirection(DcMotorSimple.Direction.REVERSE);
        transferL.setDirection(DcMotorSimple.Direction.FORWARD);
        off(); //make sure ts is off at init
        pid = pid().kP(0.5).setHardware(intake, transferL, transferR).build();
    }

    public void run() { //go brr
        transferL.setPower(0.4);
        transferR.setPower(0.4);
        transferServo.setPower(1);
        intake.setPower(1);
    }

    public void shoot() {
        pid.update(powerTarget, motor -> motor.getCurrent(CurrentUnit.AMPS));
    }

    public void runSlow() {
        transferL.setPower(0.4);
        transferR.setPower(0.4);
        intake.setPower(1);
    }

    public void off() { //stop
        transferL.setPower(0);
        transferR.setPower(0);
        transferServo.setPower(0);
        intake.setPower(0);
        pid.off();
    }

    public void back() { //go brr but backwards
        transferL.setPower(-1);
        transferR.setPower(-1);
        transferServo.setPower(-1);
        intake.setPower(-1);
    }

    public void intakeBack() { //intake out
        intake.setPower(-1);
    }

    //flower
    public void flowerOut() {
        flower.setPosition(flowerOut);
    }

    public void flowerIn() {
        flower.setPosition(flowerIn);
    }
}
