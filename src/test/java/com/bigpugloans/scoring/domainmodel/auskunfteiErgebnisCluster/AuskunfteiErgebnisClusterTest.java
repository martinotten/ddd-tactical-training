package com.bigpugloans.scoring.domainmodel.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domainmodel.Prozentwert;
import com.bigpugloans.scoring.domainmodel.ClusterGescored;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuskunfteiErgebnisClusterTest {

    @Test
    void dreiWarnungenKeinNegativUnd80WahrscheinlichkeitIst80Punkte() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        auskunfteiErgebnisCluster.warnungenHinzufuegen(3);
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(80));
        ClusterGescored clusterGescored = auskunfteiErgebnisCluster.scoren();
        assertEquals(80, clusterGescored.punkte().getPunkte(), "3 Warnungen und 80% = 80 Punkte und kein KO");
        assertEquals(0, clusterGescored.koKriterien().anzahl(), "3 Warnungen und 80% = 80 Punkte und kein KO");
    }

    @Test
    void vierWarnungenZweiNegativ80WahrscheinlichkeitIst80PunkteUndKo() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        auskunfteiErgebnisCluster.warnungenHinzufuegen(4);
        auskunfteiErgebnisCluster.negativMerkmaleHinzufuegen(2);
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(80));
        ClusterGescored clusterGescored = auskunfteiErgebnisCluster.scoren();
        assertEquals(80, clusterGescored.punkte().getPunkte(), "4 Warnungen, 2 Negativ und 80% = 80 Punkte und 2 KO");
        assertEquals(2, clusterGescored.koKriterien().anzahl(), "4 Warnungen, 2 Negativ und 80% = 80 Punkte und 2 KO");
    }

    @Test
    void keineWarnungenEinNegativ80WahrscheinlichkeitIst80PunkteUndKo() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        auskunfteiErgebnisCluster.negativMerkmaleHinzufuegen(1);
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(80));
        ClusterGescored clusterGescored = auskunfteiErgebnisCluster.scoren();
        assertEquals(80, clusterGescored.punkte().getPunkte(), "0 Warnungen, 1 Negativ und 80% = 80 Punkte und 1 KO");
        assertEquals(1, clusterGescored.koKriterien().anzahl(), "0 Warnungen, 1 Negativ und 80% = 80 Punkte und 1 KO");
    }

    @Test
    void keineWarnungenKeinNegativ50WahrscheinlichkeitIst50PunkteUndKo() {
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster();
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(50));
        ClusterGescored clusterGescored = auskunfteiErgebnisCluster.scoren();
        assertEquals(50, clusterGescored.punkte().getPunkte(), "0 Warnungen, 0 Negativ und 50% = 50 Punkte und 1 KO");
        assertEquals(1, clusterGescored.koKriterien().anzahl(), "0 Warnungen, 0 Negativ und 50% = 50 Punkte und 1 KO");
    }

}
