package com.poo.projeto;

import java.util.List;
import java.util.Map;

public class Controler {

    public Controler() {
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
