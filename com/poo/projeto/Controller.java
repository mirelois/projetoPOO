package com.poo.projeto;

import com.poo.projeto.Community.CommunityApp;
import com.poo.projeto.Community.Exceptions.NoHouseInPeriodException;
import com.poo.projeto.Provider.Exceptions.NoProvidersException;
import com.poo.projeto.SmartHouse.Exceptions.AddressAlreadyExistsException;
import com.poo.projeto.SmartHouse.Exceptions.AddressDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.DeviceDoesntExistException;
import com.poo.projeto.Provider.Exceptions.ProviderAlreadyExistsException;
import com.poo.projeto.Provider.Exceptions.ProviderDoesntExistException;
import com.poo.projeto.SmartHouse.Exceptions.DivisionAlreadyExistsException;

import java.lang.reflect.Method;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Controller {

    private CommunityApp model;
    private String lastAddress;

    private String lastDivision;

    //lista de comandos que pode estar vazia

    public CommunityApp getModel() {
        return model;
    }

    public void setModel(CommunityApp model) {
        this.model = model;
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

    public Controller(CommunityApp community) {
        this.setModel(community);
    }

    public boolean createSmartBulb(String line){
        String[] args = line.split(",");
        if(args.length!=3)
            return false;
        String tone = args[0];
        String diameter = args[1];
        String baseConsumption = args[2];
        this.model.addSmartBulb(this.lastAddress, this.lastDivision, tone, diameter, baseConsumption);

        return true;
    }

    public boolean createSmartCamera(String line){
        String[] args = line.split(",");
        if(args.length!=3)
            return false;
        String resolution = args[0];
        String dimention = args[1];
        String baseConsumption = args[2];
        this.model.addSmartCamera(this.lastAddress, this.lastDivision, resolution, dimention, baseConsumption);

        return true;
    }

    public boolean createSmartSpeaker(String line){
        String[] args = line.split(",");
        if(args.length!=4)
            return false;
        String volume = args[0];
        String brand = args[1];
        String radio = args[2];
        String baseConsumption = args[3];
        this.model.addSmartSpeaker(this.lastAddress, this.lastDivision, volume, brand, radio, baseConsumption);

        return true;
    }

    public Boolean createSmartHouse(String line){
        String[] args = line.split(",");
        if(args.length!=4)
            return false;
        String address = args[0];
        String name = args[1];
        String nif = args[2];
        String provider = args[3];
        try {
            this.model.addSmartHouse(address, name, nif, provider);
            this.lastAddress = args[0];
        }catch (AddressAlreadyExistsException e){
            e.printStackTrace();
            return false;
        }catch (ProviderDoesntExistException e){
            e.printStackTrace();
            return false;
        }

        this.setLastAddress(address);
        return true;
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

    public boolean createDivision(String line){
        String[] args = line.split(",");
        if(args.length!=1)
            return false;
        try {
            this.model.addDivision(this.lastAddress, args[0]);
            this.lastDivision = args[0];
        }catch (DivisionAlreadyExistsException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Map<String, Method> createClassMap()
    {
        String[] everyC = {"SmartBulb", "SmartCamera",
                "SmartSpeaker", "SmartHouse", "Division", "Provider"};
        Map<String, Method> everyClass = new HashMap<>();
        for(String s: everyC) {
            try {
                everyClass.put(s, this.getClass().getDeclaredMethod("create" + s, String.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return everyClass;
    }

    public void advanceDays(Integer days) {
        this.model.advanceDate(ChronoUnit.DAYS.addTo(this.model.getCurrentDate(), days));
    }
    public void parser(List<String> lines){
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

    public void turnSmartDeviceON(String address, String smartDevice) {
        try {
            this.model.turnSmartDevice(address, smartDevice, true);
        } catch (AddressDoesntExistException e) {
            e.printStackTrace();
        }
    }

    public void turnSmartDeviceOFF(String address, String smartDevice) {
        try {
            this.model.turnSmartDevice(address, smartDevice, false);
        } catch (AddressDoesntExistException e) {
            e.printStackTrace();
        }
    }

    public boolean isSimulationEmpty() {
        return isSimulationEmptyHouse() && isSimulationEmptyProvider();
    }

    public void turnONDivision(String address, String division) {
        try {
            this.model.turnDivision(address, division, true);
        } catch (AddressDoesntExistException e) {
            e.printStackTrace();
        }
    }

    public void turnOFFDivision(String address, String division) {
        try {
            this.model.turnDivision(address, division, false);
        } catch (AddressDoesntExistException e) {
            e.printStackTrace();
        }
    }

    public void advanceXCicles(int numberOfCicles) {

    }

    public void advanceXActions(int numberOfActions) {

    }

    public boolean isAutomaticSimulationOver() {

    }

    public void advanceFullAutomaticSimulation() {

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

    public String providerWithMostInvoicingVolume() {
        String string;
        try {
            string = this.model.providerWithMostInvoicingVolume();
        } catch (NoProvidersException e) {
            return e.toString();
        }
        return string;
    }

    public String invoicesByProvider(String providerName){
        List<String> list;
        try {
            list = this.model.invoicesByProvider(providerName);
        } catch (ProviderDoesntExistException e) {
            return e.toString();
        }

        StringBuilder ret = new StringBuilder();
        for (String string : list) {
            ret.append(string).append("\n");
        }
        return ret.toString();
    }

    public String orderedHousesByConsumption(String start, String end) {
        List<String> list;
        try {
            list = this.model.orderedHousesByConsumption(start, end);
        } catch (NoHouseInPeriodException e) {
            return e.toString();
        }

        StringBuilder ret = new StringBuilder();
        for (String string : list) {
            ret.append(string).append("\n");
        }
        return ret.toString();
    }

    public void changeProvider(String arg, String provider) {
        //Esta função tem de ser buffered
    }

    public void changeDiscountFactor(String arg, int discountFactor) {
        //Esta função tem de ser buffered
    }
}
