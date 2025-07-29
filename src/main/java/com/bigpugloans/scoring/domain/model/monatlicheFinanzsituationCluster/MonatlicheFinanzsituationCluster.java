package com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domain.model.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class MonatlicheFinanzsituationCluster implements ClusterScoring {
    private final ScoringId scoringId;
    private Waehrungsbetrag einnahmen;
    private Waehrungsbetrag ausgaben;
    private Waehrungsbetrag neueDarlehensBelastungen;

    public MonatlicheFinanzsituationCluster(ScoringId scoringId) {
        if(scoringId == null) {
            throw new IllegalArgumentException("ScoringId darf nicht null sein.");
        }
        this.scoringId = scoringId;
    }

    public ScoringId scoringId() {
        return scoringId;
    }

    public KoKriterien pruefeKoKriterium() {
        if(einnahmen
                .minus(ausgaben)
                .minus(neueDarlehensBelastungen)
                .kleinerAls(new Waehrungsbetrag(0))) {
            return new KoKriterien(1);
        } else {
            return new KoKriterien(0);
        }
    }

    public Punkte berechnePunkte() {
        Waehrungsbetrag monatlicherUeberschussOhneTilgungen = einnahmen.minus(ausgaben);
        if(monatlicherUeberschussOhneTilgungen.groesserAls(new Waehrungsbetrag(1500))) {
            return new Punkte(15);
        } else {
            return new Punkte(0);
        }
    }

    public Optional<ClusterGescored> scoren() {
        if(einnahmen == null) {
            return Optional.empty();
        }
        if(ausgaben == null) {
            return Optional.empty();
        }
        if(neueDarlehensBelastungen == null) {
            return Optional.empty();
        }
        return Optional.of(new ClusterGescored(this.scoringId, berechnePunkte(), pruefeKoKriterium()));
    }

    public void monatlicheEinnahmenHinzufuegen(Waehrungsbetrag monatlicheEinnahmen) {
        this.einnahmen = monatlicheEinnahmen;
    }

    public void monatlicheAusgabenHinzufuegen(Waehrungsbetrag monatlicheAusgaben) {
        this.ausgaben = monatlicheAusgaben;
    }

    public void monatlicheDarlehensbelastungenHinzufuegen(Waehrungsbetrag monatlicheDarlehensBelastungen) {
        this.neueDarlehensBelastungen = monatlicheDarlehensBelastungen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonatlicheFinanzsituationCluster that = (MonatlicheFinanzsituationCluster) o;
        return Objects.equals(scoringId, that.scoringId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(scoringId);
    }

    public static MonatlicheFinanzsituationCluster fromMemento(MonatlicheFinanzsituationClusterMemento memento) {
        MonatlicheFinanzsituationCluster cluster = new MonatlicheFinanzsituationCluster(memento.scoringId());
        if(memento.einnahmen != null) {
            cluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(memento.einnahmen));
        }
        if(memento.ausgaben != null) {
            cluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(memento.ausgaben));
        }
        if(memento.neueDarlehensBelastungen != null) {
            cluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(memento.neueDarlehensBelastungen));
        }
        return cluster;
    }

    public MonatlicheFinanzsituationClusterMemento memento() {
        BigDecimal betragEinnahmen = null;
        if (einnahmen != null) {
            betragEinnahmen = einnahmen.betrag();
        }

        BigDecimal betragAusgaben = null;
        if (ausgaben != null) {
            betragAusgaben = ausgaben.betrag();
        }

        BigDecimal betragNeueDarlehensbelastungen = null;
        if (neueDarlehensBelastungen != null) {
            betragNeueDarlehensbelastungen = neueDarlehensBelastungen.betrag();
        }
        return new MonatlicheFinanzsituationClusterMemento(scoringId, betragEinnahmen, betragAusgaben, betragNeueDarlehensbelastungen);
    }

    public record MonatlicheFinanzsituationClusterMemento(ScoringId scoringId, BigDecimal einnahmen, BigDecimal ausgaben, BigDecimal neueDarlehensBelastungen) {}

}
