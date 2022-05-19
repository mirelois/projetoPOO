package com.poo.projeto.Invoice;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Invoice implements Comparable<Invoice>, Serializable {
    private LocalDate start;
    private LocalDate end;
    private Double consumption;
    private Double cost;
    private String smartHouse;
    private String provider;

    public Invoice() {
        this.start = LocalDate.EPOCH;
        this.end = LocalDate.EPOCH;
        this.consumption = 0.0;
        this.cost = 0.0;
        this.smartHouse = "";
        this.provider = "";
    }

    public Invoice(LocalDate start, LocalDate end, Double consumption, Double cost, String smartHouse, String provider) {
        this.start = start;
        this.end = end;
        this.consumption = consumption;
        this.cost = cost;
        this.smartHouse = smartHouse;
        this.provider = provider;
    }

    public Invoice(Invoice invoice){
        this.start = invoice.getStart();
        this.end = invoice.getEnd();
        this.consumption = invoice.getConsumption();
        this.cost = invoice.getCost();
        this.smartHouse = invoice.getSmartHouse();
        this.provider = invoice.getProvider();
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getSmartHouse() {
        return smartHouse;
    }

    public void setSmartHouse(String smartHouse) {
        this.smartHouse = smartHouse;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(start, invoice.start) && Objects.equals(end, invoice.end) && Objects.equals(consumption, invoice.consumption) && Objects.equals(cost, invoice.cost);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "start=" + start +
                ", end=" + end +
                ", consumption=" + consumption +
                ", cost=" + cost +
                '}';
    }

    @Override
    public Invoice clone(){
        return new Invoice(this);
    }

    @Override
    public int compareTo(Invoice o) {
        return Double.compare(this.getConsumption(), o.getConsumption());
    }
}
