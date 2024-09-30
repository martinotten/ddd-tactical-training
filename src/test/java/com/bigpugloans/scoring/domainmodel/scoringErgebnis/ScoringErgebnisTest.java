package com.bigpugloans.scoring.domainmodel.scoringErgebnis;

import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.ScoringFarbe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoringErgebnisTest {

    @Test
    void punkteGroesserGleich120ErgibtGruenesScoringErgebnis() {
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis();
        scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(new Punkte(30), 0));
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(new Punkte(30), 0));
        scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(new Punkte(30), 0));
        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new Punkte(30), 0));
        assertEquals(ScoringFarbe.GRUEN, scoringErgebnis.berechneErgebnis(), "120 oder mehr Punkte sollten ein grünes Scoring-Ergebnis liefern.");

        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new Punkte(50), 0));  // mehr als 120 Punkte
        assertEquals(ScoringFarbe.GRUEN, scoringErgebnis.berechneErgebnis(), "Mehr als 120 Punkte sollten ein grünes Scoring-Ergebnis liefern.");
    }

    @Test
    void punkteWenigerAls120ErgibtRotesScoringErgebnis() {
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis();
        scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(new Punkte(30), 0));
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(new Punkte(30), 0));
        scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(new Punkte(30), 0));
        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new Punkte(29), 0));
        assertEquals(ScoringFarbe.ROT, scoringErgebnis.berechneErgebnis(), "Weniger als 120 Punkte sollten ein rotes Scoring-Ergebnis liefern.");
    }

    @Test
    void koKriteriumErgibtImmerRotesScoringErgebnisUnabhaengigVonPunkten() {
        // Fall 1: KO Kriterium vorhanden, aber viele Punkte
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis();
        scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(new Punkte(30), 0));
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(new Punkte(30), 0));
        scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(new Punkte(60), 1));
        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new Punkte(30), 0));


        assertEquals(ScoringFarbe.ROT, scoringErgebnis.berechneErgebnis(), "Ein KO Kriterium sollte immer ein rotes Scoring-Ergebnis liefern, egal wie viele Punkte.");

        // Fall 2: KO Kriterium vorhanden, aber wenige Punkte
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis();
        scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(new Punkte(30), 0));
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(new Punkte(30), 1));
        scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(new Punkte(20), 1));
        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new Punkte(20), 0));

        assertEquals(ScoringFarbe.ROT, scoringErgebnis.berechneErgebnis(), "Ein KO Kriterium sollte immer ein rotes Scoring-Ergebnis liefern, unabhängig von den Punkten.");
    }
}
