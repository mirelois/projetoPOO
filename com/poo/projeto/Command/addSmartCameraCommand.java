package com.poo.projeto.Command;

import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;

import java.time.LocalDate;

public class addSmartCameraCommand extends Command {
    String divisionName, resolutio, dimention, baseconsumption;

    public addSmartCameraCommand(LocalDate executionTime, String divisionName, String resolutio, String dimention, String baseconsumption) {
        this.executionTime = executionTime;
        this.divisionName = divisionName;
        this.resolutio = resolutio;
        this.dimention = dimention;
        this.baseconsumption = baseconsumption;
    }

    public void execute(CommunityApp app) throws ProviderAlreadyExistsException, AddressDoesntExistException {
        app.addSmartCamera(resolutio, dimention, baseconsumption);
    }
}
