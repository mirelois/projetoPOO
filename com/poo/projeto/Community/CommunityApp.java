package com.poo.projeto.Community;

import com.poo.projeto.Command.*;
import com.poo.projeto.Community.Exceptions.NoHouseInPeriodException;
import com.poo.projeto.DailyCostAlgorithm.DailyCostAlgorithm;
import com.poo.projeto.DailyCostAlgorithm.DailyCostAlgorithmOne;
import com.poo.projeto.DailyCostAlgorithm.DailyCostAlgorithmTwo;
import com.poo.projeto.Invoice.Invoice;
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
import java.time.format.DateTimeParseException;
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

    public CommunityApp(CommunityApp communityApp){
        this.community = communityApp.getCommunity();
        this.idDevice = communityApp.getIdDevice();
        this.commands = communityApp.getCommands();
    }

    public List<Command> getCommands() {
        return this.commands.stream().map(Command::clone).collect(Collectors.toList());
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands.stream().map(Command::clone).collect(Collectors.toList());
    }

    public Community getCommunity() {
        return this.community.clone();
    }

    public void setCommunity(Community community) {
        this.community = community.clone();
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
        Iterator<Command> iterator = commands.iterator();
        Command command;
        while (iterator.hasNext()) {
            //TODO se um command falhar não vai executar os outros nem limpar a lista
            command = iterator.next();
            if (command.getExecutionTime().compareTo(newDate) < 0) {
                command.execute(this.community);
                iterator.remove();
            }
        }
        //commands.clear();
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

    public void addProvider(String provider, Double discountFactor) throws ProviderAlreadyExistsException {
        if (discountFactor == null)
            discountFactor = 0.0;
        this.community.addProvider(new Provider(provider, discountFactor));
    }

    public void addSmartBulb(String address, String division, String tone, String diameter, String baseConsumption, Double installationCost) throws AddressDoesntExistException {
        if (installationCost == null)
            installationCost = 1.0;
        //TODO ler addSmartCamera
        SmartBulb smartBulb = new SmartBulb(this.idDevice.toString(), false, installationCost, Double.parseDouble(baseConsumption), tone, Integer.parseInt(diameter));
        this.idDevice++;
        this.community.addSmartDevice(address, division, smartBulb);
    }

    public void addSmartSpeaker(String address, String division, String volume, String brand, String radio, String baseConsumption, Double installationCost) throws AddressDoesntExistException {
        if (installationCost == null)
            installationCost = 10.0;
        //TODO ler addSmartCamera
        SmartSpeaker smartSpeaker = new SmartSpeaker(this.idDevice.toString(), false, installationCost, Double.parseDouble(baseConsumption), Integer.parseInt(volume), brand, radio);
        this.idDevice++;
        this.community.addSmartDevice(address, division, smartSpeaker);
    }

    // TODO idDevice++?
    public void addSmartCamera(String address, String division, String resolution, String dimension, String baseConsumption, Double installationCost) throws AddressDoesntExistException {
        Integer[] resolutionInt = new Integer[2];
        String[] temp = resolution.split("x");
        //TODO não gosto deste parse, o controller devia logo atirar erro se não funcionasse e o Model não tem nada que saber parses
        //TODO isto vale tanto para o create como para o add
        resolutionInt[0] = Integer.parseInt(temp[0].substring(1));
        resolutionInt[1] = Integer.parseInt(temp[1].substring(0, temp[1].length() - 1));
        if (installationCost == null)
            installationCost = 100.0;
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
        return this.community.toString();
    }

    public String houseToString(String houseName) throws AddressDoesntExistException {
        return this.community.houseToString(houseName);
    }

    public String providerToString(String providerName) throws ProviderDoesntExistException {
        return this.community.providerToString(providerName);
    }

    public String houseWithMostConsumption(String start, String end) throws NoHouseInPeriodException, DateTimeParseException {
        LocalDate s = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate e = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
            s = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            e = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeException ex) {
            return Arrays.asList(ex.toString());
        }

        return this.community.orderedHousesByConsumption(s, e).stream().map(h -> h.getAddress() + " " + h.consumptionByPeriod(s, e).toString()).collect(Collectors.toList());
    }

    public void saveState(String fileName) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    public static CommunityApp loadState(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        CommunityApp newComunityApp = (CommunityApp) ois.readObject();
        ois.close();
        return newComunityApp;
    }

    public void setBaseConsumption(String date, String address, String deviceName, Double baseConsumption) {
        LocalDate localDate = this.community.getCurrentDate();
        if (date != null)
            localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.commands.add(new SetBaseConsumptionCommand(localDate, address, deviceName, baseConsumption));
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
        this.commands.add(new SetProviderAlgorthmCommand(getDate(date), providerName, dailyCostAlgorithm));
    }

    public void setProviderDiscountFactor(String date, String providerName, Double discountFactor) {
        this.commands.add(new SetProviderDiscountFactorCommand(getDate(date), providerName, discountFactor));
    }

    public void setSmartHouseProvider(String date, String address, String provider) {
        this.commands.add(new SetSmartHouseProviderCommand(getDate(date), address, provider));
    }

    public void turnDivision(String date, String address, String division, Boolean b) {
        this.commands.add(new TurnDivisionCommand(getDate(date), address, division, b));
    }

    public void turnSmartDevice(String date, String address, String smartDevice, Boolean b) {
        this.commands.add(new TurnSmartDeviceCommand(getDate(date), address, smartDevice, b));
    }

    public void advanceXCicles(int numberOfCicles) throws AddressDoesntExistException, DivisionDoesntExistException, ProviderAlreadyExistsException, DeviceDoesntExistException, AddressAlreadyExistsException, ProviderDoesntExistException, DivisionAlreadyExistsException {
        LocalDate current = this.community.getCurrentDate();
        int i = 0;
        Iterator<Command> iterator = commands.iterator();
        Command command;
        System.out.println(commands.size());
        System.out.println(this.community.getCurrentDate().toString());
        while (iterator.hasNext() && i < numberOfCicles) {
            command = iterator.next();
            if (!command.getExecutionTime().equals(current)) {
                i++;
                this.community.advanceDate(command.getExecutionTime());
                current = command.getExecutionTime();
            }
            if (i < numberOfCicles) {
                command.execute(this.community);
                iterator.remove();
            }
            System.out.println(commands.size());
            System.out.println(this.community.getCurrentDate().toString());
        }
        if (!iterator.hasNext() && i < numberOfCicles) {
            this.community.advanceDate(this.community.getCurrentDate().plusDays(1));
        }
    }

    public Boolean setInitialDate(String initialDate) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(initialDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            ;
        } catch (DateTimeParseException e) {
            return false;
        }
        this.community.setCurrentDate(localDate);
        return true;
    }

    public boolean isAutomaticSimulationOver() {
        return this.commands.size() == 0;
    }

    public String providersToString() {
        return this.community.providersToString();
    }

    public String housesToString() {
        return this.community.housesToString();
    }

    public void advanceFullAutomaticSimulation() throws ProviderAlreadyExistsException, AddressAlreadyExistsException, AddressDoesntExistException, DeviceDoesntExistException, ProviderDoesntExistException, DivisionDoesntExistException, DivisionAlreadyExistsException
    {
        LocalDate current = this.community.getCurrentDate();
        Iterator<Command> iterator = commands.iterator();
        Command command;
        while (iterator.hasNext()) {
            command = iterator.next();
            if (!command.getExecutionTime().equals(current)) {
                this.community.advanceDate(command.getExecutionTime());
                current = command.getExecutionTime();
            }
            command.execute(this.community);
            iterator.remove();
        }
    }

    public String smartHousesByNifString(String nif){
        return this.community.housesByNifString(nif);
    }

    public String commandToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Command command : commands) {
            stringBuilder.append(command.toString());
            stringBuilder.append("\n");
        }
        //TODO Lucena limpar estes tostrings seu nabo
        return stringBuilder.toString();
    }

    public CommunityApp clone(){
        return new CommunityApp(this);
    }
}
