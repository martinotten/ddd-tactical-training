package com.bigpugloans.scoring.domainmodel.scoringErgebnis;

import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.ScoringFarbe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoringErgebnisTest {

    @Test
    void punkteGroesserGleich120ErgibtGruenesScoringErgebnis() {
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis();
        scoringErgebnis.setPunkte(new Punkte(120));  // genau 120 Punkte
        assertEquals(ScoringFarbe.GRUEN, scoringErgebnis.berechneErgebnis(), "120 oder mehr Punkte sollten ein grünes Scoring-Ergebnis liefern.");

        scoringErgebnis.setPunkte(new Punkte(130));  // mehr als 120 Punkte
        assertEquals(ScoringFarbe.GRUEN, scoringErgebnis.berechneErgebnis(), "Mehr als 120 Punkte sollten ein grünes Scoring-Ergebnis liefern.");
    }

    @Test
    void punkteWenigerAls120ErgibtRotesScoringErgebnis() {
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis();
        scoringErgebnis.setPunkte(new Punkte(119));  // weniger als 120 Punkte
        assertEquals(ScoringFarbe.ROT, scoringErgebnis.berechneErgebnis(), "Weniger als 120 Punkte sollten ein rotes Scoring-Ergebnis liefern.");
    }

    @Test
    void koKriteriumErgibtImmerRotesScoringErgebnisUnabhaengigVonPunkten() {
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis();

        // Fall 1: KO Kriterium vorhanden, aber viele Punkte
        scoringErgebnis.setPunkte(new Punkte(150));  // viele Punkte
        scoringErgebnis.setKoKriterium(true);  // KO Kriterium vorhanden
        assertEquals(ScoringFarbe.ROT, scoringErgebnis.berechneErgebnis(), "Ein KO Kriterium sollte immer ein rotes Scoring-Ergebnis liefern, egal wie viele Punkte.");

        // Fall 2: KO Kriterium vorhanden, aber wenige Punkte
        scoringErgebnis.setPunkte(new Punkte(100));  // wenige Punkte
        scoringErgebnis.setKoKriterium(true);  // KO Kriterium vorhanden
        assertEquals(ScoringFarbe.ROT, scoringErgebnis.berechneErgebnis(), "Ein KO Kriterium sollte immer ein rotes Scoring-Ergebnis liefern, unabhängig von den Punkten.");
    }
}
