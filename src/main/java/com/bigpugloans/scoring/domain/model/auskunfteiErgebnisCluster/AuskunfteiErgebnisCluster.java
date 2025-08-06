package com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.*;
import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;
import org.jmolecules.ddd.annotation.ValueObject;

import java.util.Objects;
import java.util.Optional;

@DomainModelRing
@AggregateRoot
public class AuskunfteiErgebnisCluster implements ClusterScoring {
    @Identity
    private final AntragstellerID antragstellerID;
    private final ScoringId scoringId;

    private NegativMerkmal negativMerkmale;
    private Warnung warnungen;
    private RueckzahlungsWahrscheinlichkeit rueckzahlungswahrscheinlichkeit;

    public AuskunfteiErgebnisCluster(ScoringId scoringId, AntragstellerID antragstellerID) {
        if(scoringId == null) {
            throw new IllegalArgumentException("ScoringId darf nicht null sein.");
        }
        if(antragstellerID == null) {
            throw new IllegalArgumentException("AntragstellerID darf nicht null sein.");
        }
        this.warnungen = new Warnung(0);
        this.negativMerkmale = new NegativMerkmal(0);
        this.scoringId = scoringId;
        this.antragstellerID = antragstellerID;
    }

    public AntragstellerID antragstellerID() {
        return antragstellerID;
    }

    public ScoringId scoringId() {
        return scoringId;
    }

    private KoKriterien pruefeKoKriterium() {
        int anzahlKoKriterien = 0;

        anzahlKoKriterien += this.warnungen.bestimmeKoKriterien().anzahl();
        anzahlKoKriterien += this.negativMerkmale.bestimmeKoKriterien().anzahl();
        anzahlKoKriterien += this.rueckzahlungswahrscheinlichkeit.bestimmeKoKriterien().anzahl();
        return new KoKriterien(anzahlKoKriterien);
    }

    private Punkte berechnePunkte() {
        return this.rueckzahlungswahrscheinlichkeit.berechnePunkte();
    }

    public Optional<ClusterGescored> scoren() {
        if(rueckzahlungswahrscheinlichkeit == null) {
            return Optional.empty();
        }
        return Optional.of(new ClusterGescored(this.scoringId, berechnePunkte(), pruefeKoKriterium()));
    }

    public void negativMerkmaleHinzufuegen(int anzahlNegativMerkmale) {
        this.negativMerkmale = new NegativMerkmal(anzahlNegativMerkmale);
    }

    public void warnungenHinzufuegen(int anzahlWarnungen) {
        this.warnungen = new Warnung(anzahlWarnungen);
    }

    public void rueckzahlungsWahrscheinlichkeitHinzufuegen(Prozentwert rueckzahlungsWahrscheinlichkeit) {
        this.rueckzahlungswahrscheinlichkeit = new RueckzahlungsWahrscheinlichkeit(rueckzahlungsWahrscheinlichkeit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuskunfteiErgebnisCluster that = (AuskunfteiErgebnisCluster) o;
        return Objects.equals(antragstellerID, that.antragstellerID) && Objects.equals(scoringId, that.scoringId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(antragstellerID, scoringId);
    }
}
