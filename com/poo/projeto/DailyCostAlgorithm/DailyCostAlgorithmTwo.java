package com.poo.projeto.DailyCostAlgorithm;

import com.poo.projeto.SmartHouse.SmartHouse;
import com.poo.projeto.provider.Provider;


public class DailyCostAlgorithmTwo implements DailyCostAlgorithm {

    private static DailyCostAlgorithmTwo singletonAlgorithm = null;

    private DailyCostAlgorithmTwo() { }

    public DailyCostAlgorithmTwo getInstance() {
        if (singletonAlgorithm == null)
            singletonAlgorithm = new DailyCostAlgorithmTwo();
        return singletonAlgorithm;
    }

    @Override
    public Double apply(Provider provider, SmartHouse smartHouse) {
        double r =  Provider.getBaseValueKWH() * smartHouse.totalConsumption() * (1 + Provider.getTaxFactor());
        r *= smartHouse.numberOfDevices() > 10 ? (1 - provider.getDiscountFactor()): 1;
        return r;
    }
}
