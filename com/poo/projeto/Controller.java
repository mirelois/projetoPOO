package com.poo.projeto;

import com.poo.projeto.SmartHouse.AddressAlreadyUsedException;
import com.poo.projeto.SmartHouse.Division;
import com.poo.projeto.provider.ProviderDoesntExistException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Controller {

    private Community model;

    public Community getModel() {
        return model;
    }

    public void setModel(Community model) {
        this.model = model;
    }

    public Controller(Community community) {
        this.setModel(community);
    }

    public void createSmartBulb(String line){

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
        }catch (AddressAlreadyUsedException e){
            e.printStackTrace();
            return false;
        }catch (ProviderDoesntExistException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void createProvider(String line){

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

}
