package com.poo.aula6_4;

/*********************************************************************************/
/** DISCLAIMER: Este código foi criado e alterado durante as aulas práticas      */
/** de POO. Representa uma solução em construção, com base na matéria leccionada */ 
/** até ao momento da sua elaboração, e resulta da discussão e experimentação    */
/** durante as aulas. Como tal, não deverá ser visto como uma solução canónica,  */
/** ou mesmo acabada. É disponibilizado para auxiliar o processo de estudo.      */
/** Os alunos são encorajados a testar adequadamente o código fornecido e a      */
/** procurar soluções alternativas, à medida que forem adquirindo mais           */
/** conhecimentos de POO.                                                        */
/*********************************************************************************/

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.HashMap;


/**
 * A CasaInteligente faz a gestão dos SmartDevices que existem e dos 
 * espaços (as salas) que existem na casa.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CasaInteligente {
   
    private String morada;
    private Map<String, SmartDevice> devices; // identificador -> SmartDevice
    private Map<String, List<String>> locations; // Espaço -> Lista codigo dos devices

    /**
     * Constructor for objects of class CasaInteligente
     */
    public CasaInteligente() {
        // initialise instance variables
        this.morada = "";
        this.devices = new HashMap<>();
        this.locations = new HashMap<>();
    }

    public CasaInteligente(String morada) {
        // initialise instance variables
        this.morada = morada;
        this.devices = new HashMap<>();
        this.locations = new HashMap<>();
    }
    
    public void setDeviceOn(String devCode) {
        this.devices.get(devCode).turnOn();
    }
    
    public boolean existsDevice(String id) {
        return this.devices.containsKey(id);
    }
    
    public void addDevice(SmartDevice s) {
        this.devices.put(s.getID(), s.clone());
        if (!this.locations.containsKey("hall")) {
            this.locations.put("hall", new ArrayList<>());
        }
        this.locations.get("hall").add(s.getID());
    }
    
    public SmartDevice getDevice(String s) {
        if (this.devices.containsKey(s))
            return this.devices.get(s).clone();
        else
            return null;
    }
    
    public void setOn(String s, boolean b) {
        this.devices.get(s).setOn(b);
    }
    
    public void setAllOn(boolean b) {
        for (SmartDevice d: this.devices.values()) {
            d.setOn(b);
        }
    }
    
    public void addRoom(String s) {
        this.locations.put(s, new ArrayList<>());
    }
    
    public boolean hasRoom(String s) {
        return this.locations.containsKey(s);
    }
    
    public void addToRoom (String s1, String s2) {
        if (!hasRoom(s1))
            addRoom(s1);
        this.locations.get(s1).add(s2);
    }
    
    public boolean roomHasDevice (String s1, String s2) {
        return this.locations.containsKey(s1) && this.locations.get(s1).contains(s2);
    }
    
}
