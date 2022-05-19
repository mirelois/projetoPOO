package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.*;

import java.time.LocalDate;

public abstract class Command {
    private LocalDate executionTime;

    public Command(LocalDate executionTime){
        this.executionTime = executionTime;
    }

    public LocalDate getExecutionTime(){
        return this.executionTime;
    }

    private void setExecutionTime(LocalDate executionTime) {
        this.executionTime = executionTime;
    }

    public abstract void execute(Community community) throws AddressAlreadyExistsException, ProviderDoesntExistException, ProviderAlreadyExistsException, DivisionAlreadyExistsException, AddressDoesntExistException, DeviceDoesntExistException, DivisionDoesntExistException;


}
