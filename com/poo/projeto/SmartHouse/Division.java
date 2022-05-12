package com.poo.projeto.SmartHouse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Division {
    private String name;
    private Map<String, SmartDevice> devices;

    public Division(){
        this.name = "";
        this.devices = new HashMap<>();
    }

    public Division(String name, Map<String, SmartDevice> devices){
        this.name = name;
        //this.devices = devices.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, valor -> valor.getValue().clone()));
        this.setDevices(devices);
    }

    public Division(Division division){
        this.name = division.getName();
        this.devices = division.getDevices();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, SmartDevice> getDevices() {
        return this.devices.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void setDevices(Map<String, SmartDevice> devices) {
        this.devices = devices.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Division clone(){
        return new Division(this);
    }

    @Override
    public String toString() { // default toString
        return "Division{" +
                "name='" + name + '\'' +
                ", devices=" + devices +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Division division = (Division) o;
        return Objects.equals(name, division.name) && Objects.equals(devices, division.devices);
    }

    public void turnDevice(String id, Consumer<SmartDevice> smartDeviceConsumer){
        smartDeviceConsumer.accept(this.devices.get(id));
    }

    public void interact(Consumer<SmartDevice> smartDeviceConsumer){
        this.devices.values().forEach(smartDeviceConsumer); // devices -> smartDeviceConsumer.accept(devices)
    }

    public Double divisonTotalConsumption(){
        double total = 0.0;
        for(SmartDevice smartDevice: this.devices.values())
            total += smartDevice.dailyConsumption();

        return total;
        //return this.devices.values().stream().map(SmartDevice::dailyConsumption).reduce(0.0, Double::sum); versão geek mas ineficiente
    }

    public void addDevice(SmartDevice smartDevice){
        this.devices.put(smartDevice.getID(), smartDevice);
    }

    public int numberOfDevices(){
        return this.devices.values().size();
    }

}
