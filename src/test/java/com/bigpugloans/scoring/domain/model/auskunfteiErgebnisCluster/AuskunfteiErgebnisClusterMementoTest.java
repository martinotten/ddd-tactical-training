package com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuskunfteiErgebnisClusterMementoTest {
    @Test
    void testMementoVollstaendig() {
        final AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(new Date(1970, 1, 1))
                .build();
        final Antragsnummer antragsnummer = new Antragsnummer("123");

        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster(antragsnummer, antragstellerID);
        auskunfteiErgebnisCluster.warnungenHinzufuegen(2);
        auskunfteiErgebnisCluster.negativMerkmaleHinzufuegen(1);
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(20));

        AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento memento = auskunfteiErgebnisCluster.memento();

        assertEquals(2, memento.anzahlWarnungen());
        assertEquals(1, memento.anzahlNegativMerkmale());
        assertEquals(new BigDecimal(20), memento.rueckzahlungsWahrscheinlichkeit());
        assertEquals(antragsnummer.nummer(), memento.antragsnummer());
        assertEquals(antragstellerID.id(), memento.antragstellerID());
    }

    @Test
    void testMementoOhneRueckzahlungswahrscheinlichkeit() {
        final AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(new Date(1970, 1, 1))
                .build();
        final Antragsnummer antragsnummer = new Antragsnummer("123");

        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster(antragsnummer, antragstellerID);
        auskunfteiErgebnisCluster.warnungenHinzufuegen(2);
        auskunfteiErgebnisCluster.negativMerkmaleHinzufuegen(1);

        AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento memento = auskunfteiErgebnisCluster.memento();

        assertEquals(2, memento.anzahlWarnungen());
        assertEquals(1, memento.anzahlNegativMerkmale());
        assertEquals(null, memento.rueckzahlungsWahrscheinlichkeit());
        assertEquals(antragsnummer.nummer(), memento.antragsnummer());
        assertEquals(antragstellerID.id(), memento.antragstellerID());
    }

    @Test
    void testMementoOhneWarnungen() {
        final AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(new Date(1970, 1, 1))
                .build();
        final Antragsnummer antragsnummer = new Antragsnummer("123");

        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster(antragsnummer, antragstellerID);
        auskunfteiErgebnisCluster.negativMerkmaleHinzufuegen(1);
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(20));

        AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento memento = auskunfteiErgebnisCluster.memento();

        assertEquals(0, memento.anzahlWarnungen());
        assertEquals(1, memento.anzahlNegativMerkmale());
        assertEquals(new BigDecimal(20), memento.rueckzahlungsWahrscheinlichkeit());
        assertEquals(antragsnummer.nummer(), memento.antragsnummer());
        assertEquals(antragstellerID.id(), memento.antragstellerID());
    }

    @Test
    void testMementoOhneNegativMerkmale() {
        final AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(new Date(1970, 1, 1))
                .build();
        final Antragsnummer antragsnummer = new Antragsnummer("123");

        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster(antragsnummer, antragstellerID);
        auskunfteiErgebnisCluster.warnungenHinzufuegen(1);
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(20));

        AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento memento = auskunfteiErgebnisCluster.memento();

        assertEquals(1, memento.anzahlWarnungen());
        assertEquals(0, memento.anzahlNegativMerkmale());
        assertEquals(new BigDecimal(20), memento.rueckzahlungsWahrscheinlichkeit());
        assertEquals(antragsnummer.nummer(), memento.antragsnummer());
        assertEquals(antragstellerID.id(), memento.antragstellerID());
    }

    @Test
    void clusterAusVollstaendigemMementoKeinKo() {
        String antragsnummer = "123";
        String antragstellerID = "23432df312";
        int rueckzahlungsWahrscheinlichkeit = 90;
        AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento memento = new AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento(antragsnummer, antragstellerID, 0, 1, new BigDecimal(rueckzahlungsWahrscheinlichkeit));
        AuskunfteiErgebnisCluster cluster = AuskunfteiErgebnisCluster.fromMemento(memento);
        assertEquals(new Antragsnummer(antragsnummer), cluster.antragsnummer());
        assertEquals(new AntragstellerID(antragstellerID), cluster.antragstellerID());
        ClusterScoringEvent event = cluster.scoren();
        assertEquals(ClusterGescored.class, event.getClass());
        assertEquals(new Punkte(rueckzahlungsWahrscheinlichkeit), ((ClusterGescored)event).punkte());
        assertEquals(0, ((ClusterGescored)event).koKriterien().anzahl());
        assertEquals(new Antragsnummer(antragsnummer), event.antragsnummer());
    }

    @Test
    void clusterAusVollstaendigemMementoMitKoNegativUndWarnung() {
        String antragsnummer = "123";
        String antragstellerID = "23432df312";
        int rueckzahlungsWahrscheinlichkeit = 90;
        AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento memento = new AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento(antragsnummer, antragstellerID, 1, 5, new BigDecimal(rueckzahlungsWahrscheinlichkeit));
        AuskunfteiErgebnisCluster cluster = AuskunfteiErgebnisCluster.fromMemento(memento);
        assertEquals(new Antragsnummer(antragsnummer), cluster.antragsnummer());
        assertEquals(new AntragstellerID(antragstellerID), cluster.antragstellerID());
        ClusterScoringEvent event = cluster.scoren();
        assertEquals(ClusterGescored.class, event.getClass());
        assertEquals(new Punkte(rueckzahlungsWahrscheinlichkeit), ((ClusterGescored)event).punkte());
        assertEquals(2, ((ClusterGescored)event).koKriterien().anzahl());
        assertEquals(new Antragsnummer(antragsnummer), event.antragsnummer());
    }

    @Test
    void clusterOhneRueckzahlungswahrscheinlichkeitMemento() {
        String antragsnummer = "123";
        String antragstellerID = "23432df312";
        int rueckzahlungsWahrscheinlichkeit = 90;
        AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento memento = new AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento(antragsnummer, antragstellerID, 1, 5, null);
        AuskunfteiErgebnisCluster cluster = AuskunfteiErgebnisCluster.fromMemento(memento);
        assertEquals(new Antragsnummer(antragsnummer), cluster.antragsnummer());
        assertEquals(new AntragstellerID(antragstellerID), cluster.antragstellerID());
        ClusterScoringEvent event = cluster.scoren();
        assertEquals(ClusterKonnteNochNichtGescoredWerden.class, event.getClass());
        assertEquals(new Antragsnummer(antragsnummer), event.antragsnummer());

    }
}
