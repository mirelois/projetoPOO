package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.DailyCostAlgorithm.DailyCostAlgorithm;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;

import java.time.LocalDate;

public class setProviderAlgorthmCommand extends Command {
    String providerName;
    DailyCostAlgorithm algorithm;

    public setProviderAlgorthmCommand(LocalDate executionTime, String providerName, DailyCostAlgorithm algorithm) {
        super(executionTime);
        this.providerName = providerName;
        this.algorithm = algorithm;
    }

    public void execute(Community community) throws ProviderDoesntExistException {
        community.setProviderAlgorithm(providerName, algorithm);
    }
}
