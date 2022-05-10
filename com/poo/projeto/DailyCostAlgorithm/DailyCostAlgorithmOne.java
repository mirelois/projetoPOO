package com.poo.projeto.DailyCostAlgorithm;

import com.poo.projeto.SmartHouse.SmartHouse;
import com.poo.projeto.provider.Provider;

public class DailyCostAlgorithmOne implements DailyCostAlgorithm {
    @Override
    public Double dailyCostInterface(Provider provider, SmartHouse house) {
        return Provider.getBaseValueKWH() * house.totalConsumption() * (1 + Provider.getTaxFactor());
    }

    public DailyCostAlgorithmOne() { }
}
