package com.poo.projeto.Command;

import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;

import java.time.LocalDate;

public class setProviderAlgorthmCommand extends Command {
    String providerName;
    int algorithm;

    public setProviderAlgorthmCommand(LocalDate executionTime, String providerName, int algorithm) {
        this.executionTime = executionTime;
        this.providerName = providerName;
        this.algorithm = algorithm;
    }

    public void execute(CommunityApp app) throws ProviderDoesntExistException {
        app.setProviderAlgorithm(providerName, algorithm);
    }
}
