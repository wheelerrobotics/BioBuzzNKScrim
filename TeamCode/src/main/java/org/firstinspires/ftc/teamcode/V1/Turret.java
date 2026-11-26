package org.firstinspires.ftc.teamcode.V1;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Turret {
    private final Servo s1;
    private final Servo s2;
    private final Follower f;
    private final Telemetry t;
    private static final double servoRatio = 1/1.8/360;
    private static final double minAngleDegrees = -90;
    private static final double maxAngleDegrees = 90;
    public Turret(HardwareMap hardwareMap, Follower f, Telemetry t) {
        s1 = hardwareMap.get(Servo.class,"s1");
        s2 = hardwareMap.get(Servo.class,"s2");
        s1.setDirection(Servo.Direction.REVERSE);
        s2.setDirection(Servo.Direction.REVERSE);
        this.f = f;
        this.t = t;
    }

    public double getTurretAngle_rs() {
        double y = PoseLib.targetPose().y() - f.pose().y() ;
        double x = PoseLib.targetPose().x() - f.pose().x();
        double targetAngle_fs = Math.atan2(y,x);
        t.addData("TargetAngle_fs", targetAngle_fs);
        t.addData("heading", f.pose().heading());
        double targetAngle_rs = targetAngle_fs - f.pose().heading();
        targetAngle_rs = Math.atan2(
                Math.sin(targetAngle_rs),
                Math.cos(targetAngle_rs)
        );
        t.addData("TargetAngle_rs", targetAngle_rs);
        return targetAngle_rs; 
    }

    public void update() {
        double deg = getTurretAngle_rs();
        deg = Range.clip(
                deg,
                minAngleDegrees,
                maxAngleDegrees
        );

        double servoPos = ((deg+90) * servoRatio);
        t.addData("turnTo() servoPos", servoPos);

        s1.setPosition(servoPos);
        s2.setPosition(servoPos);
    }

}
