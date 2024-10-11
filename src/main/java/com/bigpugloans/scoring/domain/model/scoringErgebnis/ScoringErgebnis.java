package com.bigpugloans.scoring.domain.model.scoringErgebnis;

import com.bigpugloans.scoring.domain.model.*;

import java.util.Objects;

public class ScoringErgebnis {
    private final Antragsnummer antragsnummer;

    private ClusterGescored antragstellerClusterErgebnis;
    private ClusterGescored auskunfteiClusterErgebnis;
    private ClusterGescored immobilienFinanzierungsClusterErgebnis;
    private ClusterGescored monatlicherHaushaltsueberschussClusterErgebnis;

    private KoKriterien koKriterien;
    private Punkte gesamtPunkte;

    public ScoringErgebnis(Antragsnummer antragsnummer) {
        if(antragsnummer == null) {
            throw new IllegalArgumentException("Antragsnummer darf nicht null sein.");
        }
        this.antragsnummer = antragsnummer;
        this.gesamtPunkte = new Punkte(0);
        this.koKriterien = new KoKriterien(0);
    }

    public Antragsnummer antragsnummer() {
        return antragsnummer;
    }

    public AntragScoringEvent berechneErgebnis() {
        if (koKriterien.anzahl() > 0) {
            return new AntragErfolgreichGescored(antragsnummer, ScoringFarbe.ROT);
        }

        if(antragstellerClusterErgebnis != null
                && auskunfteiClusterErgebnis !=null
                && immobilienFinanzierungsClusterErgebnis !=null
                && monatlicherHaushaltsueberschussClusterErgebnis !=null) {

            if (gesamtPunkte.groesserAls(new Punkte(119))) {
                return new AntragErfolgreichGescored(antragsnummer, ScoringFarbe.GRUEN);
            } else {
                return new AntragErfolgreichGescored(antragsnummer, ScoringFarbe.ROT);
            }
        } else {
            return new AntragKonnteNichtGescoredWerden(antragsnummer, "Es fehlen Teilergebnisse der Cluster.");
        }
    }

    public void auskunfteiErgebnisClusterHinzufuegen(ClusterGescored clusterGescored) {
        if(clusterGescored.antragsnummer().equals(this.antragsnummer)) {
            this.auskunfteiClusterErgebnis = clusterGescored;
            this.gesamtPunkte = gesamtPunkte.plus(clusterGescored.punkte());
            this.koKriterien = new KoKriterien(this.koKriterien.anzahl() + clusterGescored.koKriterien().anzahl());
        } else {
            throw new IllegalArgumentException(String.format(
                "Antragsnummer von ScoringErgebnis (%s) und ClusterGescored (%s) stimmt nicht überein.",
                    this.antragsnummer.nummer(),
                    clusterGescored.antragsnummer().nummer()
            ));
        }
    }


    public void antragstellerClusterHinzufuegen(ClusterGescored clusterGescored) {
        if(clusterGescored.antragsnummer().equals(this.antragsnummer)) {
            this.antragstellerClusterErgebnis = clusterGescored;
            this.gesamtPunkte = gesamtPunkte.plus(clusterGescored.punkte());
            this.koKriterien = new KoKriterien(this.koKriterien.anzahl() + clusterGescored.koKriterien().anzahl());
        } else {
            throw new IllegalArgumentException(String.format(
                "Antragsnummer von ScoringErgebnis (%s) und ClusterGescored (%s) stimmt nicht überein.",
                    this.antragsnummer.nummer(),
                    clusterGescored.antragsnummer().nummer()
            ));
        }
    }

    public void immobilienFinanzierungClusterHinzufuegen(ClusterGescored clusterGescored) {
        if (clusterGescored.antragsnummer().equals(this.antragsnummer)) {
            this.immobilienFinanzierungsClusterErgebnis = clusterGescored;
            this.gesamtPunkte = gesamtPunkte.plus(clusterGescored.punkte());
            this.koKriterien = new KoKriterien(this.koKriterien.anzahl() + clusterGescored.koKriterien().anzahl());
        } else {
            throw new IllegalArgumentException(String.format(
                "Antragsnummer von ScoringErgebnis (%s) und ClusterGescored (%s) stimmt nicht überein.",
                    this.antragsnummer.nummer(),
                    clusterGescored.antragsnummer().nummer()
            ));
        }
    }


    public void monatlicheFinansituationClusterHinzufuegen(ClusterGescored clusterGescored) {
        if(clusterGescored.antragsnummer().equals(this.antragsnummer)) {
            this.monatlicherHaushaltsueberschussClusterErgebnis = clusterGescored;
            this.gesamtPunkte = gesamtPunkte.plus(clusterGescored.punkte());
            this.koKriterien = new KoKriterien(this.koKriterien.anzahl() + clusterGescored.koKriterien().anzahl());
        } else {
            throw new IllegalArgumentException(String.format(
                "Antragsnummer von ScoringErgebnis (%s) und ClusterGescored (%s) stimmt nicht überein.",
                    this.antragsnummer.nummer(),
                    clusterGescored.antragsnummer().nummer()
            ));
        }
    }

    @Override
    public String toString() {
        return "ScoringErgebnis{" +
                "antragsnummer=" + antragsnummer +
                ", antragstellerClusterErgebnis=" + antragstellerClusterErgebnis +
                ", auskunfteiClusterErgebnis=" + auskunfteiClusterErgebnis +
                ", immobilienFinanzierungsClusterErgebnis=" + immobilienFinanzierungsClusterErgebnis +
                ", monatlicherHaushaltsueberschussClusterErgebnis=" + monatlicherHaushaltsueberschussClusterErgebnis +
                ", koKriterien=" + koKriterien +
                ", gesamtPunkte=" + gesamtPunkte +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoringErgebnis that = (ScoringErgebnis) o;
        return Objects.equals(antragsnummer, that.antragsnummer);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(antragsnummer);
    }
}
