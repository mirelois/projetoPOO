package com.poo.projeto.Community;

import com.poo.projeto.Command.*;
import com.poo.projeto.Community.Exceptions.NoHouseInPeriodException;
import com.poo.projeto.DailyCostAlgorithm.DailyCostAlgorithm;
import com.poo.projeto.DailyCostAlgorithm.DailyCostAlgorithmOne;
import com.poo.projeto.DailyCostAlgorithm.DailyCostAlgorithmTwo;
import com.poo.projeto.Invoice;
import com.poo.projeto.Provider.Exceptions.NoProvidersException;
import com.poo.projeto.SmartHouse.*;
import com.poo.projeto.SmartHouse.Exceptions.*;
import com.poo.projeto.Provider.Provider;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;

import java.awt.geom.Arc2D;
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

    private List<Command> commands;

    public CommunityApp() {
        this.community = new Community();
        this.idDevice = 0;
        this.addressGenerate = 0;
        this.lastAddress = "0";
        this.lastDivision = "0";
        this.commands = new ArrayList<>();
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

    public void advanceDate(LocalDate newDate) throws AddressDoesntExistException, DivisionDoesntExistException, ProviderAlreadyExistsException, DeviceDoesntExistException, AddressAlreadyExistsException, ProviderDoesntExistException, DivisionAlreadyExistsException {
        this.community.advanceDate(newDate);
        for (Command command : commands) {
            //TODO se um command falhar não vai executar os outros nem limpar a lista
            command.execute(this);
        }
        commands.clear();
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
        return community.existsDivision(address, division);
    }

    public boolean existsSmartDevice(String address, String smartDevice) throws AddressDoesntExistException {
        return community.existsSmartDevice(address, smartDevice);
    }

    public boolean isSmartDeviceOn(String address, String smartDevice) throws AddressDoesntExistException, DeviceDoesntExistException {
        return community.isSmartDeviceOn(address, smartDevice);
    }

    public void turnSmartDevice(String address, String smartDevice, boolean b) throws AddressDoesntExistException, DeviceDoesntExistException {
        community.turnDevice(address, smartDevice, b);
    }

    public void turnDivision(String address, String division, boolean b) throws AddressDoesntExistException, DivisionDoesntExistException {
        community.turnDivision(address, division, b);
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

    public void addSmartBulb(String tone, String diameter, String baseConsumption) throws AddressDoesntExistException {
        double installationCost = 5.99;
        SmartBulb smartBulb = new SmartBulb(this.idDevice.toString(), false, installationCost, Double.parseDouble(baseConsumption), tone, Integer.parseInt(diameter));
        this.idDevice++;
        this.community.addSmartDevice(this.lastAddress, this.lastDivision, smartBulb);
    }

    public void addSmartSpeaker(String volume, String brand, String radio, String baseConsumption) throws AddressDoesntExistException {
        double installationCost = 20.99;
        SmartSpeaker smartSpeaker = new SmartSpeaker(this.idDevice.toString(), false, installationCost, Double.parseDouble(baseConsumption), Integer.parseInt(volume), brand, radio);
        this.idDevice++;
        this.community.addSmartDevice(this.lastAddress, this.lastDivision, smartSpeaker);
    }
    // TODO idDevice++?
    public void addSmartCamera(String resolution, String dimension, String baseConsumption) throws AddressDoesntExistException {
        Integer[] resolutionInt = new Integer[2];
        String[] temp = resolution.split("x");
        resolutionInt[0] = Integer.parseInt(temp[0].substring(1));
        resolutionInt[1] = Integer.parseInt(temp[1].substring(0, temp[1].length()-1));
        double installationCost = 50.99;
        SmartCamera smartCamera = new SmartCamera(this.idDevice.toString(), false, installationCost, Double.parseDouble(baseConsumption), resolutionInt, Integer.parseInt(dimension));
        this.idDevice++;
        this.community.addSmartDevice(this.lastAddress, this.lastDivision, smartCamera);
    }

    public void addDivision(String divisionName) throws DivisionAlreadyExistsException, AddressDoesntExistException {
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
        return this.community.houseToString(houseName);
    }

    public String providerToString(String providerName) throws ProviderDoesntExistException {
        return this.community.providerToString(providerName);
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
        this.community.setDiscountFactor(providerName, discountFactor);
    }

    public void setProviderAlgorithm(String providerName, int algorithm) throws ProviderDoesntExistException {
        DailyCostAlgorithm dailyCostAlgorithm;
        switch (algorithm) {
            case 2:
                dailyCostAlgorithm = DailyCostAlgorithmTwo.getInstance();
                break;
            default:
                dailyCostAlgorithm = DailyCostAlgorithmOne.getInstance();
                break;
        }
        this.community.setProviderAlgorithm(providerName, dailyCostAlgorithm);
     //TODO fazer isto if u can
    }

    public void setSmartHouseProvider(String address, String provider) throws AddressDoesntExistException, ProviderDoesntExistException {
        this.community.setSmartHouseProvider(address, provider);
    }

    public void setBaseConsumption(String address, String device, Double baseConsumption) throws AddressDoesntExistException, DeviceDoesntExistException {
        this.community.setBaseConsumption(address, device, baseConsumption);
    }

    public void addDivisionFake(String divisionName) {
        this.commands.add(new addDivisionCommand(this.community.getCurrentDate(), divisionName));
    }

    public void addProviderFake(String providerName) {
        this.commands.add(new addProviderCommand(this.community.getCurrentDate(), providerName));
    }

    public void addSmartBulbFake(String division, String tone, String diameter, String baseConsumption) {
        this.commands.add(new addSmartBulbCommand(this.community.getCurrentDate(), division, tone, diameter, baseConsumption));
    }

    public void addSmartCameraFake(String division, String resolution, String dimension, String baseConsumption) {
        this.commands.add(new addSmartCameraCommand(this.community.getCurrentDate(), division, resolution, dimension, baseConsumption));
    }

    public void addSmartSpeakerFake(String division, String volume, String brand, String radio, String baseConsumption) {
        this.commands.add(new addSmartSpeakerCommand(this.community.getCurrentDate(), division, volume, brand, radio, baseConsumption));
    }

    public void addSmartHouseFake(String name, String nif, String provider) {
        this.commands.add(new addSmarthouseCommand(this.community.getCurrentDate(), name, nif, provider));
    }

    public void setProviderAlgorithmFake(String providerName, int algorithm) {
        this.commands.add(new setProviderAlgorthmCommand(this.community.getCurrentDate(), providerName, algorithm));
    }

    public void setProviderDiscountFactorFake(String providerName, Double discountFactor) {
        this.commands.add(new setProviderDiscountFactorCommand(this.community.getCurrentDate(), providerName, discountFactor));
    }

    public void setSmartHouseProviderFake(String address, String provider) {
        this.commands.add(new setSmartHouseProviderCommand(this.community.getCurrentDate(), address, provider));
    }

    public void turnDivisionFake(String address, String division, Boolean b) {
        this.commands.add(new turnDivisionCommand(this.community.getCurrentDate(), address, division, b));
    }

    public void turnSmartDeviceFake(String address, String smartDevice, Boolean b) {
        this.commands.add(new turnSmartDeviceCommand(this.community.getCurrentDate(), address, smartDevice, b));
    }
}
