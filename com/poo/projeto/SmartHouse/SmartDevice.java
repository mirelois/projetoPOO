package com.poo.projeto.SmartHouse;

import java.io.Serializable;
import java.util.Objects;

public abstract class SmartDevice implements Serializable {

    private final String id;
    private boolean on;
    private double installationCost;
    private double baseConsumption;

    public SmartDevice() {
        this.id = "";
        this.on = false;
        this.installationCost = 0;
        this.baseConsumption = 0;
    }

    public SmartDevice(String s) {
        this.id = s;
        this.on = false;
        this.installationCost = 1;
        this.baseConsumption = 1;
    }

    public SmartDevice(String s, boolean b) {
        this.id = s;
        this.on = b;
        this.installationCost = 10;
        this.baseConsumption = 1;
    }

    public SmartDevice(String id, double installationCost) {
        this.id = id;
        this.on = false;
        this.installationCost = installationCost;
        this.baseConsumption = 1;
    }

    public SmartDevice(double baseConsumption, String s) {
        this.id = s;
        this.on = false;
        this.installationCost = 100;
        this.baseConsumption = baseConsumption;
    }

    public SmartDevice(String s, double installationCost, double baseConsumption) {
        this.id = s;
        this.on = false;
        this.installationCost = installationCost;
        this.baseConsumption = baseConsumption;
    }

    public SmartDevice(String s, boolean b, double installationCost) {
        this.id = s;
        this.on = b;
        this.installationCost = installationCost;
        this.baseConsumption = 1;
    }

    public SmartDevice(String s, boolean b, double installationCost, double baseConsumption) {
        this.id = s;
        this.on = b;
        this.installationCost = installationCost;
        this.baseConsumption = baseConsumption;
    }

    public SmartDevice(SmartDevice d) {
        this.id = d.id;
        this.on = d.on;
        this.installationCost = d.installationCost;
        this.baseConsumption = d.baseConsumption;
    }

    public void turnOn() {
        this.on = true;
    }
    
    public void turnOff() {
        this.on = false;
    }
    
    public boolean getOn() {return this.on;}
    
    public void setOn(boolean b) {this.on = b;}
    
    public String getID() {return this.id;}

    public abstract SmartDevice clone();

    public abstract double dailyConsumption();

    public double getInstallationCost() {
        return installationCost;
    }

    public void setInstallationCost(double installationCost) {
        this.installationCost = installationCost;
    }

    public double getBaseConsumption() {
        return baseConsumption;
    }

    public void setBaseConsumption(double baseConsumption) {
        this.baseConsumption = baseConsumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartDevice that = (SmartDevice) o;
        return on == that.on &&
                Objects.equals(id, that.id);
    }
}
