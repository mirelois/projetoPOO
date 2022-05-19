package com.poo.projeto.Command;

import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.DeviceDoesntExistException;

import java.time.LocalDate;

public class turnSmartDeviceCommand extends Command {
    String address, smartDevice;
    boolean b;

    public turnSmartDeviceCommand(LocalDate executionTime, String address, String smartDevice, boolean b) {
        this.executionTime = executionTime;
        this.address = address;
        this.smartDevice = smartDevice;
        this.b = b;
    }

    @Override
    public void execute(CommunityApp app) throws AddressDoesntExistException, DeviceDoesntExistException {
        app.turnSmartDevice(address, smartDevice, b);
    }
}
