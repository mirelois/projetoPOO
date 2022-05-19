package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.AddressAlreadyExistsException;
import com.poo.projeto.SmartHouse.SmartHouse;

import java.time.LocalDate;

public class addSmartHouseCommand extends Command {
    private SmartHouse smartHouse;
    private String provider;

    public addSmartHouseCommand(LocalDate executionTime, SmartHouse smartHouse, String provider) {
        super(executionTime);
        this.smartHouse = smartHouse;
        this.provider = provider;
    }

    private SmartHouse getSmartHouse() {
        return smartHouse;
    }

    private void setSmartHouse(SmartHouse smartHouse) {
        this.smartHouse = smartHouse;
    }

    private String getProvider() {
        return provider;
    }

    private void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public void execute(Community community) throws AddressAlreadyExistsException, ProviderDoesntExistException {
        community.addSmartHouse(smartHouse, provider);
    }

}
