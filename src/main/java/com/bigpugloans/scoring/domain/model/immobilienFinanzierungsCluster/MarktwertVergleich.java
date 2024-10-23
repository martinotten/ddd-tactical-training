package com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.Punkte;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import jakarta.persistence.*;

import java.util.Objects;

@Embeddable
class MarktwertVergleich {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "betrag", column = @Column(name = "minimalerMarktwert"))
    })
    private Waehrungsbetrag minimalerMarktwert;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "betrag", column = @Column(name = "maximalerMarktwert"))
    })
    private Waehrungsbetrag maximalerMarktwert;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "betrag", column = @Column(name = "durchschnittlicherMarktwertVon"))
    })
    private Waehrungsbetrag durchschnittlicherMarktwertVon;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "betrag", column = @Column(name = "durchschnittlicherMarktwertBis"))
    })
    private Waehrungsbetrag durchschnittlicherMarktwertBis;

    private MarktwertVergleich() {}

    public MarktwertVergleich(Waehrungsbetrag minimalerMarktwert, Waehrungsbetrag maximalerMarktwert, Waehrungsbetrag durchschnittlicherMarktwertVon, Waehrungsbetrag durchschnittlicherMarktwertBis) {
        if(minimalerMarktwert == null) {
            throw new IllegalArgumentException("Minimaler Marktwert darf nicht null sein.");
        }
        if(maximalerMarktwert == null) {
            throw new IllegalArgumentException("Maximaler Marktwert darf nicht null sein.");
        }
        if(durchschnittlicherMarktwertVon == null) {
            throw new IllegalArgumentException("Durchschnittlicher Marktwert von darf nicht null sein.");
        }
        if(durchschnittlicherMarktwertBis == null) {
            throw new IllegalArgumentException("Durchschnittlicher Marktwert bis darf nicht null sein.");
        }

        if(minimalerMarktwert.groesserAls(maximalerMarktwert)) {
            throw new IllegalArgumentException("Minimaler Marktwert darf nicht groesser als maximaler Marktwert sein.");
        }

        if(durchschnittlicherMarktwertVon.kleinerAls(minimalerMarktwert)) {
            throw new IllegalArgumentException("Durchschnittlicher Marktwert von darf nicht kleiner als minimaler Marktwert sein.");
        }

        if(durchschnittlicherMarktwertBis.groesserAls(maximalerMarktwert)) {
            throw new IllegalArgumentException("Durchschnittlicher Marktwert bis darf nicht groesser als maximaler Marktwert sein.");
        }

        if(durchschnittlicherMarktwertVon.groesserAls(durchschnittlicherMarktwertBis)) {
            throw new IllegalArgumentException("Durchschnittlicher Marktwert von darf nicht groesser als durchschnittlicher Marktwert bis sein.");
        }
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
