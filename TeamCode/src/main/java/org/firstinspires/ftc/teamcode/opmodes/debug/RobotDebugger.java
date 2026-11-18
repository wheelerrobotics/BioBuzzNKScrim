package org.firstinspires.ftc.teamcode.opmodes.debug;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.opmodes.debug.RobotDebuggerVals.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.robot.hardware.HardwareNames;

/**
 * Standalone drivetrain control panel, independent of the unfinished V1 follower.
 * Hardware names and directions are inherited from BioBuzz26-27; verify them
 * against the NKScrim robot configuration before enabling the drivetrain.
 */
@Configurable
@TeleOp(name = "Robot Debugger", group = "Debug")
public class RobotDebugger extends OpMode {
    private TelemetryManager telemetryM;

    public static boolean MASTER_ENABLE = true;

    private DcMotorEx frontLeft, frontRight, backLeft, backRight;

    @Override
    public void init() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        frontLeft = hardwareMap.get(DcMotorEx.class, HardwareNames.FRONT_LEFT_DRIVE);
        frontRight = hardwareMap.get(DcMotorEx.class, HardwareNames.FRONT_RIGHT_DRIVE);
        backLeft = hardwareMap.get(DcMotorEx.class, HardwareNames.BACK_LEFT_DRIVE);
        backRight = hardwareMap.get(DcMotorEx.class, HardwareNames.BACK_RIGHT_DRIVE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        for (DcMotorEx motor : new DcMotorEx[]{frontLeft, frontRight, backLeft, backRight}) {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        DriveDebug.ENABLED = false;
        stopDrivetrain();

        telemetryM.addLine("Robot Debugger initialized");
        telemetryM.addLine("Enable MASTER_ENABLE and the desired subsystem in Panels");
        telemetryM.update(telemetry);
    }

    @Override
    public void loop() {
        updateDrivetrain();

        telemetryM.addData("Master Enable", MASTER_ENABLE);
        telemetryM.addLine("================================");
        telemetryM.update(telemetry);
    }

    private void updateDrivetrain() {
        if (!MASTER_ENABLE || !DriveDebug.ENABLED) {
            stopDrivetrain();
            return;
        }

        if (DriveDebug.CONTROLLER) {
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double rotation = gamepad1.right_stick_x;

            double scale = Math.max(1.0, Math.abs(y) + Math.abs(x) + Math.abs(rotation));
            frontLeft.setPower((y + x + rotation) / scale);
            backLeft.setPower((y - x + rotation) / scale);
            frontRight.setPower((y - x - rotation) / scale);
            backRight.setPower((y + x - rotation) / scale);
        } else {
            frontLeft.setPower(DriveDebug.FRONT_LEFT_POWER);
            frontRight.setPower(DriveDebug.FRONT_RIGHT_POWER);
            backLeft.setPower(DriveDebug.BACK_LEFT_POWER);
            backRight.setPower(DriveDebug.BACK_RIGHT_POWER);
        }
    }
    private void stopDrivetrain() {
        for (DcMotorEx motor : new DcMotorEx[]{frontLeft, frontRight, backLeft, backRight}) {
            if (motor != null) motor.setPower(0);
        }
    }

    @Override
    public void stop() {
        stopDrivetrain();
    }
}
