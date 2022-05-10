package com.poo.projeto.SmartHouse;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SmartHouse {

    private String address;
    private Map<String, String> devices; // id -> string da divisão
    private Map<String, Division> divisions; // string da divisão -> Divisão (classe)

    /**
     * Constructor for objects of class CasaInteligente
     */
    public SmartHouse() {
        this.address = "";
        this.devices = new HashMap<>();
        this.divisions = new HashMap<>();
    }

    public SmartHouse(String address, Map<String, String> devices, Map<String, Division> divisions) {
        this.address = address;
        this.devices = devices.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.divisions = divisions.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, div -> div.getValue().clone()));
    }

    public SmartHouse(SmartHouse smartHouse){
        this.address = smartHouse.getAddress();
        this.devices = smartHouse.getDevices();
        this.divisions = smartHouse.getDivisions();
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, String> getDevices() {
        return this.devices.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void setDevices(Map<String, String> devices) {
        this.devices = devices;
    }

    public Map<String, Division> getDivisions(){
        return this.divisions.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, value -> value.getValue().clone()));
    }

    public void setDivisions(Map<String, Division> divisions){
        this.divisions = divisions;
    }

    @Override
    public SmartHouse clone(){
        return new SmartHouse(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartHouse that = (SmartHouse) o;
        return Objects.equals(address, that.address) && Objects.equals(devices, that.devices) && Objects.equals(divisions, that.divisions);
    }

    @Override
    public String toString() {
        return "SmartHouse{" +
                "address='" + address + '\'' +
                ", devices=" + devices +
                ", divisions=" + divisions +
                '}';
    }

    public boolean existsDevice(String id){
        return this.devices.containsKey(id);
    }

    public boolean existsDivision(String division){
        return this.divisions.containsKey(division);
    }

    public boolean existsDivisionOfDevice(String id){
        return this.divisions.containsKey(this.devices.get(id));
    }
    public boolean deviceInDivision(String id, String division){
        return Objects.equals(this.devices.get(id), division);
    }

    public void turnDiv(String division, Consumer<SmartDevice> smartDeviceConsumer){
        this.divisions.get(division).interact(smartDeviceConsumer);
    }

    public void turnHouse(Consumer<SmartDevice> divisionConsumer){
        for(Division division: this.divisions.values()){
            turnDiv(division.getName(), divisionConsumer);
        }
    }

    public void turnDevice(String id, Consumer<SmartDevice> smartDeviceConsumer){
        this.divisions.get(this.devices.get(id)).turnDevice(id, smartDeviceConsumer);
    }

    public Double totalConsumption(){
        double total = 0.0;

        for(Division division: this.divisions.values())
            total += division.divisonTotalConsumption();

        return total;
    }

    public Double consumptionByPeriod(LocalDate start, LocalDate end){
        return 0.0;
    }

    public void addDivision(Division division){
        this.divisions.put(division.getName(), division);
    }

    public void addDeviceToDivision(SmartDevice smartDevice, String division){
        this.divisions.get(division).addDevice(smartDevice);
    }

    //public void setDeviceOn(String devCode) {
    //    this.devices.get(devCode).turnOn();
    //}
    //
    //public boolean existsDevice(String id) {
    //    return this.devices.containsKey(id);
    //}


    //public void addDevice(SmartDevice s) {
    //    this.devices.put(s.getID(), s.clone());
    //    if (!this.locations.containsKey("hall")) {
    //        this.locations.put("hall", new ArrayList<>());
    //    }
    //    this.locations.get("hall").add(s.getID());
    //}
    //
    //public SmartDevice getDevice(String s) {
    //    if (this.devices.containsKey(s))
    //        return this.devices.get(s).clone();
    //    else
    //        return null;
    //}
    //
    //
    //public void addToRoom (String s1, String s2) {
    //    if (!hasRoom(s1))
    //        addRoom(s1);
    //    this.locations.get(s1).add(s2);
    //}
    //
    //public boolean roomHasDevice (String s1, String s2) {
    //    return this.locations.containsKey(s1) && this.locations.get(s1).contains(s2);
    //}
//
    //public String getName() {
    //    return name;
    //}
//
    //public void setName(String name) {
    //    this.name = name;
    //}
//
    //public String getNif() {
    //    return nif;
    //}
//
    //public void setNif(String nif) {
    //    this.nif = nif;
    //}
}
