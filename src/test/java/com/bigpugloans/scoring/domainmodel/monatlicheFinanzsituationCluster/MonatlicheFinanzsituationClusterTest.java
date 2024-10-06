package com.bigpugloans.scoring.domainmodel.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domainmodel.Antragsnummer;
import com.bigpugloans.scoring.domainmodel.ClusterGescored;
import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.Waehrungsbetrag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MonatlicheFinanzsituationClusterTest {
    @Test
    void monatlicheFinanzsituationClusterOhneAntragsnummerWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new MonatlicheFinanzsituationCluster(null);
        });
    }

    @Test
    void monatlicheFinanzsituationClusterMitGleicherAntragsnummerSindGleich() {
        Antragsnummer antragsnummer = new Antragsnummer("123");

        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster1 = new MonatlicheFinanzsituationCluster(antragsnummer);
        monatlicheFinanzsituationCluster1.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(3000));
        monatlicheFinanzsituationCluster1.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(1000));
        monatlicheFinanzsituationCluster1.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(10));

        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster2 = new MonatlicheFinanzsituationCluster(antragsnummer);
        monatlicheFinanzsituationCluster2.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(2000));
        monatlicheFinanzsituationCluster2.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(1500));
        monatlicheFinanzsituationCluster2.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(2000));

        assertEquals(monatlicheFinanzsituationCluster1, monatlicheFinanzsituationCluster2, "Beide MonatlicheFinanzsituationCluster sollten gleich sein.");
    }
    @Test
    void monatlicheDarlehensbelastungGroesserEinnahmenMinusAusgabenIstKoKriterium() {
        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster = new MonatlicheFinanzsituationCluster(new Antragsnummer("123"));
        monatlicheFinanzsituationCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(3000));
        monatlicheFinanzsituationCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(1000));
        monatlicheFinanzsituationCluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(2500));
        ClusterGescored ergebnis = (ClusterGescored) monatlicheFinanzsituationCluster.scoren();
        assertTrue(ergebnis.koKriterien().anzahl() == 1, "Monatliche Darlehensbelastungen > (Einnahmen - Ausgaben) sollte ein KO-Kriterium sein.");
    }
    @Test
    void monatlicherHaushaltsueberschussOhneTilgungenUeber1500Gibt15Punkte() {
        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster = new MonatlicheFinanzsituationCluster(new Antragsnummer("123"));
        monatlicheFinanzsituationCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(2600));
        monatlicheFinanzsituationCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(1000));

        ClusterGescored ergebnis = (ClusterGescored) monatlicheFinanzsituationCluster.scoren();
        assertEquals(new Punkte(15), ergebnis.punkte(), "Ein monatlicher Haushaltsüberschuss ohne Tilgungen > 1.500 EUR sollte 15 Punkte geben.");
    }
}
