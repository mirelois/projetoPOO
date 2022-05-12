package com.poo.projeto;

import java.util.List;
import java.util.Map;

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

    public Map<String, Object> createClassMap(){
        return null;
    }

    public void parser(List<String> lines){
        String[] brokenLine;
        Map<String, Object> classMap = createClassMap();
        for(String line: lines){
            brokenLine = line.split(":", 2);
            if(classMap.containsKey(brokenLine[0])){
                classMap.get(brokenLine[0]);
            }
        }
    }

}
