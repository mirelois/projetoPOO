package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.SmartHouse.Division;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.DivisionAlreadyExistsException;

import java.time.LocalDate;

public class addDivisionCommand extends Command {
    private String address;
    private Division division;

    public addDivisionCommand(LocalDate executionTime, String address, Division division) {
        super(executionTime);
        this.division = division;
    }

    @Override
    public void execute(Community community) throws DivisionAlreadyExistsException, AddressDoesntExistException {
        community.addDivision(this.address, this.division);
    }
}
