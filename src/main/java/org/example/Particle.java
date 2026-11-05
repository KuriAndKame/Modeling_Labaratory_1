package org.example;

public class Particle {
    private final double mass;
    private final double charge;
    private double x;
    private double y;
    private double z;
    private double vX;
    private double vY;
    private double vZ;

    public Particle(double mass, double charge, double vX) {
        this.mass = mass;
        this.charge = charge;
        this.x = (double)0.0F;
        this.y = (double)0.0F;
        this.z = (double)0.0F;
        this.vX = vX;
        this.vY = (double)0.0F;
        this.vZ = (double)0.0F;
    }

    public void updateState(double x, double y, double z, double vX, double vY, double vZ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vX = vX;
        this.vY = vY;
        this.vZ = vZ;
    }

    public double getMass() {
        return this.mass;
    }

    public double getCharge() {
        return this.charge;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public double getVx() {
        return this.vX;
    }

    public double getVy() {
        return this.vY;
    }

    public double getVz() {
        return this.vZ;
    }
}