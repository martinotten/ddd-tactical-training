package com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ImmobilienFinanzierungsClusterMementoTest {
    @Test
    void testImmobilienFinanzierungsClusterMementoKomplett() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster(new Antragsnummer("123"));
        immobilienFinanzierungsCluster.marktwertHinzufuegen(new Waehrungsbetrag(100000));
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(new Waehrungsbetrag(100000), new Waehrungsbetrag(300000), new Waehrungsbetrag(200000), new Waehrungsbetrag(250000));
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(25000));
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(75000));
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(15000));
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(new Waehrungsbetrag(150000));
        ImmobilienFinanzierungsCluster.ImmobilienFinanzierungsClusterMemento memento = immobilienFinanzierungsCluster.memento();

        assertEquals("123", memento.antragsnummer());
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
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster(new Antragsnummer("123"));
        ImmobilienFinanzierungsCluster.ImmobilienFinanzierungsClusterMemento memento = immobilienFinanzierungsCluster.memento();

        assertEquals("123", memento.antragsnummer());
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
        ImmobilienFinanzierungsCluster.ImmobilienFinanzierungsClusterMemento memento = new ImmobilienFinanzierungsCluster.ImmobilienFinanzierungsClusterMemento("123");


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
        assertEquals(new Antragsnummer("123"), immobilienFinanzierungsCluster.antragsnummer());
        ClusterScoringEvent event = immobilienFinanzierungsCluster.scoren();
        assertEquals(ClusterGescored.class, event.getClass());
        assertEquals(new Punkte(10), ((ClusterGescored)event).punkte());
    }

    @Test
    void testMinimalesMementoZuImmobilienFinanzierungsCluster() {
        ImmobilienFinanzierungsCluster.ImmobilienFinanzierungsClusterMemento memento = new ImmobilienFinanzierungsCluster.ImmobilienFinanzierungsClusterMemento("123");

        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = ImmobilienFinanzierungsCluster.fromMemento(memento);
        assertEquals(new Antragsnummer("123"), immobilienFinanzierungsCluster.antragsnummer());
        ClusterScoringEvent event = immobilienFinanzierungsCluster.scoren();
        assertEquals(ClusterKonnteNochNichtGescoredWerden.class, event.getClass());
    }
}
