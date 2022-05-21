package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.DailyCostAlgorithm.DailyCostAlgorithm;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;

import java.time.LocalDate;
import java.util.Objects;

public class SetProviderAlgorthmCommand extends Command {
    private String providerName;
    private DailyCostAlgorithm algorithm;

    public SetProviderAlgorthmCommand(LocalDate executionTime, String providerName, DailyCostAlgorithm algorithm) {
        super(executionTime);
        this.providerName = providerName;
        this.algorithm = algorithm;
    }

    public SetProviderAlgorthmCommand(SetProviderAlgorthmCommand setProviderAlgorthmCommand) {
        super(setProviderAlgorthmCommand);
        this.providerName = setProviderAlgorthmCommand.getProviderName();
        this.algorithm = setProviderAlgorthmCommand.getAlgorithm();
    }

    public String getProviderName() {
        return this.providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public DailyCostAlgorithm getAlgorithm() {
        return this.algorithm;
    }

    public void setAlgorithm(DailyCostAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public SetProviderAlgorthmCommand clone(){
        return new SetProviderAlgorthmCommand(this);
    }

    @Override
    public String toString() {
        return "setProviderAlgorthmCommand{" +
                "providerName='" + this.providerName + '\'' +
                ", algorithm=" + this.algorithm +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SetProviderAlgorthmCommand that = (SetProviderAlgorthmCommand) o;
        return super.equals(that) && Objects.equals(providerName, that.providerName) && Objects.equals(algorithm, that.algorithm);
    }

    public void execute(Community community) throws ProviderDoesntExistException {
        community.setProviderAlgorithm(this.providerName, this.algorithm);
    }
}
