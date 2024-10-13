package com.bigpugloans.scoring.domain.model.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.Punkte;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WohnortTest {
    @Test
    void wohnortNull() {
        assertThrows(IllegalArgumentException.class, () -> new Wohnort(null));
    }

    @Test
    void antragstellerAusMuenchenBekommen5Punkte() {
        Wohnort wohnort = new Wohnort("München");
        assertEquals(new Punkte(5), wohnort.berechnePunkte(), "Antragsteller aus München sollten 5 Punkte bekommen.");
    }

    @Test
    void antragstellerAusHamburgBekommen5Punkte() {
        Wohnort wohnort = new Wohnort("Hamburg");
        assertEquals(new Punkte(5), wohnort.berechnePunkte(), "Antragsteller aus Hamburg sollten 5 Punkte bekommen.");
    }

    @Test
    void antragstellerAusDortmundBekommen5Punkte() {
        Wohnort wohnort = new Wohnort("Dortmund");
        assertEquals(new Punkte(0), wohnort.berechnePunkte(), "Antragsteller aus Dortmund sollten 0 Punkte bekommen.");
    }
}
