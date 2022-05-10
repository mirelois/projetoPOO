package com.poo.projeto.SmartHouse;
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


import java.util.Objects;

/**
 * A classe com.poo.projeto.SmartHouse.SmartDevice é um contactor simples.
 * Permite ligar ou desligar circuitos. 
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class SmartDevice {

    private final String id;
    private boolean on;
    private double installationCost;

    public double getInstallationCost() {
        return installationCost;
    }

    public void setInstallationCost(double installationCost) {
        this.installationCost = installationCost;
    }

    /**
     * Constructor for objects of class com.poo.projeto.SmartHouse.SmartDevice
     */
    public SmartDevice() {
        this.id = "";
        this.on = false;
        this.installationCost = 0;
    }

    public SmartDevice(String s) {
        this.id = s;
        this.on = false;
        this.installationCost = 0;
    }

    public SmartDevice(String s, boolean b) {
        this.id = s;
        this.on = b;
        this.installationCost = 0;
    }

    public SmartDevice(SmartDevice d) {
        this.id = d.id;
        this.on = d.on;
        this.installationCost = d.installationCost;
    }

    public void turnOn() {
        this.on = true;
    }
    
    public void turnOff() {
        this.on = false;
    }
    
    public boolean getOn() {return this.on;}
    
    public void setOn(boolean b) {this.on = b;}
    
    public String getID() {return this.id;}

    public abstract SmartDevice clone();

    public abstract double dailyConsumption();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartDevice that = (SmartDevice) o;
        return on == that.on &&
                Objects.equals(id, that.id);
    }
}
