package com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ImmobilienFinanzierungsClusterMementoTest {
    @Test
    void testImmobilienFinanzierungsClusterMementoKomplett() {
        final ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("123");
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster(scoringId);
        immobilienFinanzierungsCluster.marktwertHinzufuegen(new Waehrungsbetrag(100000));
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(new Waehrungsbetrag(100000), new Waehrungsbetrag(300000), new Waehrungsbetrag(200000), new Waehrungsbetrag(250000));
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(25000));
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(75000));
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(15000));
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(new Waehrungsbetrag(150000));
        ImmobilienFinanzierungsCluster.ImmobilienFinanzierungsClusterMemento memento = immobilienFinanzierungsCluster.memento();

        assertEquals(scoringId, memento.scoringId());
        assertEquals(BigDecimal.valueOf(100000), memento.marktwert());
        assertEquals(BigDecimal.valueOf(25000), memento.eigenmittel());
        assertEquals(BigDecimal.valueOf(75000), memento.summeDarlehen());
        assertEquals(BigDecimal.valueOf(15000), memento.kaufnebenkosten());
        assertEquals(BigDecimal.valueOf(150000), memento.beleihungswert());
        assertEquals(BigDecimal.valueOf(100000), memento.minimalerMarktwert());
        assertEquals(BigDecimal.valueOf(300000), memento.maximalerMarktwert());
        assertEquals(BigDecimal.valueOf(200000), memento.durchschnittlicherMarktwertVon());
        assertEquals(BigDecimal.valueOf(250000), memento.durchschnittlicherMarktwertBis());
    }

    @Test
    void testImmobilienFinanzierungsClusterMementoMinimal() {
        ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("123");
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster(scoringId);
        ImmobilienFinanzierungsCluster.ImmobilienFinanzierungsClusterMemento memento = immobilienFinanzierungsCluster.memento();

        assertEquals(scoringId, memento.scoringId());
        assertNull(memento.marktwert());
        assertNull(memento.eigenmittel());
        assertNull(memento.summeDarlehen());
        assertNull(memento.kaufnebenkosten());
        assertNull(memento.beleihungswert());
        assertNull(memento.minimalerMarktwert());
        assertNull(memento.maximalerMarktwert());
        assertNull(memento.durchschnittlicherMarktwertVon());
        assertNull(memento.durchschnittlicherMarktwertBis());
    }

    @Test
    void testVollstaendigesMementoZuImmobilienFinanzierungsCluster() {
        ImmobilienFinanzierungsCluster.ImmobilienFinanzierungsClusterMemento memento = new ImmobilienFinanzierungsCluster.ImmobilienFinanzierungsClusterMemento(ScoringId.preScoringIdAusAntragsnummer("123"));


        memento.marktwert(BigDecimal.valueOf(100000));
        memento.eigenmittel(BigDecimal.valueOf(25000));
        memento.summeDarlehen(BigDecimal.valueOf(75000));
        memento.kaufnebenkosten(BigDecimal.valueOf(15000));
        memento.beleihungswert(BigDecimal.valueOf(150000));
        memento.minimalerMarktwert(BigDecimal.valueOf(100000));
        memento.maximalerMarktwert(BigDecimal.valueOf(300000));
        memento.durchschnittlicherMarktwertVon(BigDecimal.valueOf(200000));
        memento.durchschnittlicherMarktwertBis(BigDecimal.valueOf(250000));

        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = ImmobilienFinanzierungsCluster.fromMemento(memento);
        assertEquals(ScoringId.preScoringIdAusAntragsnummer("123"), immobilienFinanzierungsCluster.scoringId());
        Optional<ClusterGescored> event = immobilienFinanzierungsCluster.scoren();
        assertTrue(event.isPresent());
        assertEquals(new Punkte(10), event.get().punkte());
    }

    @Test
    void testMinimalesMementoZuImmobilienFinanzierungsCluster() {
        final ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("123");
        ImmobilienFinanzierungsCluster.ImmobilienFinanzierungsClusterMemento memento = new ImmobilienFinanzierungsCluster.ImmobilienFinanzierungsClusterMemento(scoringId);

        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = ImmobilienFinanzierungsCluster.fromMemento(memento);
        assertEquals(scoringId, immobilienFinanzierungsCluster.scoringId());
        Optional<ClusterGescored> event = immobilienFinanzierungsCluster.scoren();
        assertTrue(event.isEmpty());
    }
}
