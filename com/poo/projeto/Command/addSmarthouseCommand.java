package com.poo.projeto.Command;

import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.AddressAlreadyExistsException;

import java.time.LocalDate;

public class addSmarthouseCommand extends Command {
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
