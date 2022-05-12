package com.poo.projeto.DailyCostAlgorithm;

import com.poo.projeto.SmartHouse.SmartHouse;
import com.poo.projeto.provider.Provider;

public class DailyCostAlgorithmOne implements DailyCostAlgorithm {

    private static DailyCostAlgorithmOne singletonAlgorithm = null;

    private DailyCostAlgorithmOne() { }

    public static DailyCostAlgorithmOne getInstance() {
        if (singletonAlgorithm == null)
            singletonAlgorithm = new DailyCostAlgorithmOne();
        return singletonAlgorithm;
    }

    @Override
    public Double apply(Provider provider, SmartHouse smartHouse) {
        return Provider.getBaseValueKWH() * smartHouse.totalConsumption() * (1 + Provider.getTaxFactor());
    }
}
