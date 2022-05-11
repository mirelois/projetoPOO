package com.poo.projeto.DailyCostAlgorithm;

import com.poo.projeto.SmartHouse.SmartHouse;
import com.poo.projeto.provider.Provider;

public class DailyCostAlgorithmOne implements DailyCostAlgorithm {

    public DailyCostAlgorithmOne() { }

    @Override
    public Double apply(Provider provider, SmartHouse smartHouse) {
        return Provider.getBaseValueKWH() * smartHouse.totalConsumption() * (1 + Provider.getTaxFactor());
    }
}
