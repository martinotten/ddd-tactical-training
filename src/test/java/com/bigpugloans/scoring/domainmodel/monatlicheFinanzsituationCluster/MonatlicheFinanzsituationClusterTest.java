package com.bigpugloans.scoring.domainmodel.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.Waehrungsbetrag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MonatlicheFinanzsituationClusterTest {
    @Test
    void monatlicheDarlehensbelastungGroesserEinnahmenMinusAusgabenIstKoKriterium() {
        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster = new MonatlicheFinanzsituationCluster();
        monatlicheFinanzsituationCluster.setMonatlicheEinnahmen(new Waehrungsbetrag(3000));
        monatlicheFinanzsituationCluster.setMonatlicheAusgaben(new Waehrungsbetrag(1000));
        monatlicheFinanzsituationCluster.setMonatlicheDarlehensbelastungen(new Waehrungsbetrag(2500));
        assertTrue(monatlicheFinanzsituationCluster.pruefeKoKriterium(), "Monatliche Darlehensbelastungen > (Einnahmen - Ausgaben) sollte ein KO-Kriterium sein.");
    }

    @Test
    void monatlicherHaushaltsueberschussOhneTilgungenUeber1500Gibt15Punkte() {
        MonatlicheFinanzsituationCluster cluster = new MonatlicheFinanzsituationCluster();
        cluster.setMonatlicherUeberschussOhneTilgungen(new Waehrungsbetrag(1501));
        assertEquals(new Punkte(15), cluster.berechnePunkte());
    }

    @Test
    void monatlicherHaushaltsueberschussOhneTilgungenGleich1500Gibt0Punkte() {
        MonatlicheFinanzsituationCluster cluster = new MonatlicheFinanzsituationCluster();
        cluster.setMonatlicherUeberschussOhneTilgungen(new Waehrungsbetrag(1500));
        assertEquals(new Punkte(0), cluster.berechnePunkte());
    }

    @Test
    void monatlicherHaushaltsueberschussOhneTilgungenUnter1500Gibt0Punkte() {
        MonatlicheFinanzsituationCluster cluster = new MonatlicheFinanzsituationCluster();
        cluster.setMonatlicherUeberschussOhneTilgungen(new Waehrungsbetrag(1499));
        assertEquals(new Punkte(0), cluster.berechnePunkte());
    }
}
