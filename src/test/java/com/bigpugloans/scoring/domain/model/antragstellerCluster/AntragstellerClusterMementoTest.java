package com.bigpugloans.scoring.domain.model.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AntragstellerClusterMementoTest {
    @Test
    void testMementoVollstaendigToAntragstellerCluster() {
        AntragstellerCluster.AntragstellerClusterMemento memento = new AntragstellerCluster.AntragstellerClusterMemento("123", "München", new BigDecimal(11000));
        AntragstellerCluster antragstellerCluster = AntragstellerCluster.fromMemento(memento);

        assertEquals(new Antragsnummer("123"), antragstellerCluster.antragsnummer());
        ClusterScoringEvent event = antragstellerCluster.scoren();
        assertEquals(ClusterGescored.class, event.getClass());
        assertEquals(new Punkte(10), ((ClusterGescored)event).punkte());
    }

    @Test
    void testMementoOhneWohnortToAntragstellerCluster() {
        AntragstellerCluster.AntragstellerClusterMemento memento = new AntragstellerCluster.AntragstellerClusterMemento("123", null, new BigDecimal(11000));
        AntragstellerCluster antragstellerCluster = AntragstellerCluster.fromMemento(memento);

        assertEquals(new Antragsnummer("123"), antragstellerCluster.antragsnummer());
        ClusterScoringEvent event = antragstellerCluster.scoren();
        assertEquals(ClusterKonnteNochNichtGescoredWerden.class, event.getClass());
    }

    @Test
    void testMementoOhneGuthabenToAntragstellerCluster() {
        AntragstellerCluster.AntragstellerClusterMemento memento = new AntragstellerCluster.AntragstellerClusterMemento("123", "München", null);
        AntragstellerCluster antragstellerCluster = AntragstellerCluster.fromMemento(memento);

        assertEquals(new Antragsnummer("123"), antragstellerCluster.antragsnummer());
        ClusterScoringEvent event = antragstellerCluster.scoren();
        assertEquals(ClusterGescored.class, event.getClass());
        assertEquals(new Punkte(5), ((ClusterGescored)event).punkte());
    }

    @Test
    void testMementoOhneAntragsnummerToAntragstellerCluster() {
        AntragstellerCluster.AntragstellerClusterMemento memento = new AntragstellerCluster.AntragstellerClusterMemento(null, "München", new BigDecimal(11000));
        assertThrows(IllegalArgumentException.class, () -> AntragstellerCluster.fromMemento(memento));
    }
    @Test
    void testAntragstellerClusterToMemento() {

        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(new Antragsnummer("123"));
        AntragstellerCluster.AntragstellerClusterMemento memento = antragstellerCluster.memento();

        assertEquals(BigDecimal.ZERO, memento.guthaben());
        assertNull(memento.wohnort());
        assertEquals("123", memento.antragsnummer());

        antragstellerCluster.wohnortHinzufuegen("München");
        memento = antragstellerCluster.memento();
        assertEquals(BigDecimal.ZERO, memento.guthaben());
        assertEquals("München", memento.wohnort());
        assertEquals("123", memento.antragsnummer());

        antragstellerCluster.guthabenHinzufuegen(new Waehrungsbetrag(11000));
        memento = antragstellerCluster.memento();
        assertEquals(new BigDecimal(11000), memento.guthaben());
        assertEquals("München", memento.wohnort());
        assertEquals("123", memento.antragsnummer());
    }
}
