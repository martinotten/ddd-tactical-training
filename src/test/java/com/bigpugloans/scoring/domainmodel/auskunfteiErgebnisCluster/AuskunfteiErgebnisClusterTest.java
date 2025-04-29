package com.bigpugloans.scoring.domainmodel.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domainmodel.Prozentwert;
import com.bigpugloans.scoring.domainmodel.Punkte;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuskunfteiErgebnisClusterTest {
    @Test
    void negativMerkmalIstKoKriterium() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        auskunfteiErgebnisCluster.hatMindestensEinNegativmerkmal();
        assertTrue(auskunfteiErgebnisCluster.koKriteriumIstErfuellt(), "Negativmerkmal sollte ein KO-Kriterium sein.");
    }

    @Test
    void mehrAlsDreiWarnungenSindKoKriterium() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        auskunfteiErgebnisCluster.hatWarnungen(4); // Mehr als 3 Warnungen
        assertTrue(auskunfteiErgebnisCluster.koKriteriumIstErfuellt(), "Mehr als drei Warnungen sollten ein KO-Kriterium sein.");
    }

    @Test
    void rueckzahlungswahrscheinlichkeitUnter60IstKoKriterium() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        auskunfteiErgebnisCluster.setRueckzahlungswahrscheinlichkeit(new Prozentwert(59)); // < 60%
        assertTrue(auskunfteiErgebnisCluster.koKriteriumIstErfuellt(), "Rückzahlungswahrscheinlichkeit < 60 sollte ein KO-Kriterium sein.");
    }

    @Test
    void rueckzahlungswahrscheinlichkeitEntsprichtPunkte() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        auskunfteiErgebnisCluster.setRueckzahlungswahrscheinlichkeit(new Prozentwert(85));
        Punkte punkte = auskunfteiErgebnisCluster.berechnePunkte();
        assertEquals(new Punkte(85), punkte, "Die Rückzahlungswahrscheinlichkeit sollte den Punkten entsprechen.");
    }
}
