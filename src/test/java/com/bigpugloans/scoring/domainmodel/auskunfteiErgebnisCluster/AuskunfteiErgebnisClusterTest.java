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
        auskunfteiErgebnisCluster.setNegativMerkmal(true);
        ClusterGescored clusterGescored = auskunfteiErgebnisCluster.scoren();
        assertTrue(clusterGescored.koKriterien() == 1, "Negativmerkmal sollte ein KO-Kriterium sein.");
    }    }

    @Test
    void mehrAlsDreiWarnungenSindKoKriterium() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        auskunfteiErgebnisCluster.hatWarnungen(4); // Mehr als 3 Warnungen
        ClusterGescored clusterGescored = auskunfteiErgebnisCluster.scoren();
        assertTrue(clusterGescored.koKriterien() == 1, "Mehr als 3 Warnungen sollte ein KO-Kriterium sein.");
    }

    @Test
    void rueckzahlungswahrscheinlichkeitUnter60IstKoKriterium() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        auskunfteiErgebnisCluster.setRueckzahlungswahrscheinlichkeit(new Prozentwert(59)); // < 60%
        ClusterGescored clusterGescored = auskunfteiErgebnisCluster.scoren();
        assertTrue(clusterGescored.koKriterien() == 1, "Rückzahlungswahrscheinlichkeit < 60% sollte ein KO-Kriterium sein.");
    }

    @Test
    void rueckzahlungswahrscheinlichkeitEntsprichtPunkte() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        auskunfteiErgebnisCluster.setRueckzahlungswahrscheinlichkeit(new Prozentwert(85));
        ClusterGescored clusterGescored = auskunfteiErgebnisCluster.scoren();
        assertEquals(new Punkte(rueckzahlungsWahrscheinlichkeit), clusterGescored.punkte(), "Die Rückzahlungswahrscheinlichkeit sollte den Punkten entsprechen.");
    }
}
