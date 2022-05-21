package com.poo.projeto.DailyCostAlgorithm;

import com.poo.projeto.Provider.Provider;
import com.poo.projeto.SmartHouse.SmartHouse;

import java.io.Serializable;

public class DailyCostAlgorithmOne extends DailyCostAlgorithm implements Serializable {

    private static DailyCostAlgorithmOne singletonAlgorithm = null;

    private DailyCostAlgorithmOne() { }

    public static DailyCostAlgorithmOne getInstance() {
        if (singletonAlgorithm == null)
            singletonAlgorithm = new DailyCostAlgorithmOne();
        return singletonAlgorithm;
    }

    @Override
    public Double apply(Provider provider, SmartHouse smartHouse) {
        return Provider.getBaseValueKWH() * smartHouse.totalConsumption() * (1 + Provider.getTaxFactor()) * Math.min((1 - provider.getDiscountFactor()), 0.01);
    }

    @Override
    public String getName() {
        return "DailyCostAlgorithmOne";
    }

    @Override
    public String toString(){
        return "DailyCostAlgorithmOne";
    }
}
