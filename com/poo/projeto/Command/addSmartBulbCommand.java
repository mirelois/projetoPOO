package com.poo.projeto.Command;

import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;

import java.time.LocalDate;

public class addSmartBulbCommand extends Command {
    String divisionName, tone, diameter, baseconsumption;

    public addSmartBulbCommand(LocalDate executionTime, String divisionName, String tone, String diameter, String baseconsumption) {
        this.executionTime = executionTime;
        this.divisionName = divisionName;
        this.tone = tone;
        this.diameter = diameter;
        this.baseconsumption = baseconsumption;
    }

    public void execute(CommunityApp app) throws ProviderAlreadyExistsException, AddressDoesntExistException {
        app.addSmartBulb(tone, diameter, baseconsumption);
    }
}
