package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public abstract class Command implements Serializable {
    private LocalDate executionTime;

    public Command(LocalDate executionTime){
        this.executionTime = executionTime;
    }

    public Command(Command command){
        this.executionTime = command.getExecutionTime();
    }

    public LocalDate getExecutionTime(){
        return this.executionTime;
    }

    private void setExecutionTime(LocalDate executionTime) {
        this.executionTime = executionTime;
    }

    public abstract void execute(Community community) throws AddressAlreadyExistsException, ProviderDoesntExistException, ProviderAlreadyExistsException, DivisionAlreadyExistsException, AddressDoesntExistException, DeviceDoesntExistException, DivisionDoesntExistException;

    @Override
    public abstract Command clone();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(this.executionTime, command.executionTime);
    }

    @Override
    public String toString() {
        return "Command{" +
                "executionTime=" + this.executionTime +
                '}';
    }
}
