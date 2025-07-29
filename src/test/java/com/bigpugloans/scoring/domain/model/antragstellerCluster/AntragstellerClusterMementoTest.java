package com.bigpugloans.scoring.domain.model.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AntragstellerClusterMementoTest {
    @Test
    void testMementoVollstaendigToAntragstellerCluster() {
        AntragstellerCluster.AntragstellerClusterMemento memento = new AntragstellerCluster.AntragstellerClusterMemento(ScoringId.mainScoringIdAusAntragsnummer("123"), "München", new BigDecimal(11000));
        AntragstellerCluster antragstellerCluster = AntragstellerCluster.fromMemento(memento);

        assertEquals(ScoringId.mainScoringIdAusAntragsnummer("123"), antragstellerCluster.scoringId());
        Optional<ClusterGescored> event = antragstellerCluster.scoren();
        assertTrue(event.isPresent());
        assertEquals(new Punkte(10), event.get().punkte());
    }

    @Test
    void testMementoOhneWohnortToAntragstellerCluster() {
        AntragstellerCluster.AntragstellerClusterMemento memento = new AntragstellerCluster.AntragstellerClusterMemento(ScoringId.mainScoringIdAusAntragsnummer("123"), null, new BigDecimal(11000));
        AntragstellerCluster antragstellerCluster = AntragstellerCluster.fromMemento(memento);

        assertEquals(ScoringId.mainScoringIdAusAntragsnummer("123"), antragstellerCluster.scoringId());
        Optional<ClusterGescored> event = antragstellerCluster.scoren();
        assertFalse(event.isPresent());
    }

    @Test
    void testMementoOhneGuthabenToAntragstellerCluster() {
        AntragstellerCluster.AntragstellerClusterMemento memento = new AntragstellerCluster.AntragstellerClusterMemento(ScoringId.mainScoringIdAusAntragsnummer("123"), "München", null);
        AntragstellerCluster antragstellerCluster = AntragstellerCluster.fromMemento(memento);

        assertEquals(ScoringId.mainScoringIdAusAntragsnummer("123"), antragstellerCluster.scoringId());
        Optional<ClusterGescored> event = antragstellerCluster.scoren();
        assertTrue(event.isPresent());
        assertEquals(new Punkte(5), event.get().punkte());
    }

    @Test
    void testMementoOhneAntragsnummerToAntragstellerCluster() {
        AntragstellerCluster.AntragstellerClusterMemento memento = new AntragstellerCluster.AntragstellerClusterMemento(null, "München", new BigDecimal(11000));
        assertThrows(IllegalArgumentException.class, () -> AntragstellerCluster.fromMemento(memento));
    }
    @Test
    void testAntragstellerClusterToMemento() {

        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(ScoringId.mainScoringIdAusAntragsnummer("123"));
        AntragstellerCluster.AntragstellerClusterMemento memento = antragstellerCluster.memento();

        assertEquals(BigDecimal.ZERO, memento.guthaben());
        assertNull(memento.wohnort());
        assertEquals(ScoringId.mainScoringIdAusAntragsnummer("123"), memento.scoringId());

        antragstellerCluster.wohnortHinzufuegen("München");
        memento = antragstellerCluster.memento();
        assertEquals(BigDecimal.ZERO, memento.guthaben());
        assertEquals("München", memento.wohnort());
        assertEquals(ScoringId.mainScoringIdAusAntragsnummer("123"), memento.scoringId());

        antragstellerCluster.guthabenHinzufuegen(new Waehrungsbetrag(11000));
        memento = antragstellerCluster.memento();
        assertEquals(new BigDecimal(11000), memento.guthaben());
        assertEquals("München", memento.wohnort());
        assertEquals(ScoringId.mainScoringIdAusAntragsnummer("123"), memento.scoringId());
    }
}
