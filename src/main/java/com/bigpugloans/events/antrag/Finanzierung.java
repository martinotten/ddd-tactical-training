package com.bigpugloans.events.antrag;

import java.util.Date;
import java.util.Objects;

public class Finanzierung {
    private int finanzierungBedarf;

    private Darlehen darlehen1;
    private Darlehen darlehen2;
    private Darlehen darlehen3;
    private Darlehen darlehen4;

    public int getFinanzierungBedarf() {
        return finanzierungBedarf;
    }

    public void setFinanzierungBedarf(int finanzierungBedarf) {
        this.finanzierungBedarf = finanzierungBedarf;
    }

    public Darlehen getDarlehen1() {
        return darlehen1;
    }

    public void setDarlehen1(Darlehen darlehen1) {
        this.darlehen1 = darlehen1;
    }

    public Darlehen getDarlehen2() {
        return darlehen2;
    }

    public void setDarlehen2(Darlehen darlehen2) {
        this.darlehen2 = darlehen2;
    }

    public Darlehen getDarlehen3() {
        return darlehen3;
    }

    public void setDarlehen3(Darlehen darlehen3) {
        this.darlehen3 = darlehen3;
    }

    public Darlehen getDarlehen4() {
        return darlehen4;
    }

    public void setDarlehen4(Darlehen darlehen4) {
        this.darlehen4 = darlehen4;
    }

    @Override
    public String toString() {
        return "Finanzierung{" +
                "finanzierungBedarf=" + finanzierungBedarf +
                ", darlehen1=" + darlehen1 +
                ", darlehen2=" + darlehen2 +
                ", darlehen3=" + darlehen3 +
                ", darlehen4=" + darlehen4 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Finanzierung that = (Finanzierung) o;
        return finanzierungBedarf == that.finanzierungBedarf && Objects.equals(darlehen1, that.darlehen1) && Objects.equals(darlehen2, that.darlehen2) && Objects.equals(darlehen3, that.darlehen3) && Objects.equals(darlehen4, that.darlehen4);
    }

    @Override
    public int hashCode() {
        return Objects.hash(finanzierungBedarf, darlehen1, darlehen2, darlehen3, darlehen4);
    }
}
