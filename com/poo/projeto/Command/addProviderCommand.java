package com.poo.projeto.Command;

import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;

import java.time.LocalDate;

public class addProviderCommand extends Command {

    private String name;

    public addProviderCommand(LocalDate executionTime, String name) {
        this.executionTime = executionTime;
        this.name = name;
    }

    public void execute(CommunityApp app) throws ProviderAlreadyExistsException {
        app.addProvider(name);
    }
}
