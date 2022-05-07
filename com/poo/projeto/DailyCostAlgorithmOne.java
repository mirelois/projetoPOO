package com.poo.projeto;

public class DailyCostAlgorithmOne implements DailyCostAlgorithm{
    @Override
    public Double dailyCostInterface(Provider provider, SmartHouse house) {
        double r = 0;
        for (SmartDevice smartDevice : house.getDevices().values()) {
            r += Provider.getBaseValueKWH() * smartDevice.ConsumoDiario() * (1 + Provider.getTaxFactor()) * 0.9;
        }
        return r;
    }

    public DailyCostAlgorithmOne() { }
}
