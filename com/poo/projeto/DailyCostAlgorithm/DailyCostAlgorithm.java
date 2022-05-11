package com.poo.projeto.DailyCostAlgorithm;

import com.poo.projeto.SmartHouse.SmartHouse;
import com.poo.projeto.provider.Provider;

import java.util.function.BiFunction;

public interface DailyCostAlgorithm extends BiFunction<Provider, SmartHouse, Double> { }