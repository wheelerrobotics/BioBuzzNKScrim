package org.firstinspires.ftc.teamcode.robot.hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.hardware.util.HardwareDeviceSpec;

/** Devices currently required by V1.Shooter and V1.Transfer.
 * Add drivetrain/localizer devices once pedro.Constants is configured.
 */
public final class ExpectedHardware {
    public static final HardwareDeviceSpec[] DEVICES = {
            new HardwareDeviceSpec("shooter", DcMotorEx.class, "Shooter motor"),
            new HardwareDeviceSpec("hood", Servo.class, "Shooter hood"),
            new HardwareDeviceSpec("stopper", Servo.class, "Shooter stopper"),
            new HardwareDeviceSpec("transferl", DcMotor.class, "Left transfer motor"),
            new HardwareDeviceSpec("transferr", DcMotor.class, "Right transfer motor"),
            new HardwareDeviceSpec("tservo", CRServo.class, "Transfer servo"),
            new HardwareDeviceSpec("intake", DcMotor.class, "Intake motor")
    };

    private ExpectedHardware() {}
}
