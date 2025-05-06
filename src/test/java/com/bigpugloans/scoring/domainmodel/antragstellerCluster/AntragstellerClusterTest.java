package com.bigpugloans.scoring.domainmodel.antragstellerCluster;

import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.Waehrungsbetrag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AntragstellerClusterTest {

    private AntragstellerCluster antragstellerCluster;

    @BeforeEach
    void setUp() {
        antragstellerCluster = new AntragstellerCluster();
    }

    @ParameterizedTest
    @ValueSource(strings = {"München", "Hamburg"})
    @DisplayName("Antragsteller aus München oder Hamburg bekommen 5 Punkte mehr")
    void wohnortBonus(String wohnort) {
        antragstellerCluster.setWohnort(wohnort);
        Punkte punkte = antragstellerCluster.berechnePunkte();
        assertEquals(new Punkte(5), punkte, "Antragsteller aus " + wohnort + " sollen 5 Punkte mehr bekommen.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Köln", "Berlin", "Coburg"})
    @DisplayName("Antragsteller aus anderen Orten bekommen keine Punkte")
    void keineWohnortBonus(String wohnort) {
        antragstellerCluster.setWohnort(wohnort);
        Punkte punkte = antragstellerCluster.berechnePunkte();
        assertEquals(new Punkte(0), punkte, "Antragsteller aus %s sollen keine zusätzlichen Punkte bekommen.".formatted(wohnort));
    }

    @ParameterizedTest
    @ValueSource(ints = {10001, 20000, 30000, 1000000})
    @DisplayName("Bestandskunden mit Guthaben über 10.000€ bekommen 5 Punkte mehr")
    void guthabenBonus(int bankGuthaben) {
        antragstellerCluster.setGuthabenBeiMopsBank(new Waehrungsbetrag(bankGuthaben));
        Punkte punkte = antragstellerCluster.berechnePunkte();
        assertEquals(new Punkte(5), punkte, "Bestandskunden mit Guthaben > 10.000 EUR sollten 5 Punkte mehr bekommen. Hier ist der Wert: %d".formatted(bankGuthaben));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1000, 0, 1, 9999, 10000})
    @DisplayName("Bestandskunden mit Guthaben bis 10.000€ bekommen keine extra Punkte")
    void keinGuthabenBonus(int bankGuthaben) {
        antragstellerCluster.setGuthabenBeiMopsBank(new Waehrungsbetrag(bankGuthaben));
        Punkte punkte = antragstellerCluster.berechnePunkte();
        assertEquals(
                new Punkte(0),
                punkte,
                () -> "Bestandskunden mit Guthaben " + bankGuthaben + " EUR dürfen keine zusätzlichen Punktebekommen."
        );
    }

    @Nested
    @DisplayName("Wohnort und Guthaben beeinflussen sich nicht")
    class KombinierteTests {

        @ParameterizedTest
        @CsvSource({
                "München, 15000, 10",
                "Hamburg, 20000, 10",
                "Berlin, 15000, 5",
                "München, 5000, 5",
                "Berlin, 5000, 0"
        })
        @DisplayName("Kombination von Wohnort und Guthaben")
        void kombinierteFaktoren(String wohnort, int guthaben, int erwarteteGesamtpunkte) {
            // Arrange
            antragstellerCluster.setWohnort(wohnort);
            antragstellerCluster.setGuthabenBeiMopsBank(new Waehrungsbetrag(guthaben));

            // Act
            Punkte punkte = antragstellerCluster.berechnePunkte();

            // Assert
            assertEquals(new Punkte(erwarteteGesamtpunkte), punkte,
                    "Für Wohnort '%s' und Guthaben %d wurden %d Punkte erwartet".formatted(
                            wohnort, guthaben, erwarteteGesamtpunkte));
        }
    }
}
