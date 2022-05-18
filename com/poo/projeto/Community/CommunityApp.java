package com.poo.projeto.Community;

import com.poo.projeto.Community.Exceptions.NoHouseInPeriodException;
import com.poo.projeto.Invoice;
import com.poo.projeto.Provider.Exceptions.NoProvidersException;
import com.poo.projeto.SmartHouse.*;
import com.poo.projeto.SmartHouse.Exceptions.AddressAlreadyExistsException;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.DeviceDoesntExistException;
import com.poo.projeto.Provider.Provider;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public boolean isSmartDeviceOn(String address, String smartDevice) throws AddressDoesntExistException, DeviceDoesntExistException {
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

    public void addSmartBulb(String address){
        SmartBulb smartBulb = new SmartBulb();
        this.community.addSmartDevice(address, smartBulb);
    }

    public void addSmartSpeaker(){
        this.community.addSmartDevice();
    }

    public void addSmartCamera(String address, String resolution, String dimension, String baseConsumption){
        SmartCamera smartCamera = new SmartCamera();
        this.community.addSmartDevice(address, smartCamera);
    }

    //TODO mudar este toString pensando em como mostrar a app toda
    @Override
    public String toString() {
        return "CommunityApp{" +
                "community=" + community.toString() +
                '}';
    }

    public String houseToString(String houseName) throws AddressDoesntExistException {
        return this.community.getSmartHouseByAddress(houseName).toString();
    }

    public String providerToString(String providerName) throws ProviderDoesntExistException {
        return this.community.getProviderByName(providerName).toString();
    }

    public String houseWithMostConsumption(String start, String end) throws NoHouseInPeriodException {
        LocalDate s = LocalDate.parse(start, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate e = LocalDate.parse(end, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        SmartHouse house = this.community.houseWithMostConsumption(s, e);
        return house.toString();
    }

    public String providerWithMostInvoicingVolume() throws NoProvidersException {
        return this.community.providerWithMostInvoicingVolume().getName();
    }

    public List<String> invoicesByProvider(String providerName) throws ProviderDoesntExistException {
        return this.community.invoicesByProvider(providerName).stream().map(Invoice::toString).collect(Collectors.toList());
    }

    public List<String> orderedHousesByConsumption(String start, String end) throws NoHouseInPeriodException {
        LocalDate s, e;
        try {
            s = LocalDate.parse(start, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            e = LocalDate.parse(end, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeException ex) {
            return Arrays.asList(ex.toString());
        }

        return this.community.orderedHousesByConsumption(s, e).stream().map(SmartHouse::getAddress).collect(Collectors.toList());
    }
}
