package com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.KoKriterien;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
class Warnung {
    private int anzahlWarnungen;

    private Warnung() {}

    public Warnung(int anzahlWarnungen) {
        this.anzahlWarnungen = anzahlWarnungen;
    }

    public KoKriterien bestimmeKoKriterien() {
        if(anzahlWarnungen > 3) {
            return new KoKriterien(1);
        } else {
            return new KoKriterien(0);
        }
    }

    @Override
    public String toString() {
        return "Warnung{" +
                "anzahlWarnungen=" + anzahlWarnungen +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warnung warnung = (Warnung) o;
        return anzahlWarnungen == warnung.anzahlWarnungen;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(anzahlWarnungen);
    }
}
