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
import com.poo.projeto.SmartHouse.Exceptions.DivisionAlreadyExistsException;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommunityApp implements Serializable {

    private Community community;

    private Integer idDevice;

    private Integer addressGenerate;

    private String lastAddress;

    private String lastDivision;

    public CommunityApp() {
        this.community = new Community();
        this.idDevice = 0;
        this.addressGenerate = 0;
        this.lastAddress = "0";
        this.lastDivision = "0";
    }

    public Integer getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Integer idDevice) {
        this.idDevice = idDevice;
    }

    public Integer getAddressGenerate() {
        return addressGenerate;
    }

    public void setAddressGenerate(Integer addressGenerate) {
        this.addressGenerate = addressGenerate;
    }

    public String getLastAddress() {
        return lastAddress;
    }

    public void setLastAddress(String lastAddress) {
        this.lastAddress = lastAddress;
    }

    public String getLastDivision() {
        return lastDivision;
    }

    public void setLastDivision(String lastDivision) {
        this.lastDivision = lastDivision;
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

    public String addSmartHouse(String name, String nif, String provider) throws AddressAlreadyExistsException, ProviderDoesntExistException {
        this.lastAddress = this.addressGenerate.toString();
        SmartHouse house = new SmartHouse(this.lastAddress, name, nif);
        this.addressGenerate++;
        this.community.addSmartHouse(house, provider);
        return this.lastAddress;
    }

    public void addProvider(String provider) throws ProviderAlreadyExistsException {
        this.community.addProvider(new Provider(provider));
    }

    public void addSmartBulb(String tone, String diameter, String baseConsumption){
        double installationCost = 5.99;
        SmartBulb smartBulb = new SmartBulb(this.idDevice.toString(), false, installationCost, Double.parseDouble(baseConsumption), tone, Integer.parseInt(diameter));
        this.idDevice++;
        this.community.addSmartDevice(this.lastAddress, this.lastDivision, smartBulb);
    }

    public void addSmartSpeaker(String volume, String brand, String radio, String baseConsumption){
        double installationCost = 20.99;
        SmartSpeaker smartSpeaker = new SmartSpeaker(this.idDevice.toString(), false, installationCost, Double.parseDouble(baseConsumption), Integer.parseInt(volume), brand, radio);
        this.idDevice++;
        this.community.addSmartDevice(this.lastAddress, this.lastDivision, smartSpeaker);
    }
    // TODO idDevice++?
    public void addSmartCamera(String resolution, String dimension, String baseConsumption){
        Integer[] resolutionInt = new Integer[2];
        String[] temp = resolution.split("x");
        resolutionInt[0] = Integer.parseInt(temp[0].substring(1));
        resolutionInt[1] = Integer.parseInt(temp[1].substring(0, temp[1].length()-1));
        double installationCost = 50.99;
        SmartCamera smartCamera = new SmartCamera(this.idDevice.toString(), false, installationCost, Double.parseDouble(baseConsumption), resolutionInt, Integer.parseInt(dimension));
        this.idDevice++;
        this.community.addSmartDevice(this.lastAddress, this.lastDivision, smartCamera);
    }

    public void addDivision(String divisionName) throws DivisionAlreadyExistsException {
        Division division = new Division(divisionName);
        this.community.addDivision(this.lastAddress, division);
        this.setLastDivision(divisionName);
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

    public void saveState(String fileName) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);

        oos.flush();
        oos.close();
    }

    public static CommunityApp loadState(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        CommunityApp newComunityApp = (CommunityApp) ois.readObject();
        ois.close();
        return newComunityApp;
    }

    public void setProviderDiscountFactor(String providerName, Double discountFactor) throws ProviderDoesntExistException {
        this.community.getProviderByName(providerName).setDiscountFactor(discountFactor);
    }

    public void setProviderAlgorithm(String providerName, int algorithm){
     //TODO fazer isto if u can
    }

    public void setSmartHouseProvider(String address, String provider) throws AddressDoesntExistException, ProviderDoesntExistException {
        this.community.getSmartHouseByAddress(address).setProvider(this.community.getProviderByName(provider));
    }
}
