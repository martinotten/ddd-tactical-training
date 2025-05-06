package com.bigpugloans.scoring.domainmodel.scoringErgebnis;

import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.ScoringErgebnis;
import com.bigpugloans.scoring.domainmodel.ScoringFarbe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ScoringErgebnis Tests")
public class ScoringErgebnisTest {

    private ScoringErgebnis scoringErgebnis;

    @BeforeEach
    void setUp() {
        scoringErgebnis = new ScoringErgebnis();
    }

    @Nested
    @DisplayName("Punktebasierte Ergebnisse ohne KO-Kriterium")
    class PunktebasierteErgebnisse {
        @Test
        @DisplayName("Genau 120 Punkte ergeben ein grünes Scoring-Ergebnis")
        void punkteGleich120ErgibtGruenesScoringErgebnis() {
            scoringErgebnis.setPunkte(new Punkte(120));
            assertEquals(ScoringFarbe.GRUEN, scoringErgebnis.berechneErgebnis(),
                        "120 Punkte sollten zu einem grünen Ergebnis führen.");
        }

        @ParameterizedTest
        @ValueSource(ints = {121, 130, 150, 200})
        @DisplayName("Mehr als 120 Punkte ergeben ein grünes Scoring-Ergebnis")
        void punkteGroesser120ErgibtGruenesScoringErgebnis(int punkteWert) {
            scoringErgebnis.setPunkte(new Punkte(punkteWert));
            assertEquals(ScoringFarbe.GRUEN, scoringErgebnis.berechneErgebnis(),
                        punkteWert + " Punkte sollten zu einem grünen Ergebnis führen.");
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 50, 100, 119})
        @DisplayName("Weniger als 120 Punkte ergeben ein rotes Scoring-Ergebnis")
        void punkteWenigerAls120ErgibtRotesScoringErgebnis(int punkteWert) {
            scoringErgebnis.setPunkte(new Punkte(punkteWert));
            assertEquals(ScoringFarbe.ROT, scoringErgebnis.berechneErgebnis(),
                        punkteWert + " Punkte sollten zu einem roten Ergebnis führen.");
        }
    }

    @Nested
    @DisplayName("Ergebnisse mit KO-Kriterium")
    class KoKriteriumErgebnisse {

        @ParameterizedTest
        @ValueSource(ints = {0, 50, 119, 120, 130, 150, 200})
        @DisplayName("KO-Kriterium führt immer zu einem roten Scoring-Ergebnis unabhängig von Punkten")
        void koKriteriumErgibtRotesScoringErgebnis(int punkteWert) {
            scoringErgebnis.setPunkte(new Punkte(punkteWert));
            scoringErgebnis.setKoKriterium(true);
            assertEquals(ScoringFarbe.ROT, scoringErgebnis.berechneErgebnis(),
                        "KO-Kriterium sollte immer zu einem roten Ergebnis führen, unabhängig von der Punktzahl (" + punkteWert + ").");
        }
    }
}
