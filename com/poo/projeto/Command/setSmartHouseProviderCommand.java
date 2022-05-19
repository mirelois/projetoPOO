package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;

import java.time.LocalDate;

public class setSmartHouseProviderCommand extends Command {
    String address, provider;

    public setSmartHouseProviderCommand(LocalDate executionTime, String address, String provider) {
        super(executionTime);
        this.address = address;
        this.provider = provider;
    }

    @Override
    public void execute(Community app) throws AddressDoesntExistException, ProviderDoesntExistException {
        app.setSmartHouseProvider(address, provider);
    }
}
