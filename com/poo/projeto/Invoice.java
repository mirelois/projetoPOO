package com.poo.projeto;

import java.time.LocalDate;
import java.util.Objects;

public class Invoice {
    private LocalDate start;
    private LocalDate end;
    private Integer consumption;
    private Integer cost;

    public Invoice() {
        this.start = LocalDate.EPOCH;
        this.end = LocalDate.EPOCH;
        this.consumption = 0;
        this.cost = 0;
    }

    public Invoice(LocalDate start, LocalDate end, Integer consumption, Integer cost) {
        this.start = start;
        this.end = end;
        this.consumption = consumption;
        this.cost = cost;
    }

    public Invoice(Invoice invoice){
        this.start = invoice.getStart();
        this.end = invoice.getEnd();
        this.consumption = invoice.getConsumption();
        this.cost = invoice.getCost();
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

    public Integer getConsumption() {
        return consumption;
    }

    public void setConsumption(Integer consumption) {
        this.consumption = consumption;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
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

}
