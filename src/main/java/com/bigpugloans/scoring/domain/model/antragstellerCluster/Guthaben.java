package com.bigpugloans.scoring.domain.model.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.Punkte;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.ValueObject;

import java.util.Objects;

@DomainModelRing
@ValueObject
class Guthaben {
    private Waehrungsbetrag guthaben;

    private Guthaben() {
    }

    public Guthaben(Waehrungsbetrag guthaben) {
        this.guthaben = guthaben;
    }

    public Guthaben(int guthaben) {
        this.guthaben = new Waehrungsbetrag(guthaben);
    }

    public Punkte berechnePunkte() {
        if (guthaben.groesserAls(new Waehrungsbetrag(10000))) {
            return new Punkte(5);
        } else {
            return new Punkte(0);
        }
    }

    @Override
    public String toString() {
        return "Guthaben{" +
                "guthaben=" + guthaben +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guthaben guthaben1 = (Guthaben) o;
        return Objects.equals(guthaben, guthaben1.guthaben);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(guthaben);
    }
}
