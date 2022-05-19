package com.poo.projeto.Command;

import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.DivisionAlreadyExistsException;

import java.time.LocalDate;

public class addDivisionCommand extends Command {
    String divisionName;

    public addDivisionCommand(LocalDate executionTime, String divisionName) {
        this.executionTime = executionTime;
        this.divisionName = divisionName;
    }

    @Override
    public void execute(CommunityApp app) throws DivisionAlreadyExistsException, AddressDoesntExistException {
        app.addDivision(divisionName);
    }
}
