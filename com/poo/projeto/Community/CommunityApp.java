package com.poo.projeto.Community;

import com.poo.projeto.Command.*;
import com.poo.projeto.Community.Exceptions.NoHouseInPeriodException;
import com.poo.projeto.DailyCostAlgorithm.DailyCostAlgorithm;
import com.poo.projeto.DailyCostAlgorithm.DailyCostAlgorithmOne;
import com.poo.projeto.DailyCostAlgorithm.DailyCostAlgorithmTwo;
import com.poo.projeto.Invoice;
import com.poo.projeto.Provider.Exceptions.NoProvidersException;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.Provider.Provider;
import com.poo.projeto.SmartHouse.*;
import com.poo.projeto.SmartHouse.Exceptions.*;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CommunityApp implements Serializable {

    private Community community;

    private Integer idDevice;

    private List<Command> commands;

    public CommunityApp() {
        this.community = new Community();
        this.idDevice = 0;
        this.commands = new ArrayList<>();
    }

    public Integer getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Integer idDevice) {
        this.idDevice = idDevice;
    }

    public void advanceDate(LocalDate newDate) throws AddressDoesntExistException, DivisionDoesntExistException,
            ProviderAlreadyExistsException, DeviceDoesntExistException,
            AddressAlreadyExistsException, ProviderDoesntExistException, DivisionAlreadyExistsException {
        this.community.advanceDate(newDate);
        for (Command command : commands) {
            //TODO se um command falhar não vai executar os outros nem limpar a lista
            //TODO ver se o comando é para ser executado no próximo ciclo ou não
            command.execute(this.community);
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

    //public void turnSmartDevice(String address, String smartDevice, boolean b) throws AddressDoesntExistException, DeviceDoesntExistException {
    //    community.turnSmartDevice(address, smartDevice, b);
    //}
//
    //public void turnDivision(String address, String division, boolean b) throws AddressDoesntExistException, DivisionDoesntExistException {
    //    community.turnDivision(address, division, b);
    //}

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

    public void addSmartBulb(String address, String division, String tone, String diameter, String baseConsumption) throws AddressDoesntExistException {
        double installationCost = 5.99;
        SmartBulb smartBulb = new SmartBulb(this.idDevice.toString(), false, installationCost, Double.parseDouble(baseConsumption), tone, Integer.parseInt(diameter));
        this.idDevice++;
        this.community.addSmartDevice(address, division, smartBulb);
    }

    public void addSmartSpeaker(String address, String division, String volume, String brand, String radio, String baseConsumption) throws AddressDoesntExistException {
        double installationCost = 20.99;
        SmartSpeaker smartSpeaker = new SmartSpeaker(this.idDevice.toString(), false, installationCost, Double.parseDouble(baseConsumption), Integer.parseInt(volume), brand, radio);
        this.idDevice++;
        this.community.addSmartDevice(address, division, smartSpeaker);
    }
    // TODO idDevice++?
    public void addSmartCamera(String address, String division, String resolution, String dimension, String baseConsumption) throws AddressDoesntExistException {
        Integer[] resolutionInt = new Integer[2];
        String[] temp = resolution.split("x");
        resolutionInt[0] = Integer.parseInt(temp[0].substring(1));
        resolutionInt[1] = Integer.parseInt(temp[1].substring(0, temp[1].length()-1));
        double installationCost = 50.99;
        SmartCamera smartCamera = new SmartCamera(this.idDevice.toString(), false, installationCost, Double.parseDouble(baseConsumption), resolutionInt, Integer.parseInt(dimension));
        this.idDevice++;
        this.community.addSmartDevice(address, division, smartCamera);
    }

    public void addDivision(String address, String divisionName) throws DivisionAlreadyExistsException, AddressDoesntExistException {
        Division division = new Division(divisionName);
        this.community.addDivision(address, division);
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

    public void setBaseConsumption(String date, String address, String deviceName, Double baseConsumption) {
        LocalDate localDate = this.community.getCurrentDate();
        if (date != null)
            localDate = LocalDate.parse(date);
        this.commands.add(new setBaseConsumptionCommand(localDate, address, deviceName, baseConsumption));
    }

    public LocalDate getDate(String date) {
        LocalDate localDate = this.community.getCurrentDate();
        if (date != null)
            localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        return localDate;
    }

    public void setProviderAlgorithm(String date, String providerName, int algorithm) {
        DailyCostAlgorithm dailyCostAlgorithm;
        switch (algorithm) {
            case 2:
                dailyCostAlgorithm = DailyCostAlgorithmTwo.getInstance();
                break;
            default:
                dailyCostAlgorithm = DailyCostAlgorithmOne.getInstance();
                break;
        }
        this.commands.add(new setProviderAlgorthmCommand(getDate(date), providerName, dailyCostAlgorithm));
    }

    public void setProviderDiscountFactor(String date, String providerName, Double discountFactor) {
        this.commands.add(new setProviderDiscountFactorCommand(getDate(date), providerName, discountFactor));
    }

    public void setSmartHouseProvider(String date, String address, String provider) {
        this.commands.add(new setSmartHouseProviderCommand(getDate(date), address, provider));
    }

    public void turnDivision(String date, String address, String division, Boolean b) {
        this.commands.add(new turnDivisionCommand(getDate(date), address, division, b));
    }

    public void turnSmartDevice(String date, String address, String smartDevice, Boolean b) {
        this.commands.add(new turnSmartDeviceCommand(getDate(date), address, smartDevice, b));
    }

    public void advanceXCicles(int numberOfCicles) throws AddressDoesntExistException, DivisionDoesntExistException, ProviderAlreadyExistsException, DeviceDoesntExistException, AddressAlreadyExistsException, ProviderDoesntExistException, DivisionAlreadyExistsException {
        LocalDate current = this.community.getCurrentDate();
        int i = 0;
        Iterator<Command> iterator = commands.iterator();
        Command command;
        while(iterator.hasNext() && i < numberOfCicles) {
            command = iterator.next();
            if (!command.getExecutionTime().equals(current)) {
                i++;
                this.community.advanceDate(command.getExecutionTime());
                current = command.getExecutionTime();
            }
            if (i<numberOfCicles)
                command.execute(this.community);
        }
    }

    /* public void setProviderDiscountFactor(String providerName, Double discountFactor) throws ProviderDoesntExistException {
        this.community.setProviderDiscountFactor(providerName, discountFactor);
    }

    public void setSmartHouseProvider(String address, String provider) throws AddressDoesntExistException, ProviderDoesntExistException {
        this.community.setSmartHouseProvider(address, provider);
    }

    public void setBaseConsumption(String address, String device, Double baseConsumption) throws AddressDoesntExistException, DeviceDoesntExistException {
        this.community.setBaseConsumption(address, device, baseConsumption);
    } */

    //public void addDivision(String address, String divisionName) {
    //    Division division = new Division(divisionName);
    //    this.commands.add(new addDivisionCommand(this.community.getCurrentDate(), address, division));
    //}

    //public void addProvider(String providerName, Double discountFactor) {
    //    Provider provider = new Provider(providerName, discountFactor);
    //    this.commands.add(new addProviderCommand(this.community.getCurrentDate(), provider));
    //}

    //public void addSmartBulb(String division, String address, Integer tone, Integer diameter, Double baseConsumption) {
    //    SmartBulb smartBulb = new SmartBulb(this.idDevice.toString(), baseConsumption, tone, diameter);
    //    this.idDevice++;
    //    this.commands.add(new addSmartBulbCommand(this.community.getCurrentDate(), division, address, smartBulb));
    //}

    //public void addSmartCamera(String division, String address, Integer[] resolution, Integer dimension, Double baseConsumption) {
    //    SmartCamera smartCamera = new SmartCamera(this.idDevice.toString(), baseConsumption, resolution, dimension);
    //    this.idDevice++;
    //    this.commands.add(new addSmartCameraCommand(this.community.getCurrentDate(), division, address, smartCamera));
    //}

    //public void addSmartSpeaker(String division, String address,Double baseConsumption, Integer volume, String brand, String radio) {
    //    SmartSpeaker smartSpeaker = new SmartSpeaker(this.idDevice.toString(), baseConsumption, volume, brand, radio);
    //    idDevice++;
    //    this.commands.add(new addSmartSpeakerCommand(this.community.getCurrentDate(), division, address, smartSpeaker));
    //}

    //public String addSmartHouse(String address, String name, String nif, String provider) {
    //    this.lastAddress = this.addressGenerate.toString();
    //    SmartHouse house = new SmartHouse(this.lastAddress, name, nif);
    //    this.addressGenerate++;
    //    this.commands.add(new addSmartHouseCommand(this.getCurrentDate(), house, provider));
    //    return this.lastAddress;
    //}
}
