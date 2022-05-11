package com.poo.projeto;

import com.poo.projeto.SmartHouse.SmartHouse;
import com.poo.projeto.provider.Provider;

import java.time.LocalDate;
import java.util.*;

public class Community {
    SortedSet<String> providerSet;
    Map<String, Provider> providerMap;
    Map<String, SmartHouse> smartHouseMap;
    LocalDate currentDate;

    public Community(Community c) {
        this.setProviderSet(c.providerSet);
        this.setProviderMap(c.providerMap);
        this.setSmartHouseMap(c.smartHouseMap);
        this.setCurrentDate(c.currentDate);
    }

    public Community() {
        this.setProviderSet(new TreeSet<>(Comparator.comparing(o -> providerMap.get(o))));
        this.setProviderMap(new HashMap<>());
        this.setSmartHouseMap(new HashMap<>());
        this.setCurrentDate(LocalDate.EPOCH);
    }

    public Community(SortedSet<String> providerSet, Map<String, Provider> providerMap, Map<String, SmartHouse> smartHouseMap, LocalDate currentDate, ArrayList<LocalDate> periods, Map<LocalDate, SortedSet<Invoice>> invoicesByPeriod) {
        this.setProviderSet(providerSet);
        this.setProviderMap(providerMap);
        this.setSmartHouseMap(smartHouseMap);
        this.setCurrentDate(currentDate);
    }

    public SortedSet<String> getProviderSet() {
        return new TreeSet<>(providerSet);
    }

    public void setProviderSet(SortedSet<String> providerSet) {
        this.providerSet = new TreeSet<>(providerSet);
    }

    public Map<String, Provider> getProviderMap() {
        return providerMap;
    }

    public void setProviderMap(Map<String, Provider> providerMap) {
        this.providerMap = providerMap;
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

    public List<SmartHouse> orderedHousesWithMostConsumption(LocalDate period) {
        List<SmartHouse> smartHouses = new ArrayList<>();
        for (Invoice invoice : this.invoicesByPeriod.get(period)) {
            smartHouses.add(this.smartHouseMap.get(invoice.getSmartHouse()));
        }
        return smartHouses;
    }

    public Set<Invoice> invoicesFromProvider(String provider) {
        Provider provider1 = this.providerMap.get(provider);
        if (provider1 == null) {
            //TODO pode dar throw NoProvider exception
            return new TreeSet<>();
        } else {
            Set<Invoice> tmp = new TreeSet<>();
            provider1.getInvoiceMap().values().forEach(tmp::addAll);
            return tmp;
        }
    }

    public Provider providerWithMostInvoicingVolume() {
        //TODO tentar ordenar o mapa em si e não necessitar de indireção
        return this.providerMap.get(this.providerSet.last()); //provider ordena por invoicingVolume
    }

    public SmartHouse houseWithMostConsumption(LocalDate period) {
        SortedSet<Invoice> invoices = this.invoicesByPeriod.get(period);
        if (invoices == null) {
            //TODO pode dar throw NoPeriodAdvanced exception
            return null;
        } else {
            return this.smartHouseMap.get(invoices.last().getSmartHouse());
        }
    }

    public void advanceDate(LocalDate newDate) {
        SortedSet<Invoice> invoices = new TreeSet<>((Comparator.comparingDouble(Invoice::getConsumption)));
        for(SmartHouse house : this.smartHouseMap.values()) {
            invoices.add(house.invoiceEmission(this.currentDate, newDate));
        }
        this.invoicesByPeriod.put(this.currentDate, invoices);
        this.periods.add(this.currentDate);
        this.currentDate = newDate;
    }

    public void addSmartHouse(SmartHouse house) {
        //TODO throw HouseNull
        if (house != null)
            this.smartHouseMap.put(house.getAddress(), house.clone());
    }

    public void addProvider(Provider provider) {
        //TODO throw ProviderNull
        if (provider != null)
            this.providerMap.put(provider.getName(), provider.clone());
    }
}
