package com.bigpugloans.scoring.domainmodel.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domainmodel.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import com.bigpugloans.scoring.domainmodel.Prozentwert;
import com.bigpugloans.scoring.domainmodel.Punkte;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuskunfteiErgebnisClusterTest {
    @Test
    void negativMerkmalIstKoKriterium() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        auskunfteiErgebnisCluster.setNegativMerkmal(true);
        assertTrue(auskunfteiErgebnisCluster.pruefeKoKriterium(), "Negativmerkmal sollte ein KO-Kriterium sein.");
    }

    @Test
    void mehrAlsDreiWarnungenSindKoKriterium() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        auskunfteiErgebnisCluster.setWarnungen(4); // Mehr als 3 Warnungen
        assertTrue(auskunfteiErgebnisCluster.pruefeKoKriterium(), "Mehr als drei Warnungen sollten ein KO-Kriterium sein.");
    }

    @Test
    void rueckzahlungswahrscheinlichkeitUnter60IstKoKriterium() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        auskunfteiErgebnisCluster.setRueckzahlungswahrscheinlichkeit(new Prozentwert(59)); // < 60%
        assertTrue(auskunfteiErgebnisCluster.pruefeKoKriterium(), "Rückzahlungswahrscheinlichkeit < 60 sollte ein KO-Kriterium sein.");
    }

    @Test
    void rueckzahlungswahrscheinlichkeitEntsprichtPunkte() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        auskunfteiErgebnisCluster.setRueckzahlungswahrscheinlichkeit(new Prozentwert(85));
        Punkte punkte = auskunfteiErgebnisCluster.berechnePunkte();
        assertEquals(new Punkte(85), punkte, "Die Rückzahlungswahrscheinlichkeit sollte den Punkten entsprechen.");
    }
}
