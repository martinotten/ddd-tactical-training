package com.bigpugloans.scoring.domainmodel.antragstellerCluster;

import com.bigpugloans.scoring.domainmodel.Antragsteller;
import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.Waehrungsbetrag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AntragstellerClusterTest {
    @Test
    void antragstellerAusMuenchenUndHamburgBekommen5PunkteMehr() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster();
        antragstellerCluster.setWohnort("München");
        Punkte punkte = antragstellerCluster.berechnePunkte();
        assertEquals(new Punkte(5), punkte, "Antragsteller aus München sollten 5 Punkte mehr bekommen.");
    }

    @Test
    void bestandskundenMitGuthabenUeber10000Bekommen5PunkteMehr() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster();
        antragstellerCluster.setGuthabenBeiMopsBank(new Waehrungsbetrag(12000));
        Punkte punkte = antragstellerCluster.berechnePunkte();
        assertEquals(new Punkte(5), punkte, "Bestandskunden mit Guthaben > 10.000 EUR sollten 5 Punkte mehr bekommen.");
    }
}
