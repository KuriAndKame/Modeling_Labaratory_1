package org.example;

public class SimulationParametrs {
    private final double mass;
    private final double charge;
    private final double electricField;
    private final double magneticField;
    private final double initialVelocity;

    public SimulationParametrs(double mass, double charge, double electricField, double magneticField, double initialVelocity) {
        if (!Double.isFinite(mass) || mass <= 0) {
            throw new IllegalArgumentException("Масса должна быть положительным конечным числом.");
        } else {
            this.mass = mass;
            this.charge = charge;
            this.electricField = electricField;
            this.magneticField = magneticField;
            this.initialVelocity = initialVelocity;
        }
    }

    public double getMass() {
        return this.mass;
    }

    public double getCharge() {
        return this.charge;
    }

    public double getElectricField() {
        return this.electricField;
    }

    public double getMagneticField() {
        return this.magneticField;
    }

    public double getInitialVelocity() {
        return this.initialVelocity;
    }
}

