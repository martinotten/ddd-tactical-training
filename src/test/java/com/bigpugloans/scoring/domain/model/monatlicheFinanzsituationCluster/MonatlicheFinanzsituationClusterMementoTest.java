package com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domain.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MonatlicheFinanzsituationClusterMementoTest {
    @Test
    void testClusterVollstaendigZuMemento() {
        String antragsnummer = "123";
        int einnamen = 3000;
        int ausgaben = 2000;
        int neueDarlehensbelastungen = 500;

        MonatlicheFinanzsituationCluster cluster = new MonatlicheFinanzsituationCluster(new Antragsnummer(antragsnummer));

        cluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(einnamen));
        cluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(ausgaben));
        cluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(neueDarlehensbelastungen));

        MonatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterMemento memento = cluster.memento();
        assertEquals(antragsnummer, memento.antragsnummer());
        assertEquals(BigDecimal.valueOf(einnamen), memento.einnahmen());
        assertEquals(BigDecimal.valueOf(ausgaben), memento.ausgaben());
        assertEquals(BigDecimal.valueOf(neueDarlehensbelastungen), memento.neueDarlehensBelastungen());
    }

    @Test
    void testClusterMinimalZuMemento() {
        String antragsnummer = "123";

        MonatlicheFinanzsituationCluster cluster = new MonatlicheFinanzsituationCluster(new Antragsnummer(antragsnummer));

        MonatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterMemento memento = cluster.memento();
        assertEquals(antragsnummer, memento.antragsnummer());
        assertNull(memento.einnahmen());
        assertNull(memento.ausgaben());
        assertNull(memento.neueDarlehensBelastungen());
    }

    @Test
    void mementoVollstaendigZuCluster() {
        String antragsnummer = "123";
        int einnamen = 6000;
        int ausgaben = 2000;
        int neueDarlehensbelastungen = 500;

        MonatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterMemento memento = new MonatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterMemento(antragsnummer, BigDecimal.valueOf(einnamen), BigDecimal.valueOf(ausgaben), BigDecimal.valueOf(neueDarlehensbelastungen));
        MonatlicheFinanzsituationCluster cluster = MonatlicheFinanzsituationCluster.fromMemento(memento);

        assertEquals(new Antragsnummer(antragsnummer), cluster.antragsnummer());
        ClusterScoringEvent clusterScoringEvent = cluster.scoren();
        assertEquals(ClusterGescored.class, clusterScoringEvent.getClass());
        assertEquals(new Punkte(15), ((ClusterGescored)clusterScoringEvent).punkte());
    }

    @Test
    void mementoMinimalZuCluster() {
        String antragsnummer = "123";

        MonatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterMemento memento = new MonatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterMemento(antragsnummer, null, null, null);
        MonatlicheFinanzsituationCluster cluster = MonatlicheFinanzsituationCluster.fromMemento(memento);

        assertEquals(new Antragsnummer(antragsnummer), cluster.antragsnummer());
        ClusterScoringEvent clusterScoringEvent = cluster.scoren();
        assertEquals(ClusterKonnteNochNichtGescoredWerden.class, clusterScoringEvent.getClass());
    }
}
