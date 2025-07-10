package com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domain.model.*;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MonatlicheFinanzsituationClusterTest {
    @Test
    void monatlicheFinanzsituationClusterOhneScoringIdWirftException() {
        assertThrows(IllegalArgumentException.class, () -> new MonatlicheFinanzsituationCluster(null));
    }

    @Test
    void keinScoringOhneEinnahmen() {
        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster = new MonatlicheFinanzsituationCluster(ScoringId.mainScoringIdAusAntragsnummer("123"));
        monatlicheFinanzsituationCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(1000));
        monatlicheFinanzsituationCluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(10));
        Optional<ClusterGescored> ergebnis = monatlicheFinanzsituationCluster.scoren();

        assertTrue(ergebnis.isEmpty(),"Kein Scoring ohne Einnahmen");
    }

    @Test
    void keinScoringOhneAusgaben() {
        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster = new MonatlicheFinanzsituationCluster(ScoringId.mainScoringIdAusAntragsnummer("123"));
        monatlicheFinanzsituationCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(1000));
        monatlicheFinanzsituationCluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(10));
        Optional<ClusterGescored> ergebnis = monatlicheFinanzsituationCluster.scoren();

        assertTrue(ergebnis.isEmpty(),"Kein Scoring ohne Ausgaben");
    }

    @Test
    void keinScoringOhneMonatlicheDarlehensbelastungen() {
        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster = new MonatlicheFinanzsituationCluster(ScoringId.mainScoringIdAusAntragsnummer("123"));
        monatlicheFinanzsituationCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(1000));
        monatlicheFinanzsituationCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(10));
        Optional<ClusterGescored> ergebnis = monatlicheFinanzsituationCluster.scoren();

        assertTrue(ergebnis.isEmpty(),"Kein Scoring ohne Darlehensbelastungen");
    }

    @Test
    void monatlicheFinanzsituationClusterMitGleicherScoringIdSindGleich() {
        ScoringId scoringId = ScoringId.mainScoringIdAusAntragsnummer("123");

        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster1 = new MonatlicheFinanzsituationCluster(scoringId);
        monatlicheFinanzsituationCluster1.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(3000));
        monatlicheFinanzsituationCluster1.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(1000));
        monatlicheFinanzsituationCluster1.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(10));

        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster2 = new MonatlicheFinanzsituationCluster(scoringId);
        monatlicheFinanzsituationCluster2.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(2000));
        monatlicheFinanzsituationCluster2.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(1500));
        monatlicheFinanzsituationCluster2.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(2000));

        assertEquals(monatlicheFinanzsituationCluster1, monatlicheFinanzsituationCluster2, "Beide MonatlicheFinanzsituationCluster sollten gleich sein.");
    }
    @Test
    void monatlicheDarlehensbelastungGroesserEinnahmenMinusAusgabenIstKoKriterium() {
        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster = new MonatlicheFinanzsituationCluster(ScoringId.mainScoringIdAusAntragsnummer("123"));
        monatlicheFinanzsituationCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(3000));
        monatlicheFinanzsituationCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(1000));
        monatlicheFinanzsituationCluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(2500));
        ClusterGescored ergebnis = monatlicheFinanzsituationCluster.scoren().get();
        assertEquals(1, ergebnis.koKriterien().anzahl(), "Monatliche Darlehensbelastungen > (Einnahmen - Ausgaben) sollte ein KO-Kriterium sein.");
    }
    @Test
    void monatlicherHaushaltsueberschussOhneTilgungenUeber1500Gibt15Punkte() {
        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster = new MonatlicheFinanzsituationCluster(ScoringId.mainScoringIdAusAntragsnummer("123"));
        monatlicheFinanzsituationCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(2600));
        monatlicheFinanzsituationCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(1000));
        monatlicheFinanzsituationCluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(1500));

        ClusterGescored ergebnis = monatlicheFinanzsituationCluster.scoren().get();
        assertEquals(new Punkte(15), ergebnis.punkte(), "Ein monatlicher Haushaltsüberschuss ohne Tilgungen > 1.500 EUR sollte 15 Punkte geben.");
    }
}
