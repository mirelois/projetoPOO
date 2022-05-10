package com.poo.projeto.SmartHouse;


import java.util.Objects;

public class SmartSpeaker extends SmartDevice {
    public static final int MAX = 100; //volume máximo
    
    private int volume;
    private String brand;
    private String radio;

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
        this.brand = "";
        this.radio = "";
    }

    public SmartSpeaker(String s) {
        // initialise instance variables
        super(s);
        this.volume = 10;
        this.brand = "";
        this.radio = "";
    }

    public SmartSpeaker(String cod, String marca, String radio, int i) {
        // initialise instance variables
        super(cod);
        this.volume = Math.min(MAX, Math.max(i, 0));
        this.brand = marca;
        this.radio = radio;
    }

    public SmartSpeaker(SmartSpeaker s) {
        super(s);
        this.brand = s.brand;
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

    public String getBrand() {return this.brand;}

    public void setBrand(String c) {this.brand = c;}

    @Override
    public SmartSpeaker clone() {
        return new SmartSpeaker(this);
    }

    @Override
    public double dailyConsumption() {
        return this.getOn() ? 1+this.volume/100.0 : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartSpeaker that = (SmartSpeaker) o;
        return super.equals(o) && volume == that.volume &&
                Objects.equals(brand, that.brand);
    }

}
