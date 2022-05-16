package com.poo.projeto;

import com.poo.projeto.SmartHouse.Division;

import java.lang.reflect.Constructor;
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

    public Set<String> createClassSet()
    {
        String[] everyClass = {"SmartDevice", "SmartBulb", "SmartCamera",
                "SmartSpeaker", "SmartHouse", "Division", "Owner", "Provider"};
        return new HashSet<>(Arrays.asList(everyClass));
    }

    public void parser(List<String> lines){
        String[] brokenLine;
        Set<String> classSet = createClassSet();
        for(String line: lines){
            brokenLine = line.split(":", 2);
            if(classSet.contains(brokenLine[0])){
                Class<?> c = Class.forName(brokenLine[0]);
                Constructor<?> constructor = c.getConstructor(String.class, Integer.class);
                Object instancia = constructor.newInstance("ola", 1);
                this.model.addProvider(instancia);
            }
        }
    }

}
