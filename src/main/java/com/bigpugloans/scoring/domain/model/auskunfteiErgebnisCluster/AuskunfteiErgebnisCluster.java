package com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.*;

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
}
