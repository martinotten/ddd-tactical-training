package com.bigpugloans.scoring.domainmodel.antragstellerCluster;

import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.Waehrungsbetrag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AntragstellerClusterTest {
    @Test
    void antragstellerAusMuenchenUndHamburgBekommen5PunkteMehr() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster();
        antragstellerCluster.wohnortHinzufuegen("München");
        ClusterGescored ergebnis = antragstellerCluster.scoren();
        assertEquals(new Punkte(5), ergebnis.punkte(), "Antragsteller aus München sollten 5 Punkte mehr bekommen.");
    }

    @Test
    void bestandskundenMitGuthabenUeber10000Bekommen5PunkteMehr() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster();
        antragstellerCluster.guthabenHinzufuegen(new Waehrungsbetrag(12000));
        ClusterGescored ergebnis = antragstellerCluster.scoren();
        assertEquals(new Punkte(5), ergebnis.punkte(), "Bestandskunden mit Guthaben > 10.000 EUR sollten 5 Punkte mehr bekommen.");
    }

    @Test
    void antragstellerAusMuenchenUndHamburgMitGuthabenUeber10000Bekommen10PunkteMehr() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster();
        antragstellerCluster.wohnortHinzufuegen("München");
        antragstellerCluster.guthabenHinzufuegen(new Waehrungsbetrag(12000));
        ClusterGescored ergebnis = antragstellerCluster.scoren();
        assertEquals(new Punkte(10), ergebnis.punkte(), "Antragsteller aus München mit Guthaben > 10.000 EUR sollten 10 Punkte mehr bekommen.");
    }
}
