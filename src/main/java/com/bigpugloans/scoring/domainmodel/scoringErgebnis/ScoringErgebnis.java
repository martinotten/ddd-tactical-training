package com.bigpugloans.scoring.domainmodel.scoringErgebnis;

import com.bigpugloans.scoring.domainmodel.ClusterGescored;
import com.bigpugloans.scoring.domainmodel.KoKriterien;
import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.ScoringFarbe;

public class ScoringErgebnis {
    private ClusterGescored antragstellerClusterErgebnis;
    private ClusterGescored auskunfteiClusterErgebnis;
    private ClusterGescored immobilienFinanzierungsClusterErgebnis;
    private ClusterGescored monatlicherHaushaltsueberschussClusterErgebnis;

    private KoKriterien koKriterien;
    private Punkte gesamtPunkte;

    public ScoringErgebnis() {
        this.gesamtPunkte = new Punkte(0);
        this.koKriterien = new KoKriterien(0);
    }


    public ScoringFarbe berechneErgebnis() {
        if (koKriterien.anzahl() > 0) {
            return ScoringFarbe.ROT;
        }

        if(antragstellerClusterErgebnis != null
                && auskunfteiClusterErgebnis !=null
                && immobilienFinanzierungsClusterErgebnis !=null
                && monatlicherHaushaltsueberschussClusterErgebnis !=null) {

            if (gesamtPunkte.groesserAls(new Punkte(119))) {
                return ScoringFarbe.GRUEN;
            } else {
                return ScoringFarbe.ROT;
            }
        } else {
            throw new IllegalStateException("Es fehlen Cluster-Ergebnisse.");
        }
    }

    public void auskunfteiErgebnisClusterHinzufuegen(ClusterGescored clusterGescored) {
        this.auskunfteiClusterErgebnis = clusterGescored;
        this.gesamtPunkte = gesamtPunkte.plus(clusterGescored.punkte());
        this.koKriterien = new KoKriterien(this.koKriterien.anzahl() + clusterGescored.koKriterien().anzahl());
    }


    public void antragstellerClusterHinzufuegen(ClusterGescored clusterGescored) {
        this.antragstellerClusterErgebnis = clusterGescored;
        this.gesamtPunkte = gesamtPunkte.plus(clusterGescored.punkte());
        this.koKriterien = new KoKriterien(this.koKriterien.anzahl() + clusterGescored.koKriterien().anzahl());
    }

    public void immobilienFinanzierungClusterHinzufuegen(ClusterGescored clusterGescored) {
        this.immobilienFinanzierungsClusterErgebnis = clusterGescored;
        this.gesamtPunkte = gesamtPunkte.plus(clusterGescored.punkte());
        this.koKriterien = new KoKriterien(this.koKriterien.anzahl() + clusterGescored.koKriterien().anzahl());
    }


    public void monatlicheFinansituationClusterHinzufuegen(ClusterGescored clusterGescored) {
        this.monatlicherHaushaltsueberschussClusterErgebnis = clusterGescored;
        this.gesamtPunkte = gesamtPunkte.plus(clusterGescored.punkte());
        this.koKriterien = new KoKriterien(this.koKriterien.anzahl() + clusterGescored.koKriterien().anzahl());
    }
}
