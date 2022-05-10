package com.poo.projeto;

import com.poo.projeto.SmartHouse.SmartHouse;
import com.poo.projeto.provider.Provider;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Community {
    SortedSet<Provider> providerSet;
    Map<String, SmartHouse> smartHouseMap;
    LocalDate currentDate;

    public Community(Community c) {
        this.setProviderSet(c.providerSet);
        this.setSmartHouseMap(c.smartHouseMap);
        this.currentDate = c.currentDate;
    }

    public Community() {
        this.providerSet = new TreeSet<>();
        this.smartHouseMap = new HashMap<>();
        this.currentDate = LocalDate.EPOCH;
    }

    public Community(Set<Provider> providerSet, Map<String, SmartHouse> smartHouseMap, LocalDate currentDate) {
        this.setProviderSet(providerSet);
        this.setSmartHouseMap(smartHouseMap);
        this.currentDate = currentDate;
    }

    public SortedSet<Provider> getProviderSet() {
        return providerSet.stream().map(Provider::clone).collect(Collectors.toCollection(TreeSet::new));
    }

    public void setProviderSet(Set<Provider> providerSet) {
        this.providerSet = providerSet.stream().map(Provider::clone).collect(Collectors.toCollection(TreeSet::new));
    }

    public Map<String, SmartHouse> getSmartHouseMap() {
        Map<String, SmartHouse> smartHouseMap = new HashMap<>();
        for (Map.Entry<String, SmartHouse> entry : this.smartHouseMap.entrySet()) {
            smartHouseMap.put(entry.getKey(), entry.getValue().clone());
        }
        return smartHouseMap;
    }

    public void setSmartHouseMap(Map<String, SmartHouse> smartHouseMap) {
        this.smartHouseMap = new HashMap<>();
        for (Map.Entry<String, SmartHouse> entry : smartHouseMap.entrySet()) {
            this.smartHouseMap.put(entry.getKey(), entry.getValue().clone());
        }
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    //função que avança o tempo
    //atualizar "casaMaisGostosa"
}
