package com.poo.projeto;

import java.util.Map;
import java.util.Set;

public class Provider {
    private String name;
    private Map<SmartHouse, Set<Invoice>> invoiceMap;
    private static int baseValueKWH, taxFactor;
    private int discountFactor;
    private DailyCostAlgorithm dailyCostAlgorithm;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<SmartHouse, Set<Invoice>> getInvoiceMap() {
        return invoiceMap;
    }

    public void setInvoiceMap(Map<SmartHouse, Set<Invoice>> invoiceMap) {
        this.invoiceMap = invoiceMap;
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
}
