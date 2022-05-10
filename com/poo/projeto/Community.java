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
    ArrayList<LocalDate> periods;
    Map<LocalDate, SortedSet<Invoice>> invoicesByPeriod;

    public Community(Community c) {
        this.setProviderSet(c.providerSet);
        this.setSmartHouseMap(c.smartHouseMap);
        this.setCurrentDate(c.currentDate);
        this.setPeriods(c.periods);
        this.setInvoicesByPeriod(c.invoicesByPeriod);
    }

    public Community() {
        this.setProviderSet(new TreeSet<>());
        this.setSmartHouseMap(new HashMap<>());
        this.setCurrentDate(LocalDate.EPOCH);
        this.setPeriods(new ArrayList<>());
        this.setInvoicesByPeriod(new HashMap<>());
    }

    public Community(SortedSet<Provider> providerSet, Map<String, SmartHouse> smartHouseMap, LocalDate currentDate, ArrayList<LocalDate> periods, Map<LocalDate, SortedSet<Invoice>> invoicesByPeriod) {
        this.setProviderSet(providerSet);
        this.setSmartHouseMap(smartHouseMap);
        this.setCurrentDate(currentDate);
        this.setPeriods(periods);
        this.setInvoicesByPeriod(invoicesByPeriod);
    }

    public SortedSet<Provider> getProviderSet() {
        return providerSet.stream().map(Provider::clone).collect(Collectors.toCollection(TreeSet::new));
    }

    public void setProviderSet(SortedSet<Provider> providerSet) {
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

    public ArrayList<LocalDate> getPeriods() {
        return periods;
    }

    public void setPeriods(ArrayList<LocalDate> periods) {
        this.periods = periods;
    }

    public Map<LocalDate, SortedSet<Invoice>> getInvoicesByPeriod() {
        return invoicesByPeriod;
    }

    public void setInvoicesByPeriod(Map<LocalDate, SortedSet<Invoice>> invoicesByPeriod) {
        this.invoicesByPeriod = invoicesByPeriod;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    public void

    public void advanceDate(LocalDate newDate) {
        SortedSet<Invoice> invoices = new TreeSet<>((Comparator.comparingDouble(Invoice::getConsumption)));
        for(SmartHouse house : this.smartHouseMap.values()) {
            invoices.add(house.invoiceEmission(this.currentDate, newDate));
        }
        this.invoicesByPeriod.put(this.currentDate, invoices);
        this.periods.add(this.currentDate);
        this.currentDate = newDate;
    }
}
