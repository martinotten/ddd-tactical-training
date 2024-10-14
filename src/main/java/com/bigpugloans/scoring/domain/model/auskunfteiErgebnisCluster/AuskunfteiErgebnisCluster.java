package com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.*;

import java.math.BigDecimal;
import java.util.Objects;

public class AuskunfteiErgebnisCluster {
    private final AntragstellerID antragstellerID;
    private final Antragsnummer antragsnummer;

    private NegativMerkmal negativMerkmale;
    private Warnung warnungen;
    private RueckzahlungsWahrscheinlichkeit rueckzahlungswahrscheinlichkeit;

    public AuskunfteiErgebnisCluster(Antragsnummer antragsnummer, AntragstellerID antragstellerID) {
        if(antragsnummer == null) {
            throw new IllegalArgumentException("Antragsnummer darf nicht null sein.");
        }
        if(antragstellerID == null) {
            throw new IllegalArgumentException("AntragstellerID darf nicht null sein.");
        }
        this.warnungen = new Warnung(0);
        this.negativMerkmale = new NegativMerkmal(0);
        this.antragsnummer = antragsnummer;
        this.antragstellerID = antragstellerID;
    }

    public AntragstellerID antragstellerID() {
        return antragstellerID;
    }

    public Antragsnummer antragsnummer() {
        return antragsnummer;
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

    public ClusterScoringEvent scoren() {
        if(rueckzahlungswahrscheinlichkeit == null) {
            return new ClusterKonnteNochNichtGescoredWerden(this.antragsnummer, "Die Rückzahlungswahrscheinlichkeit fehlt.");
        }
        return new ClusterGescored(this.antragsnummer, berechnePunkte(), pruefeKoKriterium());
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
        return Objects.equals(antragstellerID, that.antragstellerID) && Objects.equals(antragsnummer, that.antragsnummer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(antragstellerID, antragsnummer);
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

        return new AuskunfteiErgebnisClusterMemento(antragsnummer.nummer(), antragstellerID.id(), anzahlNegativMerkmale, anzahlWarnungen, rueckzahlungswahrscheinlichkeit);
    }

    public static AuskunfteiErgebnisCluster fromMemento(AuskunfteiErgebnisClusterMemento memento) {
        Antragsnummer antragsnummer = new Antragsnummer(memento.antragsnummer());
        AntragstellerID antragstellerID = new AntragstellerID(memento.antragstellerID());
        AuskunfteiErgebnisCluster cluster = new AuskunfteiErgebnisCluster(antragsnummer, antragstellerID);
        cluster.negativMerkmale = new NegativMerkmal(memento.anzahlNegativMerkmale());
        cluster.warnungen = new Warnung(memento.anzahlWarnungen());
        if (memento.rueckzahlungsWahrscheinlichkeit() != null) {
            cluster.rueckzahlungswahrscheinlichkeit = new RueckzahlungsWahrscheinlichkeit(new Prozentwert(memento.rueckzahlungsWahrscheinlichkeit()));
        }
        return cluster;
    }

    public record AuskunfteiErgebnisClusterMemento (String antragsnummer, String antragstellerID, int anzahlNegativMerkmale, int anzahlWarnungen, BigDecimal rueckzahlungsWahrscheinlichkeit) {}


}
