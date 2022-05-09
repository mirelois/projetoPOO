package com.poo.projeto.provider;

import com.poo.projeto.DailyCostAlgorithm.DailyCostAlgorithm;
import com.poo.projeto.Invoice;
import com.poo.projeto.SmartDevice;
import com.poo.projeto.SmartHouse;

import java.util.*;
import java.util.stream.Collectors;

public class Provider {
    private String name;
    private Map<SmartHouse, SortedSet<Invoice>> oldInvoiceMap;
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

    public Provider(String name, Map<SmartHouse, SortedSet<Invoice>> oldInvoiceMap, int discountFactor, DailyCostAlgorithm dailyCostAlgorithm, Map<SmartHouse, Invoice> recentInvoiceMap) {
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

    public Map<SmartHouse, SortedSet<Invoice>> getOldInvoiceMap() {
        HashMap<SmartHouse, SortedSet<Invoice>> invoiceMap = new HashMap<>();
        for (Map.Entry<SmartHouse, SortedSet<Invoice>> m : oldInvoiceMap.entrySet()) {
            this.oldInvoiceMap.put(m.getKey(), m.getValue().stream().map(Invoice::clone).collect(Collectors.toCollection(TreeSet::new)));
        }
        return invoiceMap;
    }

    public void setOldInvoiceMap(Map<SmartHouse, SortedSet<Invoice>> oldInvoiceMap) {
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

    public double dailyCost(SmartHouse house) {
        return dailyCostAlgorithm.dailyCostInterface(this, house);
    }

    public void setDailyCostAlgorithm(DailyCostAlgorithm dailyCostAlgorithm) {
        this.dailyCostAlgorithm = dailyCostAlgorithm;
    }

    public Provider clone() {
        return new Provider(this);
    }

    public Double invoicingVolume() {
        double r = 0;
        for (Invoice invoice : this.recentInvoiceMap.values()) {
            r += invoice.getCost();
        }
        for (SortedSet<Invoice> invoices : this.oldInvoiceMap.values()) {
            for (Invoice invoice : invoices) {
                r += invoice.getCost();
            }
        }
        return r;
    }
}
