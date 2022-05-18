package com.poo.projeto.Command;

import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.AddressAlreadyExistsException;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.DivisionAlreadyExistsException;

import java.time.LocalDate;

class addProviderCommand extends Command {

    private String name;

    public addProviderCommand(LocalDate executionTime, String name){
        this.executionTime = executionTime;
        this.name = name;
    }
    public void execute(CommunityApp app) throws ProviderAlreadyExistsException {
        app.addProvider(name);
    }
}

class addSmarthouseCommand extends Command {
    String name, NIF, provider;

    public addSmarthouseCommand(LocalDate executionTime, String name, String NIF, String provider) {
        this.executionTime = executionTime;
        this.name = name;
        this.NIF = NIF;
        this.provider = provider;
    }
    @Override
    public void execute(CommunityApp app) throws AddressAlreadyExistsException, ProviderDoesntExistException {
        app.addSmartHouse(name, NIF, provider);
    }
}

class addSmatBulbCommand extends Command {
    String divisionName, tone, diameter, baseconsumption;

    public addSmatBulbCommand(LocalDate executionTime, String divisionName, String tone, String diameter, String baseconsumption) {
        this.executionTime = executionTime;
        this.divisionName = divisionName;
        this.tone = tone;
        this.diameter = diameter;
        this.baseconsumption = baseconsumption;
    }

    public void execute(CommunityApp app) throws ProviderAlreadyExistsException {
        app.addSmartBulb(tone, diameter, baseconsumption);
    }
}

class addSmatSpeakerCommand extends Command {
    String divisionName, volume, brand, radio, baseconsumption;

    public addSmatSpeakerCommand(LocalDate executionTime, String divisionName, String volume, String brand, String radio, String baseconsumption) {
        this.executionTime = executionTime;
        this.divisionName = divisionName;
        this.volume = volume;
        this.brand = brand;
        this.radio = radio;
        this.baseconsumption = baseconsumption;
    }

    public void execute(CommunityApp app) throws ProviderAlreadyExistsException {
        app.addSmartSpeaker(volume, brand, radio, baseconsumption);
    }
}

class addSmatCameraCommand extends Command {
    String divisionName, resolutio, dimention, baseconsumption;

    public addSmatCameraCommand(LocalDate executionTime, String divisionName, String resolutio, String dimention, String baseconsumption) {
        this.executionTime = executionTime;
        this.divisionName = divisionName;
        this.resolutio = resolutio;
        this.dimention = dimention;
        this.baseconsumption = baseconsumption;
    }

    public void execute(CommunityApp app) throws ProviderAlreadyExistsException {
        app.addSmartCamera(resolutio, dimention, baseconsumption);
    }
}

class addDivisionCommand extends Command {
    String divisionName;

    public addDivisionCommand(LocalDate executionTime, String divisionName) {
        this.executionTime = executionTime;
        this.divisionName = divisionName;
    }

    @Override
    public void execute(CommunityApp app) throws DivisionAlreadyExistsException {
        app.addDivision(divisionName);
    }
}

class setProviderDiscountFactorCommand extends Command {
    String providerName;
    Double discountFactor;

    public setProviderDiscountFactorCommand(LocalDate executionTime, String providerName, Double discountFactor) {
        this.executionTime = executionTime;
        this.providerName = providerName;
        this.discountFactor = discountFactor;
    }

    @Override
    public void execute(CommunityApp app) throws DivisionAlreadyExistsException, ProviderDoesntExistException {
        app.setProviderDiscountFactor(providerName, discountFactor);
    }
}

class setProviderAlgorthmCommand extends Command {
    String providerName;
    int algorithm;

    public setProviderAlgorthmCommand(LocalDate executionTime, String providerName, int algorithm) {
        this.executionTime = executionTime;
        this.providerName = providerName;
        this.algorithm = algorithm;
    }

    public void execute(CommunityApp app) throws ProviderDoesntExistException {
        app.setProviderAlgorithm(providerName, algorithm);
    }
}

class setSmartHousaProviderCommand extends Command {
    String address, provider;

    public setSmartHousaProviderCommand(LocalDate executionTime, String address, String provider) {
        this.executionTime = executionTime;
        this.address = address;
        this.provider = provider;
    }

    @Override
    public void execute(CommunityApp app) throws AddressDoesntExistException, ProviderDoesntExistException {
        app.setSmartHouseProvider(address, provider);
    }
}

class turnSmartDeviceCommand extends Command {
    String address, smartDevice;
    boolean b;

    public turnSmartDeviceCommand(LocalDate executionTime, String address, String smartDevice, boolean b) {
        this.executionTime = executionTime;
        this.address = address;
        this.smartDevice = smartDevice;
        this.b = b;
    }

    @Override
    public void execute(CommunityApp app) throws AddressDoesntExistException {
        app.turnSmartDevice(address, smartDevice, b);
    }
}

class turnDivisionCommand extends Command {
    String address, division;
    boolean b;

    public turnDivisionCommand(LocalDate executionTime, String address, String division, boolean b) {
        this.executionTime = executionTime;
        this.address = address;
        this.division = division;
        this.b = b;
    }

    @Override
    public void execute(CommunityApp app) throws AddressDoesntExistException {
        app.turnDivision(address, division, b);
    }
}