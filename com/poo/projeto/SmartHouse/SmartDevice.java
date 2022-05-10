package com.poo.projeto.SmartHouse;

import java.util.Objects;

public abstract class SmartDevice {

    private final String id;
    private boolean on;
    private double installationCost;

    public double getInstallationCost() {
        return installationCost;
    }

    public void setInstallationCost(double installationCost) {
        this.installationCost = installationCost;
    }

    /**
     * Constructor for objects of class com.poo.projeto.SmartHouse.SmartDevice
     */
    public SmartDevice() {
        this.id = "";
        this.on = false;
        this.installationCost = 0;
    }

    public SmartDevice(String s) {
        this.id = s;
        this.on = false;
        this.installationCost = 0;
    }

    public SmartDevice(String s, boolean b) {
        this.id = s;
        this.on = b;
        this.installationCost = 0;
    }

    public SmartDevice(SmartDevice d) {
        this.id = d.id;
        this.on = d.on;
        this.installationCost = d.installationCost;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartDevice that = (SmartDevice) o;
        return on == that.on &&
                Objects.equals(id, that.id);
    }
}
