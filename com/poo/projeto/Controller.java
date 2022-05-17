package com.poo.projeto;

import com.poo.projeto.SmartHouse.AddressAlreadyExistsException;
import com.poo.projeto.SmartHouse.AddressDoesntExistException;
import com.poo.projeto.provider.ProviderAlreadyExistsException;
import com.poo.projeto.provider.ProviderDoesntExistException;

import java.lang.reflect.Method;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Controller {

    private CommunityApp model;
    private String lastAddress;
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
        this.model.addSmartDevice(this.lastAddress, tone, diameter, baseConsumption);

        return true;
    }

    public void createSmartCamera(String line){

    }

    public void createSmartSpearker(String line){

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

    public boolean existsDivision(String address, String division) throws AddressDoesntExistException {
        return this.model.existsDivision(address, division);
    }

    public boolean existsSmartDevice(String address, String smartDevice) throws AddressDoesntExistException {
        return this.model.existsSmartDevice(address, smartDevice);
    }

    public boolean isSmartDeviceOn(String address, String smartDevice) throws AddressDoesntExistException {
        return this.model.isSmartDeviceOn(address, smartDevice);
    }

    public void turnSmartDeviceON(String address, String smartDevice) throws AddressDoesntExistException {
        this.model.turnSmartDevice(address, smartDevice, true);
    }

    public void turnSmartDeviceOFF(String address, String smartDevice) throws AddressDoesntExistException {
        this.model.turnSmartDevice(address, smartDevice, false);
    }

    public boolean isSimulationEmpty() {
        return isSimulationEmptyHouse() && isSimulationEmptyProvider();
    }

    public void turnONDivision(String address, String division) throws AddressDoesntExistException {
        this.model.turnDivision(address, division, true);
    }

    public void turnOFFDivision(String address, String division) throws AddressDoesntExistException {
        this.model.turnDivision(address, division, false);
    }

    public void advanceXCicles(int numberOfCicles) {

    }

    public void advanceXActions(int numberOfActions) {

    }

    public boolean isAutomaticSimulationOver() {

    }

    public void advanceFullAutomaticSimulation() {
    }

    public String printHouse(String houseName) {
        return this.model.houseToString(houseName);
    }

    public boolean printProvider(String providerName) {
        return this.model.providerToString(providerName);
    }


    public String printAll() {
        return this.model.toString();
    }
}
