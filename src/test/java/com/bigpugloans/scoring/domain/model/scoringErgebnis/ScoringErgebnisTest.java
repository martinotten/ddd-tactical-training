package com.bigpugloans.scoring.domain.model.scoringErgebnis;

import com.bigpugloans.scoring.domain.model.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoringErgebnisTest {
    @Test
    void scoringErgebnisOhneAntragsnummerWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ScoringErgebnis(null);
        });
    }

    @Test
    void auskunfteiErgebnisClusterMitAndererAntragsnummerWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ScoringErgebnis scoringErgebnis = new ScoringErgebnis(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN));
            scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("456"), ScoringArt.MAIN), new Punkte(30), 0));
        });
    }

    void antragstellerClusterMitAndererAntragsnummerWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ScoringErgebnis scoringErgebnis = new ScoringErgebnis(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN));
            scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("456"), ScoringArt.MAIN), new Punkte(30), 0));
        });
    }

    void monatlicheFinansituationClusterMitAndererAntragsnummerWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ScoringErgebnis scoringErgebnis = new ScoringErgebnis(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN));
            scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("456"), ScoringArt.MAIN), new Punkte(30), 0));
        });
    }

    void immobilienFinanzierungClusterMitAndererAntragsnummerWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ScoringErgebnis scoringErgebnis = new ScoringErgebnis(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN));
            scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("456"), ScoringArt.MAIN), new Punkte(30), 0));
        });
    }

    @Test
    void fehlendeTeilergebnisseFuehrenZuClusterKonnteNichtGescoredWerden() {
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN));
        Optional<AntragErfolgreichGescored> ergebnisOptional = scoringErgebnis.berechneErgebnis();

        assert(ergebnisOptional.isEmpty());

        scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));
        ergebnisOptional = scoringErgebnis.berechneErgebnis();
        assert(ergebnisOptional.isEmpty());

        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));
        ergebnisOptional = scoringErgebnis.berechneErgebnis();
        assert(ergebnisOptional.isEmpty());

        scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));
        ergebnisOptional = scoringErgebnis.berechneErgebnis();
        assert(ergebnisOptional.isEmpty());

        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));
        ergebnisOptional = scoringErgebnis.berechneErgebnis();
        assert(ergebnisOptional.isPresent());
        var ergebnis = ergebnisOptional.get();
        assertEquals(AntragErfolgreichGescored.class, ergebnis.getClass(), "Alle Teilergebnisse vorhanden sollten zu einem AntragErfolgreichGescored führen.");
    }

    @Test
    void scoringErgebnisseMitGleicherAntragsnummerSindGleich() {
        Antragsnummer antragsnummer = new Antragsnummer("123");
        ScoringErgebnis scoringErgebnis1 = new ScoringErgebnis(new ScoringId(antragsnummer, ScoringArt.MAIN));
        scoringErgebnis1.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));
        scoringErgebnis1.antragstellerClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));
        scoringErgebnis1.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));
        scoringErgebnis1.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));

        ScoringErgebnis scoringErgebnis2 = new ScoringErgebnis(new ScoringId(antragsnummer, ScoringArt.MAIN));
        scoringErgebnis2.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(20), 0));
        scoringErgebnis2.antragstellerClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 1));
        scoringErgebnis2.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 2));

        assertEquals(scoringErgebnis1, scoringErgebnis2, "Beide ScoringErgebnisse sollten gleich sein.");
    }

    @Test
    void punkteGroesserGleich120ErgibtGruenesScoringErgebnis() {
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN));
        scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));
        scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));
        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));
        ScoringFarbe farbe = scoringErgebnis.berechneErgebnis().get().farbe();
        assertEquals(ScoringFarbe.GRUEN, farbe, "120 oder mehr Punkte sollten ein grünes Scoring-Ergebnis liefern.");

        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(50), 0));  // mehr als 120 Punkte
        farbe = scoringErgebnis.berechneErgebnis().get().farbe();
        assertEquals(ScoringFarbe.GRUEN, farbe, "Mehr als 120 Punkte sollten ein grünes Scoring-Ergebnis liefern.");
    }

    @Test
    void punkteWenigerAls120ErgibtRotesScoringErgebnis() {
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN));
        scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));
        scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));
        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(29), 0));
        ScoringFarbe farbe = scoringErgebnis.berechneErgebnis().get().farbe();
        assertEquals(ScoringFarbe.ROT, farbe, "Weniger als 120 Punkte sollten ein rotes Scoring-Ergebnis liefern.");
    }

    @Test
    void koKriteriumErgibtImmerRotesScoringErgebnisUnabhaengigVonPunkten() {
        // Fall 1: KO Kriterium vorhanden, aber viele Punkte
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN));
        scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));
        scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(60), 1));
        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));

        ScoringFarbe farbe = scoringErgebnis.berechneErgebnis().get().farbe();
        assertEquals(ScoringFarbe.ROT, farbe, "Ein KO Kriterium sollte immer ein rotes Scoring-Ergebnis liefern, egal wie viele Punkte.");

        // Fall 2: KO Kriterium vorhanden, aber wenige Punkte
        scoringErgebnis = new ScoringErgebnis(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN));
        scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 0));
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(30), 1));
        scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(20), 1));
        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN), new Punkte(20), 0));
        farbe = scoringErgebnis.berechneErgebnis().get().farbe();
        assertEquals(ScoringFarbe.ROT, farbe, "Ein KO Kriterium sollte immer ein rotes Scoring-Ergebnis liefern, unabhängig von den Punkten.");
    }
}
