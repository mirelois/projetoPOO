package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.SmartHouse.Exceptions.*;

import java.time.LocalDate;
import java.util.Objects;

public class SetBaseConsumptionCommand extends Command{

    private String address, device;
    private Double baseConsumption;

    public SetBaseConsumptionCommand(LocalDate executionDate, String address, String device, Double baseConsumption) {
        super(executionDate);
        this.address = address;
        this.device = device;
        this.baseConsumption = baseConsumption;
    }

    public SetBaseConsumptionCommand(SetBaseConsumptionCommand s) {
        super(s);
        this.address = s.getAddress();
        this.baseConsumption = s.getBaseConsumption();
        this.device = s.getDevice();
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDevice() {
        return this.device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Double getBaseConsumption() {
        return this.baseConsumption;
    }

    public void setBaseConsumption(Double baseConsumption) {
        this.baseConsumption = baseConsumption;
    }

    @Override
    public SetBaseConsumptionCommand clone(){
        return new SetBaseConsumptionCommand(this);
    }

    @Override
    public String toString() {
        return "setBaseConsumptionCommand{" +
                "address='" + this.address + '\'' +
                ", device='" + this.device + '\'' +
                ", baseConsumption=" + this.baseConsumption +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetBaseConsumptionCommand that = (SetBaseConsumptionCommand) o;
        return super.equals(that) && Objects.equals(address, that.address) && Objects.equals(device, that.device) && Objects.equals(baseConsumption, that.baseConsumption);
    }

    @Override
    public void execute(Community community) throws AddressDoesntExistException, DeviceDoesntExistException {
        community.setBaseConsumption(this.address, this.device, this.baseConsumption);
    }
}
