package com.bigpugloans.scoring.domainmodel.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domainmodel.Prozentwert;
import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.ClusterGescored;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuskunfteiErgebnisClusterTest {




    @Test
    void rueckzahlungswahrscheinlichkeitEntsprichtPunkte() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        int rueckzahlungsWahrscheinlichkeit = 85;
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(rueckzahlungsWahrscheinlichkeit));
        ClusterGescored clusterGescored = auskunfteiErgebnisCluster.scoren();
        assertEquals(new Punkte(rueckzahlungsWahrscheinlichkeit), clusterGescored.punkte(), "Die Rückzahlungswahrscheinlichkeit sollte den Punkten entsprechen.");
    }
}
