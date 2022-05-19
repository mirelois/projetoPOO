package com.poo.projeto.Command;

import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.*;

import java.time.LocalDate;

public abstract class Command {
    LocalDate executionTime;

    public abstract void execute(CommunityApp app) throws AddressAlreadyExistsException, ProviderDoesntExistException, ProviderAlreadyExistsException, DivisionAlreadyExistsException, AddressDoesntExistException, DeviceDoesntExistException, DivisionDoesntExistException;

    public LocalDate getExecutionTime(){
        return this.executionTime;
    }
}
