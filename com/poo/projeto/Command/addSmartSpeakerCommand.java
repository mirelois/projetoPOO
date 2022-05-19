package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;
import com.poo.projeto.SmartHouse.SmartSpeaker;

import java.time.LocalDate;

public class addSmartSpeakerCommand extends Command {
    private String divisionName, address;
    private SmartSpeaker smartSpeaker;

    public addSmartSpeakerCommand(LocalDate executionTime, String divisionName, String address, SmartSpeaker smartSpeaker) {
        super(executionTime);
        this.divisionName = divisionName;
        this.address = address;
        this.smartSpeaker = smartSpeaker;
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

    private SmartSpeaker getSmartSpeaker() {
        return smartSpeaker;
    }

    private void setSmartSpeaker(SmartSpeaker smartSpeaker) {
        this.smartSpeaker = smartSpeaker;
    }

    public void execute(Community community) throws ProviderAlreadyExistsException, AddressDoesntExistException {
        community.addSmartDevice(this.address, this.divisionName, this.smartSpeaker);
    }
}
