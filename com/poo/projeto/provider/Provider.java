package com.poo.projeto.provider;

import com.poo.projeto.DailyCostAlgorithm.DailyCostAlgorithm;
import com.poo.projeto.Invoice;
import com.poo.projeto.SmartHouse.SmartHouse;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Provider implements Comparable<Provider>{
    private String name;
    private Map<SmartHouse, Set<Invoice>> oldInvoiceMap;
    private Map<SmartHouse, Invoice> recentInvoiceMap;
    private static int baseValueKWH, taxFactor;
    private int discountFactor;
    private DailyCostAlgorithm dailyCostAlgorithm;

    public Provider(Provider p) {
        this.name = p.name;
        this.oldInvoiceMap = p.getOldInvoiceMap();
        this.recentInvoiceMap = p.getRecentInvoiceMap();
        this.discountFactor = p.discountFactor;
        this.dailyCostAlgorithm = p.dailyCostAlgorithm;
    }

    public Provider(String name, Map<SmartHouse, Set<Invoice>> oldInvoiceMap, int discountFactor, DailyCostAlgorithm dailyCostAlgorithm, Map<SmartHouse, Invoice> recentInvoiceMap) {
        this.name = name;
        this.setOldInvoiceMap(oldInvoiceMap);
        this.setRecentInvoiceMap(recentInvoiceMap);
        this.discountFactor = discountFactor;
        this.dailyCostAlgorithm = dailyCostAlgorithm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<SmartHouse, Set<Invoice>> getOldInvoiceMap() {
        HashMap<SmartHouse, Set<Invoice>> invoiceMap = new HashMap<>();
        for (Map.Entry<SmartHouse, Set<Invoice>> m : oldInvoiceMap.entrySet()) {
            this.oldInvoiceMap.put(m.getKey(), m.getValue().stream().map(Invoice::clone).collect(Collectors.toCollection(TreeSet::new)));
        }
        return invoiceMap;
    }

    public void setOldInvoiceMap(Map<SmartHouse, Set<Invoice>> oldInvoiceMap) {
        this.oldInvoiceMap = oldInvoiceMap;
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

    public int getDiscountFactor() {
        return discountFactor;
    }

    public void setDiscountFactor(int discountFactor) {
        this.discountFactor = discountFactor;
    }

    public DailyCostAlgorithm getDailyCostAlgorithm() {
        return dailyCostAlgorithm;
    }

    public void setDailyCostAlgorithm(DailyCostAlgorithm dailyCostAlgorithm) {
        this.dailyCostAlgorithm = dailyCostAlgorithm;
    }

    public Map<SmartHouse, Invoice> getRecentInvoiceMap() {
        HashMap <SmartHouse, Invoice> smartHouseInvoiceHashMap = new HashMap<>();
        for (Map.Entry<SmartHouse, Invoice> entry : recentInvoiceMap.entrySet()) {
            smartHouseInvoiceHashMap.put(entry.getKey(), entry.getValue().clone());
        }
        return smartHouseInvoiceHashMap;
    }

    public void setRecentInvoiceMap(Map<SmartHouse, Invoice> recentInvoiceMap) {
        this.recentInvoiceMap = recentInvoiceMap;
    }

    public Provider clone() {
        return new Provider(this);
    }

    @Override
    public int compareTo(Provider o) {
        return Double.compare(this.invoicingVolume(), o.invoicingVolume());
    }

    public double dailyCost(SmartHouse house) {
        return dailyCostAlgorithm.dailyCostInterface(this, house);
    }

    public Double invoicingVolume() {
        double r = 0;
        for (Invoice invoice : this.recentInvoiceMap.values()) {
            r += invoice.getCost();
        }
        for (Set<Invoice> invoices : this.oldInvoiceMap.values()) {
            for (Invoice invoice : invoices) {
                r += invoice.getCost();
            }
        }
        return r;
    }

    public Invoice emitirFatura(SmartHouse house, LocalDate start, LocalDate end) {
        long days = ChronoUnit.DAYS.between(start, end);
        Invoice invoice = new Invoice(start, end, house.totalConsumption()*days, this.dailyCost(house)*days);
        this.recentInvoiceMap.put();
        return invoice.clone();
    }
}
