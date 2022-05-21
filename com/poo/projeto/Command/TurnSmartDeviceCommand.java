package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.DeviceDoesntExistException;

import java.time.LocalDate;
import java.util.Objects;

public class TurnSmartDeviceCommand extends Command {
    private String address, smartDevice;
    private boolean b;

    public TurnSmartDeviceCommand(LocalDate executionTime, String address, String smartDevice, boolean b) {
        super(executionTime);
        this.address = address;
        this.smartDevice = smartDevice;
        this.b = b;
    }

    public TurnSmartDeviceCommand(TurnSmartDeviceCommand turnSmartDeviceCommand) {
        super(turnSmartDeviceCommand);
        this.address = turnSmartDeviceCommand.getAddress();
        this.smartDevice = turnSmartDeviceCommand.getSmartDevice();
        this.b = turnSmartDeviceCommand.getB();
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSmartDevice() {
        return this.smartDevice;
    }

    public void setSmartDevice(String smartDevice) {
        this.smartDevice = smartDevice;
    }

    public boolean getB() {
        return this.b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    @Override
    public TurnSmartDeviceCommand clone(){
        return new TurnSmartDeviceCommand(this);
    }

    @Override
    public String toString() {
        return "TurnSmartDeviceCommand{" +
                "address='" + this.address + '\'' +
                ", smartDevice='" + this.smartDevice + '\'' +
                ", b=" + this.b +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TurnSmartDeviceCommand that = (TurnSmartDeviceCommand) o;
        return super.equals(that) && b == that.b && Objects.equals(address, that.address) && Objects.equals(smartDevice, that.smartDevice);
    }

    @Override
    public void execute(Community app) throws AddressDoesntExistException, DeviceDoesntExistException {
        app.turnSmartDevice(address, smartDevice, b);
    }
}
