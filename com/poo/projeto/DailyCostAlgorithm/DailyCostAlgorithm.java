package com.poo.projeto.DailyCostAlgorithm;

import com.poo.projeto.Provider.Provider;
import com.poo.projeto.SmartHouse.SmartHouse;

import java.io.Serializable;
import java.util.function.BiFunction;

public abstract class DailyCostAlgorithm implements BiFunction<Provider, SmartHouse, Double> , Serializable {
    public abstract String getName();
    @Override
    public abstract String toString();
}