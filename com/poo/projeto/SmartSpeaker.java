package com.poo.projeto;

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
 * Um SmartSpeaker é um com.poo.projeto.SmartDevice que além de ligar e desligar permite também
 * reproduzir som.
 * Consegue ligar-se a um canal (por simplificação uma rádio online) e permite
 * a regulação do seu nível de volume.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SmartSpeaker extends SmartDevice {
    public static final int MAX = 100; //volume máximo
    
    private int volume;
    private String marca, radio;

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    /**
     * Constructor for objects of class SmartSpeaker
     */
    public SmartSpeaker() {
        // initialise instance variables
        super();
        this.volume = 0;
        this.marca = "";
        this.radio = "";
    }

    public SmartSpeaker(String s) {
        // initialise instance variables
        super(s);
        this.volume = 10;
        this.marca = "";
        this.radio = "";
    }

    public SmartSpeaker(String cod, String marca, String radio, int i) {
        // initialise instance variables
        super(cod);
        this.volume = Math.min(MAX, Math.max(i, 0));
        this.marca = marca;
        this.radio = radio;
    }

    public SmartSpeaker(SmartSpeaker s) {
        super(s);
        this.marca = s.marca;
        this.volume = s.volume;
        this.radio = s.radio;
    }

    public void volumeUp() {
        if (this.volume<MAX) this.volume++;
    }

    public void volumeDown() {
        if (this.volume>0) this.volume--;
    }

    public int getVolume() {return this.volume;}

    public String getMarca() {return this.marca;}

    public void setMarca(String c) {this.marca = c;}

    public SmartSpeaker clone() {
        return new SmartSpeaker(this);
    }

    @Override
    public double ConsumoDiario() {
        return this.getOn() ? 1+this.volume/100.0 : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartSpeaker that = (SmartSpeaker) o;
        return super.equals(o) && volume == that.volume &&
                Objects.equals(marca, that.marca);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volume, marca);
    }
}
