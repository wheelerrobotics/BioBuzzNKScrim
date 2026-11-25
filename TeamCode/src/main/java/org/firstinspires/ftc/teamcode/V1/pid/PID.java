package org.firstinspires.ftc.teamcode.V1.pid;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.util.function.Consumer;
import java.util.function.DoubleUnaryOperator;
import java.util.function.ToDoubleFunction;

public class PID {
    //DcMotorSimple interface is also implemented by CRServo so you can use that too
    private final DcMotorSimple[] hardware;
    private final DoubleUnaryOperator[] filters;
    private final ElapsedTime timer;
    private double kP;
    private double kI;
    private double kD;
    private double integral;
    private double lastError;
    private double lastPower;

    public PID(double kP, double kI, double kD, DoubleUnaryOperator[] filters, DcMotorSimple... hardware) {
        this.hardware = hardware;
        if (hardware.length == 0) {
            throw new IllegalArgumentException("Must pass initialized hardware");
        }
        this.filters = filters;
        timer = new ElapsedTime();
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        integral = 0;
        lastError = 0;
    }

    private void forHardware(Consumer<DcMotorSimple> action) { //calls method on all hardware
        for (int i = 0; i < hardware.length;) {
            action.accept(hardware[i]);
            i++;
        }
    }

    private double applyFilters(double power) {
        for (int i = 0; i < filters.length;) {
            power = filters[i].applyAsDouble(power);
            i++;
        }
        return power;
    }

    public void updateFilter(DoubleUnaryOperator nFilter, int index) {
        filters[index] = nFilter;
    }

    public void off() {
        forHardware(dcMotorSimple -> dcMotorSimple.setPower(lastPower));
    }
    
    public void update(double target, double current) {
        double deltaTime = timer.seconds();
        timer.reset();

        double error = target - current;

        integral += error * deltaTime;
        double derivative = (error - lastError) / deltaTime;

        // pid calculation
        double power = kP * error
                + kI * integral
                + kD * derivative;

        lastError = error;

        power = applyFilters(power);

        if (power == lastPower) return;

        double lastPower = power;
        forHardware(dcMotorSimple -> dcMotorSimple.setPower(lastPower));
    }

    public void update(double target, ToDoubleFunction<DcMotorEx> producer) throws IllegalArgumentException {
        double current = 0;
        for (int i = 0; i < hardware.length;) {
            try {
                current = producer.applyAsDouble((DcMotorEx) hardware[i]);
            } catch (ClassCastException e) {
                throw new IllegalArgumentException(
                        "Hardware device was not of type DcMotorEx use update(target, current) instead"
                );
            }
        }
        current /= hardware.length;
        update(target, current);
    }

    public static PIDBuilder pid() {
        return new PIDBuilder();
    }

    public void setkP(double kP) {
        this.kP = kP;
    }

    public void setkI(double kI) {
        this.kI = kI;
    }

    public void setkD(double kD) {
        this.kD = kD;
    }
    public double getIntegral() {
        return integral;
    }

    public double getLastError() {
        return lastError;
    }

    public double getLastPower() {
        return lastPower;
    }

    public static final class Filters {

        /**
         * Clips the power in between 0 and 1, useful in a complex series of filters
         * @return A filter which can be applied
         */
        public static DoubleUnaryOperator clip() {
            return v -> Range.clip(v, 0, 1);
        }

        /**
         * If the power is below min, the power will be set to 0
         * @param min the min value before truncation
         * @return A filter which can be applied
         */
        public static DoubleUnaryOperator lowPass0(double min) {
            return v -> v < min ? 0 : v;
        }

        /**
         * If the power is above max, the power will be set to 1
         * @param max the max value before truncation
         * @return A filter which can be applied
         */
        public static DoubleUnaryOperator highPass1(double max) {
            return v -> v > max ? 1 : v;
        }

        /** 
         * MUST be updated in real time with updateFilters(filters...) 
         * or an applicable overload of update()
         @param min if the change in power is below this value, lastPower will be returned
         @param lastPower pass getLastPower()
         @return A filter which can be applied
         **/
        public DoubleUnaryOperator lowPassPowerChange(double min, double lastPower) {
            return v -> Math.abs(lastPower - v) < min ? lastPower : v;
        }
    }
}
