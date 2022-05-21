package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.DivisionDoesntExistException;

import java.time.LocalDate;
import java.util.Objects;

public class TurnDivisionCommand extends Command {
    private String address, division;
    private boolean b;

    public TurnDivisionCommand(LocalDate executionTime, String address, String division, boolean b) {
        super(executionTime);
        this.address = address;
        this.division = division;
        this.b = b;
    }

    public TurnDivisionCommand(TurnDivisionCommand turnDivisionCommand) {
        super(turnDivisionCommand);
        this.address = turnDivisionCommand.getAddress();
        this.division = turnDivisionCommand.getDivision();
        this.b = turnDivisionCommand.getB();
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDivision() {
        return this.division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public boolean getB() {
        return this.b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "TurnDivisionCommand{" +
                "address='" + this.address + '\'' +
                ", division='" + this.division + '\'' +
                ", b=" + this.b +
                '}';
    }

    @Override
    public TurnDivisionCommand clone(){
        return new TurnDivisionCommand(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TurnDivisionCommand that = (TurnDivisionCommand) o;
        return super.equals(that) && b == that.b && Objects.equals(address, that.address) && Objects.equals(division, that.division);
    }

    @Override
    public void execute(Community app) throws AddressDoesntExistException, DivisionDoesntExistException {
        app.turnDivision(address, division, b);
    }

}
