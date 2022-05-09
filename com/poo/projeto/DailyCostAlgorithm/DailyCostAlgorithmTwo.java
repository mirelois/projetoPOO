package com.poo.projeto.DailyCostAlgorithm;

import com.poo.projeto.SmartHouse.SmartHouse;
import com.poo.projeto.provider.Provider;


public class DailyCostAlgorithmTwo implements DailyCostAlgorithm {
    @Override
    public Double dailyCostInterface(Provider provider, SmartHouse house) {
        double r =  Provider.getBaseValueKWH() * house.totalConsumption() * (1 + Provider.getTaxFactor());
        r *= house.getDevices().size() > 10 ? (1 - provider.getDiscountFactor()): 1;
        return r;
    }

    public DailyCostAlgorithmTwo() { }
}
