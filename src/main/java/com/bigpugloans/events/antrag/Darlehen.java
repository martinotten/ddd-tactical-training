package com.bigpugloans.events.antrag;

import java.util.Objects;

public class Darlehen {
    private int darlehensHoehe;
    private double zinssatz;
    private int tilgung;
    private int zinsBindung;
    private int monatlicheBelastung;

    public int getDarlehensHoehe() {
        return darlehensHoehe;
    }

    public void setDarlehensHoehe(int darlehensHoehe) {
        this.darlehensHoehe = darlehensHoehe;
    }

    public double getZinssatz() {
        return zinssatz;
    }

    public void setZinssatz(double zinssatz) {
        this.zinssatz = zinssatz;
    }

    public int getTilgung() {
        return tilgung;
    }

    public void setTilgung(int tilgung) {
        this.tilgung = tilgung;
    }

    public int getZinsBindung() {
        return zinsBindung;
    }

    public void setZinsBindung(int zinsBindung) {
        this.zinsBindung = zinsBindung;
    }

    public int getMonatlicheBelastung() {
        return monatlicheBelastung;
    }

    public void setMonatlicheBelastung(int monatlicheBelastung) {
        this.monatlicheBelastung = monatlicheBelastung;
    }

    @Override
    public String toString() {
        return "Darlehen{" +
                "darlehensHoehe=" + darlehensHoehe +
                ", zinssatz=" + zinssatz +
                ", tilgung=" + tilgung +
                ", zinsBindung=" + zinsBindung +
                ", monatlicheBelastung=" + monatlicheBelastung +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Darlehen darlehen = (Darlehen) o;
        return darlehensHoehe == darlehen.darlehensHoehe && Double.compare(zinssatz, darlehen.zinssatz) == 0 && tilgung == darlehen.tilgung && zinsBindung == darlehen.zinsBindung && monatlicheBelastung == darlehen.monatlicheBelastung;
    }

    @Override
    public int hashCode() {
        return Objects.hash(darlehensHoehe, zinssatz, tilgung, zinsBindung, monatlicheBelastung);
    }
}
