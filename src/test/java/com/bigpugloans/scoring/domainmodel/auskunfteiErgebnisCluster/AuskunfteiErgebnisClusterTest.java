package com.bigpugloans.scoring.domainmodel.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domainmodel.*;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuskunfteiErgebnisClusterTest {

    @Test
    void antragsnummerNullWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AuskunfteiErgebnisCluster(null, new AntragstellerID.Builder("Michael", "Ploed")
                    .postleitzahl("40789")
                    .stadt("Monheim")
                    .strasse("Krischerstrasse 100")
                    .geburtsdatum(new Date(1970, 1, 1))
                    .build());
        });
    }

    @Test
    void antragsstellerIDNullWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AuskunfteiErgebnisCluster(new Antragsnummer("123"), null);
        });
    }
    @Test
    void ohneRueckzahlungswahrscheinlichkeitKeinScoring() {
        AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(new Date(1970, 1, 1))
                .build();
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster(new Antragsnummer("123"), antragstellerID);
        assertEquals(Optional.empty(), auskunfteiErgebnisCluster.scoren());
    }
    @Test
    void auskunfteiErgebnisClusterMitGleicherAntragsnummerUndAntragstellerIDsindGleich() {
        AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(new Date(1970, 1, 1))
                .build();
        Antragsnummer antragsnummer = new Antragsnummer("123");

        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster1 = new AuskunfteiErgebnisCluster(antragsnummer, antragstellerID);
        auskunfteiErgebnisCluster1.warnungenHinzufuegen(3);
        auskunfteiErgebnisCluster1.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(80));

        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster2 = new AuskunfteiErgebnisCluster(antragsnummer, antragstellerID);
        auskunfteiErgebnisCluster2.negativMerkmaleHinzufuegen(2);
        auskunfteiErgebnisCluster2.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(93));

        assertEquals(auskunfteiErgebnisCluster1, auskunfteiErgebnisCluster2, "Beide AuskunfteiErgebnisCluster sollten gleich sein.");
        assertEquals(auskunfteiErgebnisCluster1.hashCode(), auskunfteiErgebnisCluster2.hashCode(), "Beide Hashcodes sollten gleich sein.");
    }
    @Test
    void dreiWarnungenKeinNegativUnd80WahrscheinlichkeitIst80Punkte() {
        AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(new Date(1970, 1, 1))
                .build();
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster(new Antragsnummer("123"), antragstellerID);
        auskunfteiErgebnisCluster.warnungenHinzufuegen(3);
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(80));
        ClusterGescored clusterGescored = auskunfteiErgebnisCluster.scoren().orElseThrow();
        assertEquals(80, clusterGescored.punkte().getPunkte(), "3 Warnungen und 80% = 80 Punkte und kein KO");
        assertEquals(0, clusterGescored.koKriterien().anzahl(), "3 Warnungen und 80% = 80 Punkte und kein KO");
    }

    @Test
    void vierWarnungenZweiNegativ80WahrscheinlichkeitIst80PunkteUndKo() {
        AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(new Date(1970, 1, 1))
                .build();
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster(new Antragsnummer("123"), antragstellerID);
        auskunfteiErgebnisCluster.warnungenHinzufuegen(4);
        auskunfteiErgebnisCluster.negativMerkmaleHinzufuegen(2);
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(80));
        ClusterGescored clusterGescored = auskunfteiErgebnisCluster.scoren().orElseThrow();
        assertEquals(80, clusterGescored.punkte().getPunkte(), "4 Warnungen, 2 Negativ und 80% = 80 Punkte und 2 KO");
        assertEquals(2, clusterGescored.koKriterien().anzahl(), "4 Warnungen, 2 Negativ und 80% = 80 Punkte und 2 KO");
    }

    @Test
    void keineWarnungenEinNegativ80WahrscheinlichkeitIst80PunkteUndKo() {
        AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(new Date(1970, 1, 1))
                .build();
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster(new Antragsnummer("123"), antragstellerID);
        auskunfteiErgebnisCluster.negativMerkmaleHinzufuegen(1);
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(80));
        ClusterGescored clusterGescored = auskunfteiErgebnisCluster.scoren().orElseThrow();
        assertEquals(80, clusterGescored.punkte().getPunkte(), "0 Warnungen, 1 Negativ und 80% = 80 Punkte und 1 KO");
        assertEquals(1, clusterGescored.koKriterien().anzahl(), "0 Warnungen, 1 Negativ und 80% = 80 Punkte und 1 KO");
    }

    @Test
    void keineWarnungenKeinNegativ50WahrscheinlichkeitIst50PunkteUndKo() {
        AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(new Date(1970, 1, 1))
                .build();
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster(new Antragsnummer("123"), antragstellerID);
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(50));
        ClusterGescored clusterGescored = auskunfteiErgebnisCluster.scoren().orElseThrow();
        assertEquals(50, clusterGescored.punkte().getPunkte(), "0 Warnungen, 0 Negativ und 50% = 50 Punkte und 1 KO");
        assertEquals(1, clusterGescored.koKriterien().anzahl(), "0 Warnungen, 0 Negativ und 50% = 50 Punkte und 1 KO");
    }

}
