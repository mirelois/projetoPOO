package com.poo.projeto;

import com.poo.projeto.Command.Command;
import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.Community.Exceptions.NoHouseInPeriodException;
import com.poo.projeto.DailyCostAlgorithm.DailyCostAlgorithm;
import com.poo.projeto.DailyCostAlgorithm.Exceptions.AlgorithmDoesntExistException;
import com.poo.projeto.Provider.Exceptions.NoProvidersException;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Controller {

    private Integer addressGenerate;
    private String lastDivision, lastAddress;
    private CommunityApp model;

    //lista de comandos que pode estar vazia

    public Controller(CommunityApp community) {
        this.setModel(community);
        this.addressGenerate = 0;
        this.lastAddress = "0";
        this.lastDivision = "cozinha";
    }

    public Controller(Controller controller){
        this.model = controller.getModel();
        this.lastAddress = controller.getLastAddress();
        this.lastDivision = controller.getLastDivision();
        this.addressGenerate = controller.getAddressGenerate();
    }

    public CommunityApp getModel() {
        return model.clone();
    }

    public void setModel(CommunityApp model) {
        this.model = model.clone();
    }

    public Integer getAddressGenerate() {
        return addressGenerate;
    }

    public void setAddressGenerate(Integer addressGenerate) {
        this.addressGenerate = addressGenerate;
    }

    public String getLastDivision() {
        return lastDivision;
    }

    public void setLastDivision(String lastDivision) {
        this.lastDivision = lastDivision;
    }

    public String getLastAddress() {
        return lastAddress;
    }

    public void setLastAddress(String lastAddress) {
        this.lastAddress = lastAddress;
    }

    public void saveState(String fileName) throws FileNotFoundException, IOException {
        this.model.saveState(fileName);
    }

    public void parseObjectFile(String filename) throws IOException, ClassNotFoundException {
        this.model = CommunityApp.loadState(filename);
    }

    public boolean createSmartBulb(String line) throws AddressDoesntExistException {
        String[] args = line.split(",");
        if(args.length!=3)
            return false;
        String tone = args[0];
        String diameter = args[1];
        String baseConsumption = args[2];
        this.model.addSmartBulb(this.lastAddress, this.lastDivision ,tone, diameter, baseConsumption, null);
        return true;
    }

    public boolean createSmartCamera(String line) throws AddressDoesntExistException, NumberFormatException {
        String[] args = line.split(",");
        if(args.length!=3)
            return false;
        String resolution = args[0];
        String dimention = args[1];
        String baseConsumption = args[2];
        this.model.addSmartCamera(this.lastAddress, this.lastDivision ,resolution, dimention, baseConsumption, null);

        return true;
    }

    public boolean createSmartSpeaker(String line) throws AddressDoesntExistException {
        String[] args = line.split(",");
        if(args.length!=4)
            return false;
        String volume = args[0];
        String brand = args[1];
        String radio = args[2];
        String baseConsumption = args[3];
        this.model.addSmartSpeaker(this.lastAddress, this.lastDivision ,volume, brand, radio, baseConsumption, null);

        return true;
    }

    public String createSmartHouse(String line) throws AddressAlreadyExistsException, ProviderDoesntExistException {
        String[] args = line.split(",");
        //if(args.length!=3)
        //    return false;
        String name = args[0];
        String nif = args[1];
        String provider = args[2];
        this.lastAddress = (this.addressGenerate++).toString();
        this.model.addSmartHouse(this.lastAddress, name, nif, provider);
        return this.lastAddress;
    }

    public boolean createProvider(String line) throws ProviderAlreadyExistsException, AlgorithmDoesntExistException {
        String[] args = line.split(",");
        if(args.length!=1)
            return false;
        String provider = args[0];
        this.model.addProvider(provider, null, 1);
        return true;
    }

    public boolean createDivision(String line) throws AddressDoesntExistException, DivisionAlreadyExistsException {
        String[] args = line.split(",");
        if(args.length!=1)
            return false;
        this.lastDivision = args[0];
        this.model.addDivision(this.lastAddress, args[0]);
        return true;
    }

    public Map<String, Method> createClassMap() throws NoSuchMethodException {
        String[] everyC = {"SmartBulb", "SmartCamera",
                "SmartSpeaker", "SmartHouse", "Division", "Provider"};
        Map<String, Method> everyClass = new HashMap<>();
        for(String s: everyC) {
            everyClass.put(s, this.getClass().getDeclaredMethod("create" + s, String.class));
        }

        return everyClass;
    }

    public void advanceDays(String days) throws AddressDoesntExistException, DivisionAlreadyExistsException, DivisionDoesntExistException, ProviderAlreadyExistsException, AddressAlreadyExistsException, ProviderDoesntExistException, DeviceDoesntExistException {
        this.model.advanceDate(ChronoUnit.DAYS.addTo(this.model.getCurrentDate(), Integer.parseInt(days)));
    }
    public void parser(List<String> lines) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String[] brokenLine;
        Map<String, Method> classMap = createClassMap();
        for(String line: lines){
            brokenLine = line.split(":", 2);
            if(classMap.containsKey(brokenLine[0])){
                classMap.get(brokenLine[0]).invoke(this, brokenLine[1]);
            }
        }
    }

    public boolean existsProvider(String providerName) {
        return this.model.existsProvider(providerName);
    }

    public boolean existsSmartHouse(String houseAddress) {
        return this.model.existsSmartHouse(houseAddress);
    }

    public boolean isSimulationEmptyProvider() {
        return this.model.numberOfProviders() == 0;
    }

    public boolean isSimulationEmptyHouse() {
        return this.model.numberOfHouses() == 0;
    }

    public boolean existsDivision(String address, String division) {
        try {
            return this.model.existsDivision(address, division);
        } catch (AddressDoesntExistException e) {
            return false;
        }
    }

    public boolean existsSmartDevice(String address, String smartDevice){
        try {
            return this.model.existsSmartDevice(address, smartDevice);
        } catch (AddressDoesntExistException e) {
            return false;
        }
    }

    public boolean isSmartDeviceOn(String address, String smartDevice) throws AddressDoesntExistException, DeviceDoesntExistException {
        return this.model.isSmartDeviceOn(address, smartDevice);
    }

    public void turnSmartDevice(String address, String smartDevice, boolean b) throws DeviceDoesntExistException {
        this.model.turnSmartDevice(null, address, smartDevice, b);
    }

    public boolean isSimulationEmpty() {
        return isSimulationEmptyHouse() && isSimulationEmptyProvider();
    }

    public void turnONDivision(String address, String division) throws AddressDoesntExistException, DivisionDoesntExistException {
        this.model.turnDivision(null, address, division, true);
    }

    public void turnOFFDivision(String address, String division) throws AddressDoesntExistException, DivisionDoesntExistException {
        this.model.turnDivision(null, address, division, false);
    }

    public void advanceXCicles(int numberOfCicles) throws AddressDoesntExistException, DivisionAlreadyExistsException, DivisionDoesntExistException, ProviderAlreadyExistsException, AddressAlreadyExistsException, ProviderDoesntExistException, DeviceDoesntExistException {
        this.model.advanceXCicles(numberOfCicles);
    }

    public boolean isAutomaticSimulationOver() {
        return this.model.isAutomaticSimulationOver();
    }

    public void advanceFullAutomaticSimulation() throws ProviderAlreadyExistsException, AddressAlreadyExistsException, AddressDoesntExistException, DeviceDoesntExistException, ProviderDoesntExistException, DivisionDoesntExistException, DivisionAlreadyExistsException {
        this.model.advanceFullAutomaticSimulation();
    }

    public String printHouse(String houseName) throws AddressDoesntExistException {
        return this.model.houseToString(houseName);
    }

    public String printProvider(String providerName) throws ProviderDoesntExistException {
        return this.model.providerToString(providerName);
    }

    public String smartHousesByNif(String nif){
        return this.model.smartHousesByNifString(nif);
    }

    public String printAll() {
        return this.model.toString();
    }

    public String printAllProviders(){
        return this.model.providersToString();
    }

    public String printAllHouses(){
        return this.model.housesToString();
    }

    public String printDateNow(){return this.model.getCurrentDate().toString();}

    public String houseWithMostConsumption(String start, String end) throws NoHouseInPeriodException, DateTimeParseException {
        return this.model.houseWithMostConsumption(start, end);
    }

    public String providerWithMostInvoicingVolume() throws NoProvidersException {
        return this.model.providerWithMostInvoicingVolume();
    }

    public String invoicesByProvider(String providerName) throws ProviderDoesntExistException {
        List<String> list = this.model.invoicesByProvider(providerName);
        StringBuilder ret = new StringBuilder();
        for (String string : list) {
            ret.append(string).append("\n");
        }
        return ret.toString();
    }

    public String orderedHousesByConsumption(String start, String end) throws NoHouseInPeriodException {
        List<String> list = this.model.orderedHousesByConsumption(start, end);
        StringBuilder ret = new StringBuilder();
        for (String string : list) {
            ret.append(string).append("\n");
        }
        return ret.toString();
    }

    public void changeProvider(String address, String provider) {
        this.model.setSmartHouseProvider(null, address, provider);
    }

    public void changeDiscountFactor(String provider, String discountFactor) {
        this.model.setProviderDiscountFactor(null, provider, Double.parseDouble(discountFactor));
        //não esquecer de dividir por 100 o factor
    }

    public void changeProviderAlgorithm(String provider, int i) {
        this.model.setProviderAlgorithm(null, provider, i);
    }

    public String parseActions(List<String> lines) throws AddressDoesntExistException, NumberFormatException, ProviderDoesntExistException, DeviceDoesntExistException, DivisionDoesntExistException {
        String[] brokenLine;
        for (String line : lines) {
            brokenLine = line.split(", ");
            if (this.model.existsSmartHouse(brokenLine[1])) {
                if (this.model.existsSmartDevice(brokenLine[1], brokenLine[2])) {
                    switch (brokenLine[3]) {
                        case "setOn":
                            this.model.turnSmartDevice(brokenLine[0], brokenLine[1], brokenLine[2], true);
                            break;
                        case "setOff":
                            this.model.turnSmartDevice(brokenLine[0], brokenLine[1], brokenLine[2], false);
                            break;
                        case "alteraConsumoBase":
                            //TODO porcaria
                            Double baseConsumption = Double.parseDouble(brokenLine[4]);
                            this.model.setBaseConsumption(brokenLine[0], brokenLine[1], brokenLine[2], baseConsumption);
                            break;
                    }
                } else if (this.model.existsProvider(brokenLine[2])) {
                    this.model.setSmartHouseProvider(brokenLine[0], brokenLine[1], brokenLine[2]);
                } else if (this.model.existsDivision(brokenLine[1], brokenLine[2])) {
                    switch (brokenLine[3]) {
                        case "divSetOn":
                            this.model.turnDivision(brokenLine[0], brokenLine[1], brokenLine[2], true);
                            break;
                        case "divSetOff":
                            this.model.turnDivision(brokenLine[0], brokenLine[1], brokenLine[2], false);
                            break;
                    }
                }
            } else if (this.model.existsProvider(brokenLine[1])) {
                switch (brokenLine[2]) {
                    case "alteraValorDesconto":
                        Double newDiscount = Double.parseDouble(brokenLine[3]);
                        this.model.setProviderDiscountFactor(brokenLine[0], brokenLine[1], newDiscount);
                        break;
                    case "alteraAlgoritmo":
                        int numAlgorithm = Integer.parseInt(brokenLine[3]);
                        this.model.setProviderAlgorithm(brokenLine[0], brokenLine[1], numAlgorithm);
                        break;
                }
            }
        }
        return this.model.commandToString();
    }

    public void addDivision(String address, String divisionName) throws AddressDoesntExistException, DivisionAlreadyExistsException {
        this.model.addDivision(address, divisionName);
    }

    public void addSmartSpeaker(String address, String division, String volume, String brand, String radio, String baseConsumption, String installationCost) throws AddressDoesntExistException {
        this.model.addSmartSpeaker(address, division, volume, brand, radio, baseConsumption, Double.parseDouble(installationCost));
    }

    public void addSmartBulb(String address, String division, String tone, String diameter, String baseConsumption, String installationCost) throws AddressDoesntExistException {
        this.model.addSmartBulb(address, division, tone, diameter, baseConsumption, Double.parseDouble(installationCost));
    }

    public void addSmartCamera(String address, String division, String resolution, String dimension, String baseConsumption, String installationCost) throws AddressDoesntExistException, NumberFormatException {
        this.model.addSmartCamera(address, division, resolution, dimension, baseConsumption, Double.parseDouble(installationCost));
    }

    public String addSmartHouse(String name, String nif, String provider) throws ProviderDoesntExistException, AddressAlreadyExistsException{
        String newAddress = this.addressGenerate.toString();
        this.addressGenerate++;
        this.model.addSmartHouse(newAddress, name, nif, provider);
        return newAddress;
    }

    public void addProvider(String name, String discountFactor, String algorithm) throws ProviderAlreadyExistsException, AlgorithmDoesntExistException {
        this.model.addProvider(name, Double.parseDouble(discountFactor), Integer.parseInt(algorithm));
    }

    public Boolean setInitialDate(String initialDate) {
        return  this.model.setInitialDate(initialDate);
    }

    public String showDailyCostAlgorithms(){
        return this.model.showDailyCostAlgorithms();
    }

    @Override
    public Controller clone(){
        return new Controller(this);
    }

    @Override
    public String toString() {
        return "Controller{" +
                "addressGenerate=" + this.addressGenerate +
                ", lastDivision='" + this.lastDivision + '\'' +
                ", lastAddress='" + this.lastAddress + '\'' +
                ", model=" + this.model +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Controller that = (Controller) o;
        return Objects.equals(addressGenerate, that.addressGenerate) && Objects.equals(lastDivision, that.lastDivision) && Objects.equals(lastAddress, that.lastAddress) && Objects.equals(model, that.model);
    }
}
