package org.firstinspires.ftc.teamcode.V1;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Configurable
public class Transfer {
    private final DcMotor transferL;
    private final DcMotor transferR;
    private final CRServo transferServo; //melonbotics push up servo
    private final DcMotor intake;
    public Transfer(HardwareMap hardwareMap) {
        //init hardware
        transferL = hardwareMap.get(DcMotor.class, "transferl");
        transferR = hardwareMap.get(DcMotor.class, "transferr");
        transferServo = hardwareMap.get(CRServo.class, "tservo");
        intake = hardwareMap.get(DcMotor.class, "intake");


        transferServo.setDirection(DcMotorSimple.Direction.REVERSE); //directions
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        transferR.setDirection(DcMotorSimple.Direction.REVERSE);
        transferL.setDirection(DcMotorSimple.Direction.REVERSE);
        off(); //make sure ts is off at init
    }

    public void run() { //go brr
        transferL.setPower(1);
        transferR.setPower(1);
        transferServo.setPower(1);
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
}
