package org.firstinspires.ftc.teamcode.V1;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

public class PIDBuilder {
    private double kP = 0;
    private double kI = 0;
    private double kD = 0;
    ArrayList<DoubleUnaryOperator> filters = new ArrayList<>();
    private DcMotorSimple[] hardware = null;

    public PIDBuilder kP(double kP) {
        this.kP = kP;
        return this;
    }

    public PIDBuilder kI(double kI) {
        this.kI = kI;
        return this;
    }

    public PIDBuilder kD(double kD) {
        this.kD = kD;
        return this;
    }

    public PIDBuilder addFilters(DoubleUnaryOperator... filters) {
        this.filters.addAll(Arrays.asList(filters));
        return this;
    }

    public PIDBuilder setHardware(DcMotorSimple... hardware) {
        this.hardware = hardware;
        return this;
    }

    public PID build() {
        return new PID(kP, kI, kD, filters.toArray(new DoubleUnaryOperator[0]), hardware);
    }
}