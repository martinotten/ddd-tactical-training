package com.bigpugloans.scoring.domain.model.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.Punkte;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuthabenTest {
    @Test
    void bestandskundenMitGuthabenUeber10000Bekommen5PunkteMehr() {
        assertEquals(new Punkte(5), new Guthaben(12000).berechnePunkte(), "Bestandskunden mit Guthaben 12.000 EUR sollten 5 Punkte mehr bekommen.");

        assertEquals(new Punkte(5), new Guthaben(10001).berechnePunkte(), "Bestandskunden mit Guthaben 10.001 EUR sollten 5 Punkte mehr bekommen.");
    }

    @Test
    void bestandskundenMitGuthabenUnter10000Bekommen0Punkte() {
        assertEquals(new Punkte(0), new Guthaben(10000).berechnePunkte(), "Bestandskunden mit Guthaben 10.000 EUR sollten 0 Punkte.");

        assertEquals(new Punkte(0), new Guthaben(0).berechnePunkte(), "Bestandskunden mit Guthaben 0 EUR sollten 0 Punkte bekommen.");
    }
}
