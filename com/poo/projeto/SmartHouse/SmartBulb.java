package com.poo.projeto.SmartHouse;

public class SmartBulb extends SmartDevice {
    public static final int WARM = 2;
    public static final int NEUTRAL = 1;
    public static final int COLD = 0;
    
    private int tone;
    private int diameter;

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    /**
     * Constructor for objects of class SmartBulb
     */
    public SmartBulb() {
        // initialise instance variables
        super();
        this.tone = NEUTRAL;
        this.diameter = 0;
    }

    public SmartBulb(String id, double baseConsumption, int tone, int diameter) {
        // initialise instance variables
        super(id, diameter * 10 + baseConsumption, baseConsumption);
        this.tone = tone;
        this.diameter = diameter;
    }

    // TODO Deve haver melhor maneira
    public SmartBulb(String id, boolean on, double installationCost, double baseConsumption, String tone, int diameter) {
        // initialise instance variables
        super(id, on, installationCost, baseConsumption);
        switch (tone) {
            case "WARM" -> this.tone = 2;
            case "COLD" -> this.tone = 0;
            case "NEUTRAL" -> this.tone = 1;
        }
        this.diameter = diameter;
    }

    public SmartBulb(String id) {
        // initialise instance variables
        super(id, 10, 1);
        this.tone = NEUTRAL;
        this.diameter = 1;
    }

    public SmartBulb(SmartBulb b) {
        super(b);
        this.tone = b.tone;
        this.diameter = b.diameter;
    }

    // TODO código
    public SmartBulb(String tone, String diameter, String baseConsumption){
        //super(id, );
        //this.tone =
        this.diameter = Integer.parseInt(diameter);
    }

    public void setTone(int t) {
        this.tone = Math.max(COLD, Math.min(WARM, t));
    }

    public int getTone() {
        return this.tone;
    }

    @Override
    public SmartBulb clone() {
        return new SmartBulb(this);
    }

    @Override
    public double dailyConsumption() {
        return this.getOn() ? (this.getBaseConsumption() + (this.tone+1)/3.0) : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartBulb smartBulb = (SmartBulb) o;
        return super.equals(smartBulb) && tone == smartBulb.tone;
    }
}

