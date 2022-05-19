package com.poo.projeto.Provider;

import com.poo.projeto.DailyCostAlgorithm.DailyCostAlgorithm;
import com.poo.projeto.DailyCostAlgorithm.DailyCostAlgorithmOne;
import com.poo.projeto.Invoice;
import com.poo.projeto.SmartHouse.SmartHouse;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Provider implements Comparable<Provider>, Serializable {
    private static int baseValueKWH, taxFactor;
    private String name;
    private Map<SmartHouse, Set<Invoice>> invoiceMap;
    private Double discountFactor;
    private DailyCostAlgorithm dailyCostAlgorithm;

    public Provider() {
        this.name = "";
        this.invoiceMap = new HashMap<>();
        this.discountFactor = 0.0;
        this.dailyCostAlgorithm = DailyCostAlgorithmOne.getInstance();
    }

    public Provider(Provider p) {
        this.name = p.name;
        this.invoiceMap = p.getInvoiceMap();
        this.discountFactor = p.discountFactor;
        this.dailyCostAlgorithm = p.dailyCostAlgorithm;
    }

    public Provider(String name, Map<SmartHouse, Set<Invoice>> invoiceMap, Double discountFactor, DailyCostAlgorithm dailyCostAlgorithm) {
        this.name = name;
        this.setInvoiceMap(invoiceMap);
        this.discountFactor = discountFactor;
        this.dailyCostAlgorithm = dailyCostAlgorithm;
    }

    public Provider(String name, Double discountFactor) {
        this.name = name;
        this.invoiceMap = new HashMap<>();
        this.discountFactor = discountFactor;
        this.dailyCostAlgorithm = DailyCostAlgorithmOne.getInstance();
    }

    public Provider(String name){
        this.name = name;
        this.invoiceMap = new HashMap<>();
        this.discountFactor = 0.0;
        this.dailyCostAlgorithm = DailyCostAlgorithmOne.getInstance();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<SmartHouse, Set<Invoice>> getInvoiceMap() {
        HashMap<SmartHouse, Set<Invoice>> invoiceMap = new HashMap<>();
        for (Map.Entry<SmartHouse, Set<Invoice>> m : this.invoiceMap.entrySet()) {
            invoiceMap.put(m.getKey(), m.getValue().stream().map(Invoice::clone).collect(Collectors.toSet()));
        }
        return invoiceMap;
    }

    public void setInvoiceMap(Map<SmartHouse, Set<Invoice>> invoiceMap) {
        this.invoiceMap = new HashMap<>();
        for (Map.Entry<SmartHouse, Set<Invoice>> m : invoiceMap.entrySet()) {
            this.invoiceMap.put(m.getKey(), m.getValue().stream().map(Invoice::clone).collect(Collectors.toSet()));
        }
    }

    public static int getBaseValueKWH() {
        return baseValueKWH;
    }

    public static void setBaseValueKWH(int baseValueKWH) {
        Provider.baseValueKWH = baseValueKWH;
    }

    public static int getTaxFactor() {
        return taxFactor;
    }

    public static void setTaxFactor(int taxFactor) {
        Provider.taxFactor = taxFactor;
    }

    public Double getDiscountFactor() {
        return discountFactor;
    }

    public void setDiscountFactor(Double discountFactor) {
        this.discountFactor = discountFactor;
    }

    public DailyCostAlgorithm getDailyCostAlgorithm() {
        return dailyCostAlgorithm;
    }

    public void setDailyCostAlgorithm(DailyCostAlgorithm dailyCostAlgorithm) {
        this.dailyCostAlgorithm = dailyCostAlgorithm;
    }

    @Override
    public Provider clone() {
        return new Provider(this);
    }

    @Override
    public int compareTo(Provider o) {
        return Double.compare(this.invoicingVolume(), o.invoicingVolume());
    }

    public double dailyCost(SmartHouse house) {
        return dailyCostAlgorithm.apply(this, house);
    }

    public Double invoicingVolume() {
        double r = 0;
        for (Set<Invoice> invoices : this.invoiceMap.values()) {
            for (Invoice invoice : invoices) {
                r += invoice.getCost();
            }
        }
        return r;
    }

    public Invoice invoiceEmission(SmartHouse house, LocalDate start, LocalDate end) {
        long days = ChronoUnit.DAYS.between(start, end);
        Invoice invoice = new Invoice(start, end, house.totalConsumption()*days, this.dailyCost(house)*days + house.getIntalationCosts(), house.getAddress(), this.getName());
        Set<Invoice> set = this.invoiceMap.get(house);
        if (set == null) {
            set = new TreeSet<>(Comparator.comparingDouble(Invoice::getCost));
            set.add(invoice);
            this.invoiceMap.put(house, set);
        } else {
            set.add(invoice);
        }
        return invoice.clone();
    }

    @Override
    public String toString() {
        return "Provider{" +
                "name='" + name + '\'' +
                //", invoiceMap=" + invoiceMap +
                ", discountFactor=" + discountFactor +
                ", dailyCostAlgorithm=" + dailyCostAlgorithm.getName() +
                '}';
    }
}
