package com.bigpugloans.scoring.domain.model.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.Punkte;
import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.ValueObject;

import java.util.Objects;

@DomainModelRing
@ValueObject
class Wohnort {
    private String wohnort;

    private Wohnort() {}

    public Wohnort(String wohnort) {
        this.wohnort = wohnort;
    }

    public Punkte berechnePunkte() {
        if ("Hamburg".equalsIgnoreCase(wohnort)) {
            return new Punkte(5);
        } else if ("München".equalsIgnoreCase(wohnort)) {
            return new Punkte(5);
        } else {
            return new Punkte(0);
        }
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
