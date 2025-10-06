package com.bigpugloans.scoring.domainmodel.antragstellerCluster;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bigpugloans.scoring.domainmodel.Antragsnummer;
import com.bigpugloans.scoring.domainmodel.ClusterGescored;
import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.Waehrungsbetrag;
import org.junit.jupiter.api.Test;

public class AntragstellerClusterTest {

    @Test
    void antragstellerClusterOhneAntragsnummerWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AntragstellerCluster(null);
        });
    }

    @Test
    void antragstellerClusterMitGleichenAntragsnummernSindGleich() {
        AntragstellerCluster antragstellerCluster1 = new AntragstellerCluster(
            new Antragsnummer("123")
        );
        antragstellerCluster1.wohnortHinzufuegen("Stuttgart");
        antragstellerCluster1.guthabenHinzufuegen(new Waehrungsbetrag(3000));

        AntragstellerCluster antragstellerCluster2 = new AntragstellerCluster(
            new Antragsnummer("123")
        );
        antragstellerCluster2.wohnortHinzufuegen("München");
        antragstellerCluster2.guthabenHinzufuegen(new Waehrungsbetrag(10000));

        assertEquals(
            antragstellerCluster1,
            antragstellerCluster2,
            "Beide AntragstellerCluster sollten gleich sein."
        );
        assertEquals(
            antragstellerCluster1.hashCode(),
            antragstellerCluster2.hashCode(),
            "Beide Hashcodes sollten gleich sein."
        );
    }

    @Test
    void antragstellerAusMuenchenMitGuthaben12000Bekommen10Punkte() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(
            new Antragsnummer("123")
        );
        antragstellerCluster.wohnortHinzufuegen("München");
        antragstellerCluster.guthabenHinzufuegen(new Waehrungsbetrag(12000));
        ClusterGescored ergebnis = antragstellerCluster.scoren().orElseThrow();
        assertEquals(
            new Punkte(10),
            ergebnis.punkte(),
            "Antragsteller aus München mit Guthaben > 10.000 EUR sollten 10 Punkte mehr bekommen."
        );
    }

    @Test
    void antragstellerAusMuenchenMitGuthabenVon9000Bekommen5Punkte() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(
            new Antragsnummer("123")
        );
        antragstellerCluster.wohnortHinzufuegen("München");
        antragstellerCluster.guthabenHinzufuegen(new Waehrungsbetrag(9000));
        ClusterGescored ergebnis = antragstellerCluster.scoren().orElseThrow();
        assertEquals(
            new Punkte(5),
            ergebnis.punkte(),
            "Antragsteller aus München mit Guthaben 9.000 EUR sollten 5 Punkte mehr bekommen."
        );
    }

    @Test
    void antragstellerAusDortmundMitGuthaben12000Bekommen5Punkte() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(
            new Antragsnummer("123")
        );
        antragstellerCluster.wohnortHinzufuegen("Dortmund");
        antragstellerCluster.guthabenHinzufuegen(new Waehrungsbetrag(12000));
        ClusterGescored ergebnis = antragstellerCluster.scoren().orElseThrow();
        assertEquals(
            new Punkte(5),
            ergebnis.punkte(),
            "Antragsteller aus Dortmund mit Guthaben > 10.000 EUR sollten 5 Punkte mehr bekommen."
        );
    }

    @Test
    void antragstellerAusDortmundMitGuthaben10000Bekommen0Punkte() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(
            new Antragsnummer("123")
        );
        antragstellerCluster.wohnortHinzufuegen("Dortmund");
        antragstellerCluster.guthabenHinzufuegen(new Waehrungsbetrag(10000));
        ClusterGescored ergebnis = antragstellerCluster.scoren().orElseThrow();
        assertEquals(
            new Punkte(0),
            ergebnis.punkte(),
            "Antragsteller aus Dortmund mit Guthaben <= 10.000 EUR sollten 0 Punkte mehr bekommen."
        );
    }
}
