package com.bigpugloans.scoring.domainmodel.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.Waehrungsbetrag;

import java.util.Objects;

class MarktwertVergleich {
    private Waehrungsbetrag minimalerMarktwert;
    private Waehrungsbetrag maximalerMarktwert;
    private Waehrungsbetrag durchschnittlicherMarktwertVon;
    private Waehrungsbetrag durchschnittlicherMarktwertBis;

    public MarktwertVergleich(Waehrungsbetrag minimalerMarktwert, Waehrungsbetrag maximalerMarktwert, Waehrungsbetrag durchschnittlicherMarktwertVon, Waehrungsbetrag durchschnittlicherMarktwertBis) {
        this.minimalerMarktwert = minimalerMarktwert;
        this.maximalerMarktwert = maximalerMarktwert;
        this.durchschnittlicherMarktwertVon = durchschnittlicherMarktwertVon;
        this.durchschnittlicherMarktwertBis = durchschnittlicherMarktwertBis;
    }

    @Override
    public String toString() {
        return "MartkwertVergleich{" +
                "minimalerMarktwert=" + minimalerMarktwert +
                ", maximalerMarktwert=" + maximalerMarktwert +
                ", durchschnittlicherMarktwertVon=" + durchschnittlicherMarktwertVon +
                ", durchschnittlicherMarktwertBis=" + durchschnittlicherMarktwertBis +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarktwertVergleich that = (MarktwertVergleich) o;
        return Objects.equals(minimalerMarktwert, that.minimalerMarktwert) && Objects.equals(maximalerMarktwert, that.maximalerMarktwert) && Objects.equals(durchschnittlicherMarktwertVon, that.durchschnittlicherMarktwertVon) && Objects.equals(durchschnittlicherMarktwertBis, that.durchschnittlicherMarktwertBis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minimalerMarktwert, maximalerMarktwert, durchschnittlicherMarktwertVon, durchschnittlicherMarktwertBis);
    }

    public Punkte berechnePunkte(Waehrungsbetrag marktwert) {
        if(marktwert.groesserAls(this.durchschnittlicherMarktwertVon) && marktwert.kleinerAls(this.durchschnittlicherMarktwertBis)) {
            return new Punkte(15);
        } else {
            return new Punkte(0);
        }
    }
}
