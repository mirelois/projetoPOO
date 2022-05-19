package com.poo.projeto;

import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.Community.Exceptions.NoHouseInPeriodException;
import com.poo.projeto.Provider.Exceptions.NoProvidersException;
import com.poo.projeto.SmartHouse.Exceptions.*;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Controller {

    private CommunityApp model;

    //lista de comandos que pode estar vazia

    public CommunityApp getModel() {
        return model;
    }

    public void setModel(CommunityApp model) {
        this.model = model;
    }

    public Controller(CommunityApp community) {
        this.setModel(community);
    }

    public void saveState(String fileName) throws FileNotFoundException, IOException {
        this.model.saveState(fileName);
    }

    public boolean createSmartBulb(String line) throws AddressDoesntExistException {
        String[] args = line.split(",");
        if(args.length!=3)
            return false;
        String tone = args[0];
        String diameter = args[1];
        String baseConsumption = args[2];
        this.model.addSmartBulb(tone, diameter, baseConsumption);

        return true;
    }

    public boolean createSmartCamera(String line) throws AddressDoesntExistException {
        String[] args = line.split(",");
        if(args.length!=3)
            return false;
        String resolution = args[0];
        String dimention = args[1];
        String baseConsumption = args[2];
        this.model.addSmartCamera(resolution, dimention, baseConsumption);

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
        this.model.addSmartSpeaker(volume, brand, radio, baseConsumption);

        return true;
    }

    public String createSmartHouse(String line) throws AddressAlreadyExistsException, ProviderDoesntExistException {
        String[] args = line.split(",");
        //if(args.length!=3)
        //    return false;
        String name = args[0];
        String nif = args[1];
        String provider = args[2];
        return this.model.addSmartHouse(name, nif, provider);
    }

    public boolean createProvider(String line){
        String[] args = line.split(",");
        if(args.length!=1)
            return false;
        try{
            this.model.addProvider(args[0]);
        }catch (ProviderAlreadyExistsException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean createDivision(String line) throws AddressDoesntExistException, DivisionAlreadyExistsException {
        String[] args = line.split(",");
        if(args.length!=1)
            return false;
        this.model.addDivision(args[0]);
        //TODO cuidado com tamanho

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

    public void advanceDays(Integer days) throws AddressDoesntExistException, DivisionAlreadyExistsException, DivisionDoesntExistException, ProviderAlreadyExistsException, AddressAlreadyExistsException, ProviderDoesntExistException, DeviceDoesntExistException {
        this.model.advanceDate(ChronoUnit.DAYS.addTo(this.model.getCurrentDate(), days));
    }
    public void parser(List<String> lines) throws NoSuchMethodException {
        String[] brokenLine;
        Map<String, Method> classMap = createClassMap();
        for(String line: lines){
            brokenLine = line.split(":", 2);
            if(classMap.containsKey(brokenLine[0])){
                try {
                    classMap.get(brokenLine[0]).invoke(this, brokenLine[1]);
                }catch (Exception e){
                    e.printStackTrace();
                }
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

    public void turnSmartDevice(String address, String smartDevice, boolean b) throws AddressDoesntExistException, DeviceDoesntExistException {
        this.model.turnSmartDevice(address, smartDevice, b);
    }

    public boolean isSimulationEmpty() {
        return isSimulationEmptyHouse() && isSimulationEmptyProvider();
    }

    public void turnONDivision(String address, String division) throws AddressDoesntExistException, DivisionDoesntExistException {
        this.model.turnDivision(address, division, true);
    }

    public void turnOFFDivision(String address, String division) throws AddressDoesntExistException, DivisionDoesntExistException {
        this.model.turnDivision(address, division, false);
    }

    public void advanceXCicles(int numberOfCicles) {
        System.out.println("todo");
    }

    public void advanceXActions(int numberOfActions) {
        System.out.println("todo");

    }

    public boolean isAutomaticSimulationOver() {
        System.out.println("todo");
        return true;
    }

    public void advanceFullAutomaticSimulation() {
        System.out.println("todo");
    }

    public String printHouse(String houseName) throws AddressDoesntExistException {
        return this.model.houseToString(houseName);
    }

    public String printProvider(String providerName) throws ProviderDoesntExistException {
        return this.model.providerToString(providerName);
    }

    public String printAll() {
        return this.model.toString();
    }

    public String houseWithMostConsumption(String start, String end) throws NoHouseInPeriodException {
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
        //Esta função tem de ser buffered
    }

    public void changeDiscountFactor(String provider, Double discountFactor) {
        //Esta função tem de ser buffered
        //não esquecer de dividir por 100 o factor
    }

    public void changeProviderAlgorithm(String provider, int i) {
        //Esta função tem de ser buffered
    }

    public void parseActions(List<String> lines) throws AddressDoesntExistException, NumberFormatException, ProviderDoesntExistException, DeviceDoesntExistException, DivisionDoesntExistException {

        String[] brokenLine;
        for (String line : lines) {
            brokenLine = line.split(", ");
            if (this.model.existsSmartHouse(brokenLine[1])) {
                if (this.model.existsSmartDevice(brokenLine[1], brokenLine[2])) {
                    switch (brokenLine[3]) {
                        case "setOn":
                            this.model.turnSmartDevice(brokenLine[1], brokenLine[2], true);
                            break;
                        case "setOff":
                            this.model.turnSmartDevice(brokenLine[1], brokenLine[2], false);
                            break;
                        case "changeBaseConsumption":
                            //TODO porcaria
                            Double baseConsumption = Double.parseDouble(brokenLine[4]);
                            this.model.setBaseConsumption(brokenLine[1], brokenLine[2], baseConsumption);
                            break;
                    }
                } else if (this.model.existsProvider(brokenLine[2])) {
                    this.model.setSmartHouseProvider(brokenLine[1], brokenLine[2]);
                } else if (this.model.existsDivision(brokenLine[1], brokenLine[2])) {
                    switch (brokenLine[3]) {
                        case "setOn":
                            this.model.turnDivision(brokenLine[1], brokenLine[3], true);
                            break;
                        case "setOff":
                            this.model.turnDivision(brokenLine[1], brokenLine[3], false);
                            break;
                    }
                }
            } else if (this.model.existsProvider(brokenLine[1])) {
                switch (brokenLine[2]) {
                    case "alteraValorDesconto":
                        Double newDiscount = Double.parseDouble(brokenLine[3]);
                        this.model.setProviderDiscountFactor(brokenLine[1], newDiscount);
                        break;
                    case "alteraAlgoritmo":
                        int numAlgorithm = Integer.parseInt(brokenLine[3]);
                        this.model.setProviderAlgorithm(brokenLine[1], numAlgorithm);
                        break;
                }
            }
        }
    }

    public void addDivision(String address, String division) {
        //TODO mudar quando conseguir receber address
        this.model.addDivision(division);
    }
}
