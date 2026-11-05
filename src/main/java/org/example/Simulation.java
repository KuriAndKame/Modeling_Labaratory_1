package org.example;

public class Simulation {
    private final SimulationParametrs params;
    private final Particle particle;
    private final RungeKuttaIntegrator integrator;

    public Simulation(SimulationParametrs params) {
        if (params == null) {
            throw new IllegalArgumentException("Параметры не могут быть null");
        } else {
            this.params = params;
        }
        this.integrator = new RungeKuttaIntegrator();
        this.particle = new Particle(params.getMass(), params.getCharge(), params.getInitialVelocity());
    }

    public void step(double dt) {
        if(!Double.isFinite(dt) || dt <= 0) {
            throw new IllegalArgumentException("Некорректный временной интервал");
        }

        double mass = params.getMass();
        double magneticFrequency = Math.abs(
                params.getCharge() * params.getMagneticField() / mass
        );

        double maxStep = dt;

        if (magneticFrequency >  0){
            maxStep  = Math.max(
                    dt,
                    0.05 / magneticFrequency
            );
        }

        double remaining = dt;

        while (remaining > 0){
            double stepSize = Math.min(
                    remaining,
                    maxStep
            );

            integrator.integrateStep(
                    particle,
                    params.getElectricField(),
                    params.getMagneticField(),
                    stepSize
            );

            remaining -= stepSize;
        }
    }

    public Particle getParticle() {
        return this.particle;
    }

    public SimulationParametrs getParams() {
        return this.params;
    }
}