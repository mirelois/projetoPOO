package com.poo.projeto.provider;

import com.poo.projeto.SmartDevice;
import com.poo.projeto.SmartHouse;

public class DailyCostAlgorithmOne implements DailyCostAlgorithm {
    @Override
    public Double dailyCostInterface(Provider provider, SmartHouse house) {
        double r = 0;
        for (SmartDevice smartDevice : house.getDevices().values()) {
            r += Provider.getBaseValueKWH() * smartDevice.ConsumoDiario() * (1 + Provider.getTaxFactor());
        }
        return r;
    }

    public DailyCostAlgorithmOne() { }
}
