package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.DivisionAlreadyExistsException;

import java.time.LocalDate;
import java.util.Objects;

public class SetProviderDiscountFactorCommand extends Command {
    private String providerName;
    private Double discountFactor;

    public SetProviderDiscountFactorCommand(LocalDate executionTime, String providerName, Double discountFactor) {
        super(executionTime);
        this.providerName = providerName;
        this.discountFactor = discountFactor;
    }

    public SetProviderDiscountFactorCommand(SetProviderDiscountFactorCommand s) {
        super(s);
        this.providerName = s.getProviderName();
        this.discountFactor = s.getDiscountFactor();
    }

    public String getProviderName() {
        return this.providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Double getDiscountFactor() {
        return this.discountFactor;
    }

    public void setDiscountFactor(Double discountFactor) {
        this.discountFactor = discountFactor;
    }

    @Override
    public SetProviderDiscountFactorCommand clone(){
        return new SetProviderDiscountFactorCommand(this);
    }

    @Override
    public String toString() {
        return "SetProviderDiscountFactorCommand{" +
                "providerName='" + this.providerName + '\'' +
                ", discountFactor=" + this.discountFactor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SetProviderDiscountFactorCommand that = (SetProviderDiscountFactorCommand) o;
        return super.equals(that) && Objects.equals(providerName, that.providerName) && Objects.equals(discountFactor, that.discountFactor);
    }

    @Override
    public void execute(Community community) throws DivisionAlreadyExistsException, ProviderDoesntExistException {
        community.setProviderDiscountFactor(providerName, discountFactor);
    }
}

