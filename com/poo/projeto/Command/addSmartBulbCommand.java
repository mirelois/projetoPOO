package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;
import com.poo.projeto.SmartHouse.SmartBulb;

import java.time.LocalDate;

public class addSmartBulbCommand extends Command {
    private String divisionName, address;

    private SmartBulb smartBulb;

    public addSmartBulbCommand(LocalDate executionTime, String divisionName, String address, SmartBulb smartBulb) {
        super(executionTime);
        this.divisionName = divisionName;
        this.address = address;
        this.smartBulb = smartBulb;
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

    private SmartBulb getSmartBulb() {
        return smartBulb;
    }

    private void setSmartBulb(SmartBulb smartBulb) {
        this.smartBulb = smartBulb;
    }

    public void execute(Community community) throws ProviderAlreadyExistsException, AddressDoesntExistException {
        community.addSmartDevice(this.address, this.divisionName, this.smartBulb);
    }
}
