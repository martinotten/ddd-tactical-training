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
        auskunfteiErgebnisCluster.negativMerkmaleHinzufuegen(1);
        ClusterGescored clusterGescored = auskunfteiErgebnisCluster.scoren();
        assertTrue(clusterGescored.koKriterien() == 1, "Negativmerkmal sollte ein KO-Kriterium sein.");
    }

    @Test
    void mehrAlsDreiWarnungenSindKoKriterium() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        auskunfteiErgebnisCluster.setWarnungen(4); // Mehr als 3 Warnungen
        ClusterGescored clusterGescored = auskunfteiErgebnisCluster.scoren();
        assertTrue(clusterGescored.koKriterien() == 1, "Mehr als 3 Warnungen sollte ein KO-Kriterium sein.");
    }

    @Test
    void rueckzahlungswahrscheinlichkeitUnter60IstKoKriterium() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(59)); // < 60%
        ClusterGescored clusterGescored = auskunfteiErgebnisCluster.scoren();
        assertTrue(clusterGescored.koKriterien == 1, "Rückzahlungswahrscheinlichkeit < 60% sollte ein KO-Kriterium sein.");
    }

    @Test
    void rueckzahlungswahrscheinlichkeitEntsprichtPunkte() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        int rueckzahlungsWahrscheinlichkeit = 85;
        auskunfteiErgebnisCluster.setRueckzahlungswahrscheinlichkeit(new Prozentwert(rueckzahlungsWahrscheinlichkeit));
        ClusterGescored clusterGescored = auskunfteiErgebnisCluster.scoren();
        assertEquals(new Punkte(rueckzahlungsWahrscheinlichkeit), clusterGescored.punkte(), "Die Rückzahlungswahrscheinlichkeit sollte den Punkten entsprechen.");
    }
}
