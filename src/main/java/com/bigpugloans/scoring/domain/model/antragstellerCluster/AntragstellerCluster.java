package com.bigpugloans.scoring.domain.model.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.*;

import java.math.BigDecimal;
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

    public AntragstellerCluster(AntragstellerClusterMemento memento) {
        this.scoringId = memento.scoringId();

        if(scoringId == null) {
            throw new IllegalArgumentException("ScoringId darf nicht null sein.");
        }

        if(memento.wohnort() == null) {
            this.wohnort = null;
        } else {
            this.wohnort = new Wohnort(memento.wohnort());
        }

        if(memento.guthaben == null) {
            this.guthabenBeiMopsBank = new Guthaben(0);
        } else {
            this.guthabenBeiMopsBank = new Guthaben(memento.guthaben());
        }

    }

    public static AntragstellerCluster fromMemento(AntragstellerClusterMemento memento) {
        return new AntragstellerCluster(memento);
    }

    public Optional<ClusterGescored> scoren() {
        if(wohnort == null || guthabenBeiMopsBank == null) {
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

    public AntragstellerClusterMemento memento() {
        ScoringId scoringId = this.scoringId;
        String wohnort = this.wohnort == null ? null : this.wohnort.wohnort();
        BigDecimal guthaben = this.guthabenBeiMopsBank == null ? null : this.guthabenBeiMopsBank.guthaben();
        return new AntragstellerClusterMemento(scoringId, wohnort, guthaben);
    }
    public record AntragstellerClusterMemento(ScoringId scoringId, String wohnort, BigDecimal guthaben) {

    }
}
