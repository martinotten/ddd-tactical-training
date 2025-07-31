package com.bigpugloans.scoring.domain.model.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.*;

import java.util.Objects;
import java.util.Optional;

public class AntragstellerCluster implements ClusterScoring {
    private final ScoringId scoringId;

    private Wohnort wohnort;
    private Guthaben guthabenBeiMopsBank;


    public AntragstellerCluster(ScoringId scoringId) {
        if(scoringId == null) {
            throw new IllegalArgumentException("ScoringId darf nicht null sein.");
        }
        this.scoringId = scoringId;
        this.guthabenBeiMopsBank = new Guthaben(0);
    }

    public Optional<ClusterGescored> scoren() {
        if(wohnort == null) {
            return Optional.empty();
        }
        if (guthabenBeiMopsBank == null) {
            return Optional.empty();
        }

        Punkte ergebnis = new Punkte(0);
        ergebnis = ergebnis.plus(wohnort.berechnePunkte());
        ergebnis = ergebnis.plus(guthabenBeiMopsBank.berechnePunkte());

        return Optional.of(new ClusterGescored(this.scoringId, ergebnis));
    }

    public ScoringId scoringId() {
        return scoringId;
    }

    public void wohnortHinzufuegen(String wohnort) {
        this.wohnort = new Wohnort(wohnort);
    }

    public void guthabenHinzufuegen(Waehrungsbetrag guthaben) {
        this.guthabenBeiMopsBank = new Guthaben(guthaben);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AntragstellerCluster that = (AntragstellerCluster) o;
        return Objects.equals(scoringId, that.scoringId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(scoringId);
    }
}
