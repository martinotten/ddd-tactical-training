package com.bigpugloans.scoring.domain.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Punkte {
    private int punkte;

    private Punkte() {
    }
    public Punkte(int punkte) {
        this.punkte = punkte;
    }

    public int getPunkte() {
        return punkte;
    }

    public boolean groesserAls(Punkte anderePunkte) {
        return punkte > anderePunkte.punkte;
    }

    public Punkte plus(Punkte anderePunkte) {
        return new Punkte(punkte + anderePunkte.punkte);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Punkte punkte1 = (Punkte) o;
        return punkte == punkte1.punkte;
    }

    @Override
    public int hashCode() {
        return Objects.hash(punkte);
    }

    @Override
    public String toString() {
        return "Punkte{" +
                "punkte=" + punkte +
                '}';
    }
}
