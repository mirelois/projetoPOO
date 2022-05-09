package com.poo.projeto;

import com.poo.projeto.provider.Provider;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Community {
    Set<Provider> providerSet;
    Map<String, SmartHouse> smartHouseMap;
    LocalDate date;

    public Community(Community c) {
        this.providerSet = c.providerSet.stream().map(Provider::clone).collect(Collectors.toSet());
        this.smartHouseMap = c.smartHouseMap;
        this.date = c.date;
    }

    public Community() {
        this.providerSet = new TreeSet<>();
        this.smartHouseMap = new HashMap<>();
        this.date = LocalDate.EPOCH;
    }

    public Community(Set<Provider> providerSet, Map<String, SmartHouse> smartHouseMap, LocalDate date) {
        this.providerSet = providerSet;
        this.smartHouseMap = smartHouseMap;
        this.date = date;
    }

    public Set<Provider> getProviderSet() {
        return providerSet;
    }

    public void setProviderSet(Set<Provider> providerSet) {
        this.providerSet = providerSet;
    }

    public Map<String, SmartHouse> getSmartHouseMap() {
        return smartHouseMap;
    }

    public void setSmartHouseMap(Map<String, SmartHouse> smartHouseMap) {
        this.smartHouseMap = smartHouseMap;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
