package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.Provider.Provider;

import java.time.LocalDate;

public class addProviderCommand extends Command {

    private Provider provider;

    public addProviderCommand(LocalDate executionTime, Provider provider) {
        super(executionTime);
        this.provider = provider;
    }

    public void execute(Community community) throws ProviderAlreadyExistsException {
        community.addProvider(this.provider);
    }
}
