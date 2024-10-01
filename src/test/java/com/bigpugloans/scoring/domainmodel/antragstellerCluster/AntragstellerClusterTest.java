package com.bigpugloans.scoring.domainmodel.antragstellerCluster;

import com.bigpugloans.scoring.domainmodel.ClusterGescored;
import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.Waehrungsbetrag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AntragstellerClusterTest {


    @Test
    void antragstellerAusMuenchenMitGuthaben12000Bekommen10Punkte() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster();
        antragstellerCluster.wohnortHinzufuegen("München");
        antragstellerCluster.guthabenHinzufuegen(new Waehrungsbetrag(12000));
        ClusterGescored ergebnis = antragstellerCluster.scoren();
        assertEquals(new Punkte(10), ergebnis.punkte(), "Antragsteller aus München mit Guthaben > 10.000 EUR sollten 10 Punkte mehr bekommen.");
    }

    @Test
    void antragstellerAusMuenchenMitGuthabenVon9000Bekommen5Punkte() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster();
        antragstellerCluster.wohnortHinzufuegen("München");
        antragstellerCluster.guthabenHinzufuegen(new Waehrungsbetrag(9000));
        ClusterGescored ergebnis = antragstellerCluster.scoren();
        assertEquals(new Punkte(5), ergebnis.punkte(), "Antragsteller aus München mit Guthaben 9.000 EUR sollten 5 Punkte mehr bekommen.");
    }

    @Test
    void antragstellerAusDortmundMitGuthaben12000Bekommen5Punkte() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster();
        antragstellerCluster.wohnortHinzufuegen("Dortmund");
        antragstellerCluster.guthabenHinzufuegen(new Waehrungsbetrag(12000));
        ClusterGescored ergebnis = antragstellerCluster.scoren();
        assertEquals(new Punkte(5), ergebnis.punkte(), "Antragsteller aus Dortmund mit Guthaben > 10.000 EUR sollten 5 Punkte mehr bekommen.");
    }
    @Test
    void antragstellerAusDortmundMitGuthaben10000Bekommen0Punkte() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster();
        antragstellerCluster.wohnortHinzufuegen("Dortmund");
        antragstellerCluster.guthabenHinzufuegen(new Waehrungsbetrag(10000));
        ClusterGescored ergebnis = antragstellerCluster.scoren();
        assertEquals(new Punkte(0), ergebnis.punkte(), "Antragsteller aus Dortmund mit Guthaben <= 10.000 EUR sollten 0 Punkte mehr bekommen.");
    }
}
