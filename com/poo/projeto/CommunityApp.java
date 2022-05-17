package com.poo.projeto;

import com.poo.projeto.SmartHouse.AddressAlreadyExistsException;
import com.poo.projeto.SmartHouse.AddressDoesntExistException;
import com.poo.projeto.SmartHouse.SmartHouse;
import com.poo.projeto.provider.Provider;
import com.poo.projeto.provider.ProviderAlreadyExistsException;
import com.poo.projeto.provider.ProviderDoesntExistException;

import java.time.LocalDate;

public class CommunityApp {

    private Community community;

    public CommunityApp() {
        this.community = new Community();
    }

    public void advanceDate(LocalDate newDate) {
        for(SmartHouse house : community.getSmartHouseMap().values()) {
            house.invoiceEmission(community.getCurrentDate(), newDate);
        }
        community.setCurrentDate(newDate);
    }

    public boolean existsProvider(String provider) {
        return this.community.existsProvider(provider);
    }

    public boolean existsSmartHouse(String houseAddress) {
        return this.community.existsSmartHouse(houseAddress);
    }

    public int numberOfProviders() {
        return this.community.numberOfProviders();
    }

    public int numberOfHouses() {
        return this.community.numberOfHouses();
    }

    public boolean existsDivision(String address, String division) throws AddressDoesntExistException {
        return community.getSmartHouseByAddress(address).existsDivision(division);
    }

    public boolean existsSmartDevice(String address, String smartDevice) throws AddressDoesntExistException {
        return community.getSmartHouseByAddress(address).existsDevice(smartDevice);
    }

    public boolean isSmartDeviceOn(String address, String smartDevice) throws AddressDoesntExistException {
        return community.getSmartHouseByAddress(address).isSmartDeviceOn(smartDevice);
    }
    public void addSmartDevice(String address, String name){

    }

    public void turnSmartDevice(String address, String smartDevice, boolean b) throws AddressDoesntExistException {
        SmartHouse house = community.getSmartHouseByAddress(address);
        if (b)
            house.setDeviceOn(smartDevice);
        else
            house.setDeviceOff(smartDevice);
    }

    public void turnDivision(String address, String division, boolean b) throws AddressDoesntExistException {
        SmartHouse house = community.getSmartHouseByAddress(address);
        if (b)
            house.setDeviceOn(division);
        else
            house.setDeviceOff(division);
    }

    public LocalDate getCurrentDate() {
        return this.community.getCurrentDate();
    }

    public void addSmartHouse(String address, String name, String nif, String provider) throws AddressAlreadyExistsException, ProviderDoesntExistException {
        SmartHouse house = new SmartHouse(address, name, nif);
        this.community.addSmartHouse(house, provider);
    }

    public void addProvider(String provider) throws ProviderAlreadyExistsException {
        this.community.addProvider(new Provider(provider));
    }
}
