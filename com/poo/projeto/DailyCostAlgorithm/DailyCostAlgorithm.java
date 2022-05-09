package com.poo.projeto.DailyCostAlgorithm;

import com.poo.projeto.SmartHouse.SmartHouse;
import com.poo.projeto.provider.Provider;

public interface DailyCostAlgorithm {
    public Double dailyCostInterface(Provider provider, SmartHouse house);
}
