package com.bigpugloans.scoring.domainmodel.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domainmodel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("ImmobilienFinanzierungsCluster Tests")
public class ImmobilienFinanzierungsClusterTest {

    private ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster;

    @BeforeEach
    void setUp() {
        immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster();
    }

    @Nested
    @DisplayName("KO-Kriterien Prüfungen")
    class KoKriterienTests {
        @Test
        @DisplayName("Darlehenssumme größer als Beleihungswert ist ein KO-Kriterium")
        void summeDarlehenGroesserBeleihungswertIstKoKriterium() {
            immobilienFinanzierungsCluster.setSummeDarlehen(new Waehrungsbetrag(200000));
            immobilienFinanzierungsCluster.setBeleihungswert(new Waehrungsbetrag(150000));
            assertTrue(immobilienFinanzierungsCluster.pruefeKoKriterium(),
                      "Summe der Darlehen > Beleihungswert sollte ein KO-Kriterium sein.");
        }
        @Test
        @DisplayName("Darlehenssumme kleiner oder gleich Beleihungswert ist kein KO-Kriterium")
        void summeDarlehenKleinerBeleihungswertIstKeinKoKriterium() {
            immobilienFinanzierungsCluster.setSummeDarlehen(new Waehrungsbetrag(150000));
            immobilienFinanzierungsCluster.setBeleihungswert(new Waehrungsbetrag(150000));

            // Wird nur dieses Kriterium getestet - andere Parameter auf gültige Werte setzen
            immobilienFinanzierungsCluster.setEigenmittel(new Waehrungsbetrag(15000));
            immobilienFinanzierungsCluster.setMarktwertImmobilie(new Waehrungsbetrag(150000));
            immobilienFinanzierungsCluster.setKaufnebenkosten(new Waehrungsbetrag(15000));

            assertFalse(immobilienFinanzierungsCluster.pruefeKoKriterium(),
                       "Summe der Darlehen <= Beleihungswert sollte kein KO-Kriterium sein.");
        }
        @Test
        @DisplayName("Unausgeglichene Finanzierung ist ein KO-Kriterium")
        void summeDarlehenPlusEigenmittelUngleichMarktwertPlusKaufnebenkostenIstKoKriterium() {
            immobilienFinanzierungsCluster.setSummeDarlehen(new Waehrungsbetrag(200000));
            immobilienFinanzierungsCluster.setEigenmittel(new Waehrungsbetrag(30000));
            immobilienFinanzierungsCluster.setMarktwertImmobilie(new Waehrungsbetrag(210000));
            immobilienFinanzierungsCluster.setKaufnebenkosten(new Waehrungsbetrag(15000));
            assertTrue(immobilienFinanzierungsCluster.pruefeKoKriterium(),
                      "Summe Darlehen + Eigenmittel != Marktwert + Kaufnebenkosten sollte ein KO-Kriterium sein.");
        }
        @Test
        @DisplayName("Ausgeglichene Finanzierung ist kein KO-Kriterium")
        void summeDarlehenPlusEigenmittelGleichMarktwertPlusKaufnebenkostenIstKeinKoKriterium() {
            immobilienFinanzierungsCluster.setSummeDarlehen(new Waehrungsbetrag(180000));
            immobilienFinanzierungsCluster.setEigenmittel(new Waehrungsbetrag(30000));
            immobilienFinanzierungsCluster.setMarktwertImmobilie(new Waehrungsbetrag(195000));
            immobilienFinanzierungsCluster.setKaufnebenkosten(new Waehrungsbetrag(15000));

            // Verhindere anderes KO-Kriterium
            immobilienFinanzierungsCluster.setBeleihungswert(new Waehrungsbetrag(200000));

            assertFalse(immobilienFinanzierungsCluster.pruefeKoKriterium(),
                       "Summe Darlehen + Eigenmittel = Marktwert + Kaufnebenkosten sollte kein KO-Kriterium sein.");
        }
    }

    @Nested
    @DisplayName("Punkteberechnung für Eigenkapitalanteil")
    class EigenkapitalanteilPunkteTests {

        @ParameterizedTest
        @ValueSource(ints = {15, 16, 17, 18, 19, 20})
        @DisplayName("Eigenkapitalanteil 15-20% gibt 5 Punkte")
        void eigenkapitalanteil15Bis20ProzentGibt5Punkte(int prozentwert) {
            immobilienFinanzierungsCluster.setEigenkapitalanteil(new Prozentwert(prozentwert));
            Punkte punkte = immobilienFinanzierungsCluster.berechnePunkte();
            assertEquals(new Punkte(5), punkte,
                        "Ein Eigenkapitalanteil von " + prozentwert + "% sollte 5 Punkte geben.");
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 5, 10, 14})
        @DisplayName("Eigenkapitalanteil unter 15% gibt keine Punkte")
        void eigenkapitalanteilUnter15ProzentGibtKeinePunkte(int prozentwert) {
            immobilienFinanzierungsCluster.setEigenkapitalanteil(new Prozentwert(prozentwert));
        Punkte punkte = immobilienFinanzierungsCluster.berechnePunkte();
            assertEquals(new Punkte(0), punkte,
                        "Ein Eigenkapitalanteil von " + prozentwert + "% sollte keine Punkte geben.");
    }

        @ParameterizedTest
        @ValueSource(ints = {21, 25, 29})
        @DisplayName("Eigenkapitalanteil 21-29% gibt 10 Punkte")
        void eigenkapitalanteil21Bis29ProzentGibt10Punkte(int prozentwert) {
            immobilienFinanzierungsCluster.setEigenkapitalanteil(new Prozentwert(prozentwert));
            Punkte punkte = immobilienFinanzierungsCluster.berechnePunkte();
            assertEquals(new Punkte(10), punkte,
                        "Ein Eigenkapitalanteil von " + prozentwert + "% sollte 10 Punkte geben.");
}

        @ParameterizedTest
        @ValueSource(ints = {30, 35, 40, 50, 60, 75, 90, 100})
        @DisplayName("Eigenkapitalanteil ab 30% gibt 15 Punkte")
        void eigenkapitalanteilAb30ProzentGibt15Punkte(int prozentwert) {
            immobilienFinanzierungsCluster.setEigenkapitalanteil(new Prozentwert(prozentwert));
            Punkte punkte = immobilienFinanzierungsCluster.berechnePunkte();
            assertEquals(new Punkte(15), punkte,
                        "Ein Eigenkapitalanteil von " + prozentwert + "% sollte 15 Punkte geben.");
        }
    }

    @Nested
    @DisplayName("Punkteberechnung für Marktwert")
    class MarktwertPunkteTests {

        @Test
        @DisplayName("Durchschnittlicher Immobilienmarktwert gibt 15 Punkte")
        void marktwertDerImmobilieImDurchschnittGibt15Punkte() {
            immobilienFinanzierungsCluster.setMarktwertDurchschnittlich(true);
            Punkte punkte = immobilienFinanzierungsCluster.berechnePunkte();
            assertEquals(new Punkte(15), punkte,
                        "Ein durchschnittlicher Marktwert der Immobilie sollte 15 Punkte geben.");
        }

        @Test
        @DisplayName("Nicht durchschnittlicher Immobilienmarktwert gibt keine Punkte")
        void marktwertDerImmobilieNichtImDurchschnittGibtKeinePunkte() {
            immobilienFinanzierungsCluster.setMarktwertDurchschnittlich(false);
            Punkte punkte = immobilienFinanzierungsCluster.berechnePunkte();
            assertEquals(new Punkte(0), punkte,
                        "Ein nicht durchschnittlicher Marktwert der Immobilie sollte keine Punkte geben.");
        }
    }

    @Nested
    @DisplayName("Kombinierte Punkteberechnung")
    class KombiniertePunkteTests {

        @Test
        @DisplayName("Kombination von Eigenkapital und Marktwert addiert Punkte")
        void kombinationEigenkapitalUndMarktwert() {
            immobilienFinanzierungsCluster.setEigenkapitalanteil(new Prozentwert(25)); // 10 Punkte
            immobilienFinanzierungsCluster.setMarktwertDurchschnittlich(true);        // 15 Punkte

            Punkte punkte = immobilienFinanzierungsCluster.berechnePunkte();
            assertEquals(new Punkte(25), punkte,
                        "Die Kombination aus Eigenkapitalanteil (25%) und durchschnittlichem Marktwert sollte 25 Punkte ergeben.");
        }
    }
}
