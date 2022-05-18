package Command;

import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.AddressAlreadyExistsException;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.DivisionAlreadyExistsException;

class addProviderCommand implements Command {
    String name;
    public addProviderCommand(String name){
        this.name = name;
    }
    public void execute(CommunityApp app) throws ProviderAlreadyExistsException {
        app.addProvider(name);
    }
}

class addSmarthouseCommand implements Command {
    String address, name, NIF, provider;
    public addSmarthouseCommand(String address, String name, String NIF, String provider) {
        this.address = address;
        this.name = name;
        this.NIF = NIF;
        this.provider = provider;
    }
    @Override
    public void execute(CommunityApp app) throws AddressAlreadyExistsException, ProviderDoesntExistException {
        app.addSmartHouse(address, name, NIF, provider);
    }
}

class addSmatBulbCommand implements Command {
    String adress, divisionName, tone, diameter, baseconsumption;

    public addSmatBulbCommand(String adress, String divisionName, String tone, String diameter, String baseconsumption) {
        this.adress = adress;
        this.divisionName = divisionName;
        this.tone = tone;
        this.diameter = diameter;
        this.baseconsumption = baseconsumption;
    }

    public void execute(CommunityApp app) throws ProviderAlreadyExistsException {
        app.addSmartBulb(adress, divisionName, tone, diameter, baseconsumption);
    }
}

class addSmatSpeakerCommand implements Command {
    String adress, divisionName, volume, brand, radio, baseconsumption;

    public addSmatSpeakerCommand(String adress, String divisionName, String volume, String brand, String radio, String baseconsumption) {
        this.adress = adress;
        this.divisionName = divisionName;
        this.volume = volume;
        this.brand = brand;
        this.radio = radio;
        this.baseconsumption = baseconsumption;
    }

    public void execute(CommunityApp app) throws ProviderAlreadyExistsException {
        app.addSmartSpeaker(adress, divisionName, volume, brand, radio, baseconsumption);
    }
}

class addSmatCameraCommand implements Command {
    String adress, divisionName, resolutio, dimention, baseconsumption;

    public addSmatCameraCommand(String adress, String divisionName, String resolutio, String dimention, String baseconsumption) {
        this.adress = adress;
        this.divisionName = divisionName;
        this.resolutio = resolutio;
        this.dimention = dimention;
        this.baseconsumption = baseconsumption;
    }

    public void execute(CommunityApp app) throws ProviderAlreadyExistsException {
        app.addSmartCamera(adress, divisionName, resolutio, dimention, baseconsumption);
    }
}

class addDivisionCommand implements Command {
    String address, divisionName;

    public addDivisionCommand(String address, String divisionName) {
        this.address = address;
        this.divisionName = divisionName;
    }

    @Override
    public void execute(CommunityApp app) throws DivisionAlreadyExistsException {
        app.addDivision(address, divisionName);
    }
}

class setProviderDiscountFactorCommand implements Command {
    String providerName;
    Double discountFactor;

    public setProviderDiscountFactorCommand(String providerName, Double discountFactor) {
        this.providerName = providerName;
        this.discountFactor = discountFactor;
    }

    @Override
    public void execute(CommunityApp app) throws DivisionAlreadyExistsException, ProviderDoesntExistException {
        app.setProviderDiscountFactor(providerName, discountFactor);
    }
}

class setProviderAlgorthmCommand implements Command {
    String providerName;
    int algorithm;

    public setProviderAlgorthmCommand(String providerName, int algorithm) {
        this.providerName = providerName;
        this.algorithm = algorithm;
    }

    public void execute(CommunityApp app){
        app.setProviderAlgorithm(providerName, algorithm);
    }
}

class setSmartHousaProviderCommand implements Command{
    String address, provider;

    public setSmartHousaProviderCommand(String address, String provider) {
        this.address = address;
        this.provider = provider;
    }

    @Override
    public void execute(CommunityApp app) throws AddressDoesntExistException, ProviderDoesntExistException {
        app.setSmartHouseProvider(address, provider);
    }
}

class turnSmartDeviceCommand implements Command{
    String address, smartDevice;
    boolean b;

    public turnSmartDeviceCommand(String address, String smartDevice, boolean b) {
        this.address = address;
        this.smartDevice = smartDevice;
        this.b = b;
    }

    @Override
    public void execute(CommunityApp app) throws AddressDoesntExistException {
        app.turnSmartDevice(address, smartDevice, b);
    }
}

class turnDivisionCommand implements Command{
    String address, division;
    boolean b;

    public turnDivisionCommand(String address, String division, boolean b) {
        this.address = address;
        this.division = division;
        this.b = b;
    }

    @Override
    public void execute(CommunityApp app) throws AddressDoesntExistException {
        app.turnDivision(address, division, b);
    }
}