package com.poo.projeto.DailyCostAlgorithm;

import com.poo.projeto.SmartDevice;
import com.poo.projeto.SmartHouse;
import com.poo.projeto.provider.Provider;

import java.util.Set;

public interface DailyCostAlgorithm {
    public Double dailyCostInterface(Provider provider, SmartHouse house);
}
