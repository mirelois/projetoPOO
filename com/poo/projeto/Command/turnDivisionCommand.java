package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.DivisionDoesntExistException;

import java.time.LocalDate;

public class turnDivisionCommand extends Command {
    String address, division;
    boolean b;

    public turnDivisionCommand(LocalDate executionTime, String address, String division, boolean b) {
        super(executionTime);
        this.address = address;
        this.division = division;
        this.b = b;
    }

    @Override
    public void execute(Community app) throws AddressDoesntExistException, DivisionDoesntExistException {
        app.turnDivision(address, division, b);
    }
}
