package com.poo.projeto;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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
        return this.devices.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, valor -> valor.getValue().clone()));
    }

    public void setDevices(Map<String, SmartDevice> devices) {
        this.devices = devices.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, valor -> valor.getValue().clone()));
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

    public void interact(Consumer<SmartDevice> smartDeviceConsumer){
        this.devices.values().forEach(devices -> smartDeviceConsumer.accept(devices));
    }

    //public void turnOff(){
    //    for(SmartDevice smartDevice: this.devices.values()){
    //        smartDevice.turnOff();
    //    }
    //}
//
    //public void turnOn(){
    //    for(SmartDevice smartDevice: this.devices.values()){
    //        smartDevice.turnOn();
    //    }
    //}
}
