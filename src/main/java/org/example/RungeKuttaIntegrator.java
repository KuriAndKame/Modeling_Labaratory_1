
package org.example;

public class RungeKuttaIntegrator {

    public void integrateStep(
            Particle particle,
            double electricField,
            double magneticField,
            double dt) {

        if (particle == null) {
            throw new IllegalArgumentException("Particle cannot be null");
        }

        if (!Double.isFinite(dt) || dt <= 0) {
            throw new IllegalArgumentException("Invalid time step");
        }

        double mass = particle.getMass();
        double charge = particle.getCharge();

        if (!Double.isFinite(mass) || mass <= 0) {
            throw new IllegalArgumentException("Invalid mass");
        }

        if (!Double.isFinite(electricField) ||
                !Double.isFinite(magneticField) ||
                !Double.isFinite(charge)) {
            throw new IllegalArgumentException("Invalid field or charge");
        }

        double[] state = {
                particle.getX(),
                particle.getY(),
                particle.getZ(),
                particle.getVx(),
                particle.getVy(),
                particle.getVz()
        };

        // k1
        double[] k1 = derivative(
                state, charge, mass, electricField, magneticField
        );

        // k2
        double[] temp = new double[6];

        for (int i = 0; i < 6; i++) {
            temp[i] = state[i] + dt * k1[i] / 2.0;
        }

        double[] k2 = derivative(
                temp, charge, mass, electricField, magneticField
        );

        // k3
        for (int i = 0; i < 6; i++) {
            temp[i] = state[i] + dt * k2[i] / 2.0;
        }

        double[] k3 = derivative(
                temp, charge, mass, electricField, magneticField
        );

        // k4
        for (int i = 0; i < 6; i++) {
            temp[i] = state[i] + dt * k3[i];
        }

        double[] k4 = derivative(
                temp, charge, mass, electricField, magneticField
        );

        // Вычисление нового состояния
        double[] result = new double[6];

        for (int i = 0; i < 6; i++) {
            result[i] = state[i] + dt / 6.0 *
                    (k1[i] + 2.0 * k2[i]
                            + 2.0 * k3[i] + k4[i]);

            if (!Double.isFinite(result[i])) {
                throw new ArithmeticException(
                        "Particle state became non-finite"
                );
            }
        }

        particle.updateState(
                result[0], result[1], result[2],
                result[3], result[4], result[5]
        );
    }

    private double[] derivative(
            double[] state,
            double charge,
            double mass,
            double electricField,
            double magneticField) {

        double vx = state[3];
        double vy = state[4];
        double vz = state[5];

        double ax = charge * magneticField * vy / mass;

        double ay = charge *
                (electricField - magneticField * vx) / mass;

        double az = 0.0;

        return new double[] {
                vx,
                vy,
                vz,
                ax,
                ay,
                az
        };
    }
}
