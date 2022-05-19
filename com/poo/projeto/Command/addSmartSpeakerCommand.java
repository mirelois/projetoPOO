package com.poo.projeto.Command;

import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;

import java.time.LocalDate;

public class addSmartSpeakerCommand extends Command {
    String divisionName, volume, brand, radio, baseconsumption;

    public addSmartSpeakerCommand(LocalDate executionTime, String divisionName, String volume, String brand, String radio, String baseconsumption) {
        this.executionTime = executionTime;
        this.divisionName = divisionName;
        this.volume = volume;
        this.brand = brand;
        this.radio = radio;
        this.baseconsumption = baseconsumption;
    }

    public void execute(CommunityApp app) throws ProviderAlreadyExistsException, AddressDoesntExistException {
        app.addSmartSpeaker(volume, brand, radio, baseconsumption);
    }
}
