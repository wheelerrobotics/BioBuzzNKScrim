package org.firstinspires.ftc.teamcode.V1;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Configurable
public class Transfer {
    private final DcMotor transferL;
    private final DcMotor transferR;
    private final CRServo transferServo; //melonbotics push up servo
    private final DcMotor intake;
    private final Servo flower;
    public static double flowerIn = 0.15;
    public static double flowerOut = 0.66;
    public Transfer(HardwareMap hardwareMap) {
        //init hardware
        transferL = hardwareMap.get(DcMotor.class, "transferl");
        transferR = hardwareMap.get(DcMotor.class, "transferr");
        transferServo = hardwareMap.get(CRServo.class, "tservo");
        intake = hardwareMap.get(DcMotor.class, "intake");
        flower = hardwareMap.get(Servo.class, "flower");


        transferServo.setDirection(DcMotorSimple.Direction.REVERSE); //directions
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        transferR.setDirection(DcMotorSimple.Direction.REVERSE);
        transferL.setDirection(DcMotorSimple.Direction.FORWARD);
        off(); //make sure ts is off at init
    }

    public void run() { //go brr
        transferL.setPower(0.4);
        transferR.setPower(0.4);
        transferServo.setPower(1);
        intake.setPower(1);
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
