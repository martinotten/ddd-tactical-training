package com.bigpugloans.events.antrag;

import java.util.Objects;

public class Eigenmittel {
    private int barvermoegen;
    private int bausparguthaben;
    private int eigeneArbeitsleistung;
    private int summe;

    public int getBarvermoegen() {
        return barvermoegen;
    }

    public void setBarvermoegen(int barvermoegen) {
        this.barvermoegen = barvermoegen;
    }

    public int getBausparguthaben() {
        return bausparguthaben;
    }

    public void setBausparguthaben(int bausparguthaben) {
        this.bausparguthaben = bausparguthaben;
    }

    public int getEigeneArbeitsleistung() {
        return eigeneArbeitsleistung;
    }

    public void setEigeneArbeitsleistung(int eigeneArbeitsleistung) {
        this.eigeneArbeitsleistung = eigeneArbeitsleistung;
    }

    public int getSumme() {
        return summe;
    }

    public void setSumme(int summe) {
        this.summe = summe;
    }

    @Override
    public String toString() {
        return "Eigenmittel{" +
                "barvermoegen=" + barvermoegen +
                ", bausparguthaben=" + bausparguthaben +
                ", eigeneArbeitsleistung=" + eigeneArbeitsleistung +
                ", summe=" + summe +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Eigenmittel that = (Eigenmittel) o;
        return barvermoegen == that.barvermoegen && bausparguthaben == that.bausparguthaben && eigeneArbeitsleistung == that.eigeneArbeitsleistung && summe == that.summe;
    }

    @Override
    public int hashCode() {
        return Objects.hash(barvermoegen, bausparguthaben, eigeneArbeitsleistung, summe);
    }
}
