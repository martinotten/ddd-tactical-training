package com.bigpugloans.scoring.domain.model.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.Punkte;

import java.util.Objects;

class Wohnort {
    private final String wohnort;

    Wohnort(String wohnort) {
        if(wohnort == null) {
            throw new IllegalArgumentException("Wohnort darf nicht null sein.");
        }
        this.wohnort = wohnort;
    }

    Punkte berechnePunkte() {
        if ("Hamburg".equalsIgnoreCase(wohnort)) {
            return new Punkte(5);
        } else if ("München".equalsIgnoreCase(wohnort)) {
            return new Punkte(5);
        } else {
            return new Punkte(0);
        }
    }

    String wohnort() {
        return wohnort;
    }

    @Override
    public String toString() {
        return "Wohnort{" +
                "wohnort='" + wohnort + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wohnort wohnort1 = (Wohnort) o;
        return Objects.equals(wohnort, wohnort1.wohnort);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(wohnort);
    }
}
