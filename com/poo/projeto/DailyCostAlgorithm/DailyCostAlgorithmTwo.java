package com.poo.projeto.DailyCostAlgorithm;

import com.poo.projeto.SmartHouse.SmartHouse;
import com.poo.projeto.provider.Provider;


public class DailyCostAlgorithmTwo implements DailyCostAlgorithm {

    public DailyCostAlgorithmTwo() { }

    @Override
    public Double apply(Provider provider, SmartHouse smartHouse) {
        double r =  Provider.getBaseValueKWH() * smartHouse.totalConsumption() * (1 + Provider.getTaxFactor());
        r *= smartHouse.numberOfDevices() > 10 ? (1 - provider.getDiscountFactor()): 1;
        return r;
    }
}
