package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;
import com.poo.projeto.SmartHouse.SmartCamera;

import java.time.LocalDate;

public class addSmartCameraCommand extends Command {
    private String divisionName, address;

    private SmartCamera smartCamera;

    public addSmartCameraCommand(LocalDate executionTime, String divisionName, String address, SmartCamera smartCamera) {
        super(executionTime);
        this.divisionName = divisionName;
        this.address = address;
        this.smartCamera = smartCamera;
    }

    private String getDivisionName() {
        return divisionName;
    }

    private void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    private String getAddress() {
        return address;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    private SmartCamera getSmartCamera() {
        return smartCamera;
    }

    private void setSmartCamera(SmartCamera smartCamera) {
        this.smartCamera = smartCamera;
    }

    public void execute(Community community) throws ProviderAlreadyExistsException, AddressDoesntExistException {
        community.addSmartDevice(this.address, this.divisionName, smartCamera);
    }
}
