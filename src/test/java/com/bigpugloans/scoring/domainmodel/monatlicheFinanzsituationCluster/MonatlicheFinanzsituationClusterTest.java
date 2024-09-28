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
        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster = new MonatlicheFinanzsituationCluster();
        monatlicheFinanzsituationCluster.setMonatlicherUeberschussOhneTilgungen(new Waehrungsbetrag(1600));
        Punkte punkte = monatlicheFinanzsituationCluster.berechnePunkte();
        assertEquals(new Punkte(15), punkte, "Ein monatlicher Haushaltsüberschuss ohne Tilgungen > 1.500 EUR sollte 15 Punkte geben.");
    }
}
