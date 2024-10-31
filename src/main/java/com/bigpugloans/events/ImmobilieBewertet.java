package com.bigpugloans.events;

import java.io.Serializable;
import java.util.Objects;

public class ImmobilieBewertet implements Serializable {
    private String antragsnummer;
    private int beleihungswert;
    private int minimalerMarktwert;
    private int maximalerMarktwert;
    private int durchschnittlicherMarktwertVon;
    private int durchschnittlicherMarktwertBis;

    public String getAntragsnummer() {
        return antragsnummer;
    }

    public void setAntragsnummer(String antragsnummer) {
        this.antragsnummer = antragsnummer;
    }

    public int getBeleihungswert() {
        return beleihungswert;
    }

    public void setBeleihungswert(int beleihungswert) {
        this.beleihungswert = beleihungswert;
    }

    public int getMinimalerMarktwert() {
        return minimalerMarktwert;
    }

    public void setMinimalerMarktwert(int minimalerMarktwert) {
        this.minimalerMarktwert = minimalerMarktwert;
    }

    public int getMaximalerMarktwert() {
        return maximalerMarktwert;
    }

    public void setMaximalerMarktwert(int maximalerMarktwert) {
        this.maximalerMarktwert = maximalerMarktwert;
    }

    public int getDurchschnittlicherMarktwertVon() {
        return durchschnittlicherMarktwertVon;
    }

    public void setDurchschnittlicherMarktwertVon(int durchschnittlicherMarktwertVon) {
        this.durchschnittlicherMarktwertVon = durchschnittlicherMarktwertVon;
    }

    public int getDurchschnittlicherMarktwertBis() {
        return durchschnittlicherMarktwertBis;
    }

    public void setDurchschnittlicherMarktwertBis(int durchschnittlicherMarktwertBis) {
        this.durchschnittlicherMarktwertBis = durchschnittlicherMarktwertBis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImmobilieBewertet that = (ImmobilieBewertet) o;
        return Objects.equals(antragsnummer, that.antragsnummer);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(antragsnummer);
    }

    @Override
    public String toString() {
        return "ImmobilieBewertet{" +
                "antragsnummer='" + antragsnummer + '\'' +
                ", beleihungswert=" + beleihungswert +
                ", minimalerMarktwert=" + minimalerMarktwert +
                ", maximalerMarktwert=" + maximalerMarktwert +
                ", durchschnittlicherMarktwertVon=" + durchschnittlicherMarktwertVon +
                ", durchschnittlicherMarktwertBis=" + durchschnittlicherMarktwertBis +
                '}';
    }
}
