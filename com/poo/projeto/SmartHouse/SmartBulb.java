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

/**
 * Uma SmartBulb é uma lâmpada inteligente que além de ligar e desligar (já que
 * é subclasse de com.poo.projeto.SmartHouse.SmartDevice) também permite escolher a intensidade da iluminação
 * (a cor da mesma).
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SmartBulb extends SmartDevice {
    public static final int WARM = 2;
    public static final int NEUTRAL = 1;
    public static final int COLD = 0;
    
    private int tone;
    private int diameter;
    private double baseConsumption;

    public double getBaseConsumption() {
        return baseConsumption;
    }

    public void setBaseConsumption(double baseConsumption) {
        this.baseConsumption = baseConsumption;
    }

    public int getDiametro() {
        return diameter;
    }

    public void setDiametro(int diametro) {
        this.diameter = diametro;
    }

    /**
     * Constructor for objects of class SmartBulb
     */
    public SmartBulb() {
        // initialise instance variables
        super();
        this.tone = NEUTRAL;
        this.diameter = 0;
        this.baseConsumption = 0;
    }

    public SmartBulb(String id, int tone, int diametro, double consumoBase) {
        // initialise instance variables
        super(id);
        this.tone = tone;
        this.diameter = diametro;
        this.baseConsumption = consumoBase;
    }

    public SmartBulb(String id) {
        // initialise instance variables
        super(id);
        this.tone = NEUTRAL;
        this.diameter = 1;
        this.baseConsumption = 1;
    }

    public SmartBulb(SmartBulb b) {
        super(b);
        this.tone = b.tone;
        this.diameter = b.diameter;
        this.baseConsumption = b.baseConsumption;
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
        return this.getOn() ? (this.baseConsumption + (this.tone+1)/3.0) : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartBulb smartBulb = (SmartBulb) o;
        return super.equals(smartBulb) && tone == smartBulb.tone;
    }
}

