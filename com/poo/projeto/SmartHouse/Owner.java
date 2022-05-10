package com.poo.projeto.SmartHouse;

import java.util.Objects;

public class Owner {
    private String name;
    private String nif;

    public Owner() {
        this.name = "";
        this.nif = "";
    }

    public Owner(String name, String nif) {
        this.name = name;
        this.nif = nif;
    }

    public Owner(Owner owner) {
        this.name = owner.getName();
        this.nif = owner.getNif();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "name='" + name + '\'' +
                ", NIF='" + nif + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return Objects.equals(name, owner.name) && Objects.equals(nif, owner.nif);
    }

    @Override
    public Owner clone(){
        return new Owner(this);
    }

}
