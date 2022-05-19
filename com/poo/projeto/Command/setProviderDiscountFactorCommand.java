package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.*;

import java.time.LocalDate;

public class setProviderDiscountFactorCommand extends Command {
    String providerName;
    Double discountFactor;

    public setProviderDiscountFactorCommand(LocalDate executionTime, String providerName, Double discountFactor) {
        super(executionTime);
        this.providerName = providerName;
        this.discountFactor = discountFactor;
    }

    @Override
    public void execute(Community community) throws DivisionAlreadyExistsException, ProviderDoesntExistException {
        community.setProviderDiscountFactor(providerName, discountFactor);
    }
}

