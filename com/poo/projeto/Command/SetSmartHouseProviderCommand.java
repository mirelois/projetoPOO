package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;

import java.time.LocalDate;
import java.util.Objects;

public class SetSmartHouseProviderCommand extends Command {
    private String address, provider;

    public SetSmartHouseProviderCommand(LocalDate executionTime, String address, String provider) {
        super(executionTime);
        this.address = address;
        this.provider = provider;
    }

    public SetSmartHouseProviderCommand(SetSmartHouseProviderCommand setSmartHouseProviderCommand) {
        super(setSmartHouseProviderCommand);
        this.address = setSmartHouseProviderCommand.getAddress();
        this.provider = setSmartHouseProviderCommand.getProvider();
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvider() {
        return this.provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public SetSmartHouseProviderCommand clone(){
        return new SetSmartHouseProviderCommand(this);
    }

    @Override
    public String toString() {
        return "SetSmartHouseProviderCommand{" +
                "address='" + this.address + '\'' +
                ", provider='" + this.provider + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SetSmartHouseProviderCommand that = (SetSmartHouseProviderCommand) o;
        return super.equals(that) && Objects.equals(address, that.address) && Objects.equals(provider, that.provider);
    }

    @Override
    public void execute(Community app) throws AddressDoesntExistException, ProviderDoesntExistException {
        app.setSmartHouseProvider(this.address, this.provider);
    }
}
