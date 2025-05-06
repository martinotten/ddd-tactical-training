package com.bigpugloans.scoring.domainmodel.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domainmodel.Prozentwert;
import com.bigpugloans.scoring.domainmodel.Punkte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("AuskunfteiErgebnisCluster Tests")
public class AuskunfteiErgebnisClusterTest {

    private AuskunfteiErgebnisCluster auskunfteiErgebnisCluster;

    @BeforeEach
    void setUp() {
        auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
    }

    @Nested
    @DisplayName("KO-Kriterien Prüfungen")
    class KoKriterienTests {
        @Test
        @DisplayName("Negativmerkmal ist ein KO-Kriterium")
        void negativMerkmalIstKoKriterium() {
            auskunfteiErgebnisCluster.setNegativMerkmal(true);
            assertTrue(auskunfteiErgebnisCluster.pruefeKoKriterium(), "Negativmerkmal sollte ein KO-Kriterium sein.");
        }

        @Test
        @DisplayName("Kein Negativmerkmal ist kein KO-Kriterium")
        void keinNegativMerkmalIstKeinKoKriterium() {
            auskunfteiErgebnisCluster.setNegativMerkmal(false);
            assertFalse(auskunfteiErgebnisCluster.pruefeKoKriterium(), "Kein Negativmerkmal sollte kein KO-Kriterium sein.");
        }

        @ParameterizedTest
        @ValueSource(ints = {4, 5, 10})
        @DisplayName("Mehr als drei Warnungen sind ein KO-Kriterium")
        void mehrAlsDreiWarnungenSindKoKriterium(int warnungen) {
            auskunfteiErgebnisCluster.setWarnungen(warnungen);
            assertTrue(auskunfteiErgebnisCluster.pruefeKoKriterium(),
                      "Warnungsanzahl " + warnungen + " sollte ein KO-Kriterium auslösen.");
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        @DisplayName("Bis zu drei Warnungen sind kein KO-Kriterium")
        void bisZuDreiWarnungenSindKeinKoKriterium(int warnungen) {
            auskunfteiErgebnisCluster.setWarnungen(warnungen);
            assertFalse(auskunfteiErgebnisCluster.pruefeKoKriterium(),
                       "Warnungsanzahl " + warnungen + " sollte kein KO-Kriterium auslösen.");
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 30, 59})
        @DisplayName("Rückzahlungswahrscheinlichkeit unter 60% ist ein KO-Kriterium")
        void rueckzahlungswahrscheinlichkeitUnter60IstKoKriterium(int prozent) {
            auskunfteiErgebnisCluster.setRueckzahlungswahrscheinlichkeit(new Prozentwert(prozent));
            assertTrue(auskunfteiErgebnisCluster.pruefeKoKriterium(),
                      "Rückzahlungswahrscheinlichkeit von " + prozent + "% sollte ein KO-Kriterium sein.");
        }

        @ParameterizedTest
        @ValueSource(ints = {60, 75, 100})
        @DisplayName("Rückzahlungswahrscheinlichkeit ab 60% ist kein KO-Kriterium")
        void rueckzahlungswahrscheinlichkeitAb60IstKeinKoKriterium(int prozent) {
            auskunfteiErgebnisCluster.setRueckzahlungswahrscheinlichkeit(new Prozentwert(prozent));
            assertFalse(auskunfteiErgebnisCluster.pruefeKoKriterium(),
                       "Rückzahlungswahrscheinlichkeit von " + prozent + "% sollte kein KO-Kriterium sein.");
        }

        @Test
        @DisplayName("Kombinierte KO-Kriterien lösen KO aus")
        void kombinierteKoKriterien() {
            auskunfteiErgebnisCluster.setWarnungen(2); // Kein KO
            auskunfteiErgebnisCluster.setRueckzahlungswahrscheinlichkeit(new Prozentwert(70)); // Kein KO
            auskunfteiErgebnisCluster.setNegativMerkmal(true); // KO

            assertTrue(auskunfteiErgebnisCluster.pruefeKoKriterium(),
                      "Kombinierte Faktoren mit mindestens einem KO-Kriterium sollten zu KO führen.");
        }
    }

    @Nested
    @DisplayName("Punkteberechnung")
    class PunkteberechnungTests {

        @ParameterizedTest
        @ValueSource(ints = {60, 75, 85, 99, 100})
        @DisplayName("Rückzahlungswahrscheinlichkeit entspricht direkt den Punkten")
        void rueckzahlungswahrscheinlichkeitEntsprichtPunkte(int prozent) {
            auskunfteiErgebnisCluster.setRueckzahlungswahrscheinlichkeit(new Prozentwert(prozent));
            Punkte punkte = auskunfteiErgebnisCluster.berechnePunkte();
            assertEquals(new Punkte(prozent), punkte,
                        "Die Rückzahlungswahrscheinlichkeit " + prozent + "% sollte " + prozent + " Punkten entsprechen.");
        }

        @ParameterizedTest
        @CsvSource({
            "true, 3, 80, 80",
            "false, 2, 90, 90"
        })
        @DisplayName("Punkteberechnung bei verschiedenen Kombinationen")
        void punkteberechnungMitKombinationen(boolean negativMerkmal, int warnungen, int prozent, int erwarteteGesamtpunkte) {
            auskunfteiErgebnisCluster.setNegativMerkmal(negativMerkmal);
            auskunfteiErgebnisCluster.setWarnungen(warnungen);
            auskunfteiErgebnisCluster.setRueckzahlungswahrscheinlichkeit(new Prozentwert(prozent));

            Punkte punkte = auskunfteiErgebnisCluster.berechnePunkte();
            assertEquals(new Punkte(erwarteteGesamtpunkte), punkte,
                        "Bei Negativmerkmal=" + negativMerkmal + ", Warnungen=" + warnungen +
                        ", Rückzahlungsproz=" + prozent + "% wurden " + erwarteteGesamtpunkte + " Punkte erwartet.");
        }
    }
}
