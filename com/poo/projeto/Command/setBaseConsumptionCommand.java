package com.poo.projeto.Command;

import com.poo.projeto.Community.Community;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.*;

import java.time.LocalDate;

public class setBaseConsumptionCommand extends Command{

    private String address, device;
    private Double baseConsumption;

    public setBaseConsumptionCommand(LocalDate executionDate, String address, String device, Double baseConsumption) {
        super(executionDate);
        this.address = address;
        this.device = device;
        this.baseConsumption = baseConsumption;
    }

    @Override
    public void execute(Community community) throws AddressDoesntExistException, DeviceDoesntExistException {
        community.setBaseConsumption(address, device, baseConsumption);
    }
}
