package com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class AuskunfteiErgebnisCluster implements ClusterScoring {
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

    public AuskunfteiErgebnisClusterMemento memento() {
        int anzahlNegativMerkmale = 0;
        if(this.negativMerkmale != null) {
            anzahlNegativMerkmale = this.negativMerkmale.anzahl();
        }

        int anzahlWarnungen = 0;
        if(this.warnungen != null) {
            anzahlWarnungen = this.warnungen.anzahl();
        }

        BigDecimal rueckzahlungswahrscheinlichkeit = null;
        if(this.rueckzahlungswahrscheinlichkeit != null) {
            rueckzahlungswahrscheinlichkeit = this.rueckzahlungswahrscheinlichkeit.rueckzahlungsWahrscheinlichkeit().getWert();
        }

        return new AuskunfteiErgebnisClusterMemento(scoringId, antragstellerID.id(), anzahlNegativMerkmale, anzahlWarnungen, rueckzahlungswahrscheinlichkeit);
    }

    public static AuskunfteiErgebnisCluster fromMemento(AuskunfteiErgebnisClusterMemento memento) {
        ScoringId scoringId1 = memento.scoringId();
        AntragstellerID antragstellerID = new AntragstellerID(memento.antragstellerID());
        AuskunfteiErgebnisCluster cluster = new AuskunfteiErgebnisCluster(scoringId1, antragstellerID);
        cluster.negativMerkmale = new NegativMerkmal(memento.anzahlNegativMerkmale());
        cluster.warnungen = new Warnung(memento.anzahlWarnungen());
        if (memento.rueckzahlungsWahrscheinlichkeit() != null) {
            cluster.rueckzahlungswahrscheinlichkeit = new RueckzahlungsWahrscheinlichkeit(new Prozentwert(memento.rueckzahlungsWahrscheinlichkeit()));
        }
        return cluster;
    }

    public record AuskunfteiErgebnisClusterMemento (ScoringId scoringId, String antragstellerID, int anzahlNegativMerkmale, int anzahlWarnungen, BigDecimal rueckzahlungsWahrscheinlichkeit) {}


}
