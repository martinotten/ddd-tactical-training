package com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.KoKriterien;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
class NegativMerkmal {
    private int anzahlNegativMerkmale;

    private NegativMerkmal() {}

    public NegativMerkmal(int anzahlNegativMerkmale) {
        this.anzahlNegativMerkmale = anzahlNegativMerkmale;
    }

    public KoKriterien bestimmeKoKriterien() {
        if(anzahlNegativMerkmale > 0) {
            return new KoKriterien(1);
        } else {
            return new KoKriterien(0);
        }
    }
    @Override
    public String toString() {
        return "NegativMerkmal{" +
                "anzahlNegativMerkmale=" + anzahlNegativMerkmale +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NegativMerkmal that = (NegativMerkmal) o;
        return anzahlNegativMerkmale == that.anzahlNegativMerkmale;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(anzahlNegativMerkmale);
    }
}
