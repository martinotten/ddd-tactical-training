package com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domain.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MonatlicheFinanzsituationClusterMementoTest {
    @Test
    void testClusterVollstaendigZuMemento() {
        String antragsnummer = "123";
        ScoringId scoringId = ScoringId.mainScoringIdAusAntragsnummer(antragsnummer);
        int einnamen = 3000;
        int ausgaben = 2000;
        int neueDarlehensbelastungen = 500;

        MonatlicheFinanzsituationCluster cluster = new MonatlicheFinanzsituationCluster(scoringId);

        cluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(einnamen));
        cluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(ausgaben));
        cluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(neueDarlehensbelastungen));

        MonatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterMemento memento = cluster.memento();
        assertEquals(scoringId, memento.scoringId());
        assertEquals(BigDecimal.valueOf(einnamen), memento.einnahmen());
        assertEquals(BigDecimal.valueOf(ausgaben), memento.ausgaben());
        assertEquals(BigDecimal.valueOf(neueDarlehensbelastungen), memento.neueDarlehensBelastungen());
    }

    @Test
    void testClusterMinimalZuMemento() {
        String antragsnummer = "123";
        ScoringId scoringId = ScoringId.mainScoringIdAusAntragsnummer(antragsnummer);

        MonatlicheFinanzsituationCluster cluster = new MonatlicheFinanzsituationCluster(scoringId);

        MonatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterMemento memento = cluster.memento();
        assertEquals(scoringId, memento.scoringId());
        assertNull(memento.einnahmen());
        assertNull(memento.ausgaben());
        assertNull(memento.neueDarlehensBelastungen());
    }

    @Test
    void mementoVollstaendigZuCluster() {
        String antragsnummer = "123";
        ScoringId scoringId = ScoringId.mainScoringIdAusAntragsnummer(antragsnummer);
        int einnamen = 6000;
        int ausgaben = 2000;
        int neueDarlehensbelastungen = 500;

        MonatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterMemento memento = new MonatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterMemento(scoringId, BigDecimal.valueOf(einnamen), BigDecimal.valueOf(ausgaben), BigDecimal.valueOf(neueDarlehensbelastungen));
        MonatlicheFinanzsituationCluster cluster = MonatlicheFinanzsituationCluster.fromMemento(memento);

        assertEquals(scoringId, cluster.scoringId());
        Optional<ClusterGescored> clusterScoringEvent = cluster.scoren();
        assertTrue(clusterScoringEvent.isPresent());
        assertEquals(new Punkte(15), clusterScoringEvent.get().punkte());
    }

    @Test
    void mementoMinimalZuCluster() {
        String antragsnummer = "123";
        ScoringId scoringId = ScoringId.mainScoringIdAusAntragsnummer(antragsnummer);

        MonatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterMemento memento = new MonatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterMemento(scoringId, null, null, null);
        MonatlicheFinanzsituationCluster cluster = MonatlicheFinanzsituationCluster.fromMemento(memento);

        assertEquals(scoringId, cluster.scoringId());
        Optional<ClusterGescored> clusterScoringEvent = cluster.scoren();
        assertFalse(clusterScoringEvent.isPresent());
    }
}
