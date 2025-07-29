package com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AuskunfteiErgebnisClusterMementoTest {
    @Test
    void testMementoVollstaendig() {
        final AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(LocalDate.of(1970, 2, 1))
                .build();
        final ScoringId scoringId = ScoringId.mainScoringIdAusAntragsnummer("123");

        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster(scoringId, antragstellerID);
        auskunfteiErgebnisCluster.warnungenHinzufuegen(2);
        auskunfteiErgebnisCluster.negativMerkmaleHinzufuegen(1);
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(20));

        AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento memento = auskunfteiErgebnisCluster.memento();

        assertEquals(2, memento.anzahlWarnungen());
        assertEquals(1, memento.anzahlNegativMerkmale());
        assertEquals(new BigDecimal(20), memento.rueckzahlungsWahrscheinlichkeit());
        assertEquals(scoringId, memento.scoringId());
        assertEquals(antragstellerID.id(), memento.antragstellerID());
    }

    @Test
    void testMementoOhneRueckzahlungswahrscheinlichkeit() {
        final AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(LocalDate.of(1970, 2, 1))
                .build();
        final ScoringId scoringId = ScoringId.mainScoringIdAusAntragsnummer("123");

        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster(scoringId, antragstellerID);
        auskunfteiErgebnisCluster.warnungenHinzufuegen(2);
        auskunfteiErgebnisCluster.negativMerkmaleHinzufuegen(1);

        AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento memento = auskunfteiErgebnisCluster.memento();

        assertEquals(2, memento.anzahlWarnungen());
        assertEquals(1, memento.anzahlNegativMerkmale());
        assertEquals(null, memento.rueckzahlungsWahrscheinlichkeit());
        assertEquals(scoringId, memento.scoringId());
        assertEquals(antragstellerID.id(), memento.antragstellerID());
    }

    @Test
    void testMementoOhneWarnungen() {
        final AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(LocalDate.of(1970, 2, 1))
                .build();
        final ScoringId scoringId = ScoringId.mainScoringIdAusAntragsnummer("123");

        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster(scoringId, antragstellerID);
        auskunfteiErgebnisCluster.negativMerkmaleHinzufuegen(1);
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(20));

        AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento memento = auskunfteiErgebnisCluster.memento();

        assertEquals(0, memento.anzahlWarnungen());
        assertEquals(1, memento.anzahlNegativMerkmale());
        assertEquals(new BigDecimal(20), memento.rueckzahlungsWahrscheinlichkeit());
        assertEquals(scoringId, memento.scoringId());
        assertEquals(antragstellerID.id(), memento.antragstellerID());
    }

    @Test
    void testMementoOhneNegativMerkmale() {
        final AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(LocalDate.of(1970, 2, 1))
                .build();
        final ScoringId scoringId = ScoringId.mainScoringIdAusAntragsnummer("123");

        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster(scoringId, antragstellerID);
        auskunfteiErgebnisCluster.warnungenHinzufuegen(1);
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(20));

        AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento memento = auskunfteiErgebnisCluster.memento();

        assertEquals(1, memento.anzahlWarnungen());
        assertEquals(0, memento.anzahlNegativMerkmale());
        assertEquals(new BigDecimal(20), memento.rueckzahlungsWahrscheinlichkeit());
        assertEquals(scoringId, memento.scoringId());
        assertEquals(antragstellerID.id(), memento.antragstellerID());
    }

    @Test
    void clusterAusVollstaendigemMementoKeinKo() {
        String antragsnummer = "123";
        String antragstellerID = "23432df312";
        int rueckzahlungsWahrscheinlichkeit = 90;
        AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento memento = new AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento(ScoringId.mainScoringIdAusAntragsnummer(antragsnummer), antragstellerID, 0, 1, new BigDecimal(rueckzahlungsWahrscheinlichkeit));
        AuskunfteiErgebnisCluster cluster = AuskunfteiErgebnisCluster.fromMemento(memento);
        assertEquals(ScoringId.mainScoringIdAusAntragsnummer(antragsnummer), cluster.scoringId());
        assertEquals(new AntragstellerID(antragstellerID), cluster.antragstellerID());
        Optional<ClusterGescored> event = cluster.scoren();
        assertTrue(event.isPresent());
        assertEquals(new Punkte(rueckzahlungsWahrscheinlichkeit), event.get().punkte());
        assertEquals(0, event.get().koKriterien().anzahl());
        assertEquals(ScoringId.mainScoringIdAusAntragsnummer(antragsnummer), event.get().scoringId());
    }

    @Test
    void clusterAusVollstaendigemMementoMitKoNegativUndWarnung() {
        String antragsnummer = "123";
        String antragstellerID = "23432df312";
        int rueckzahlungsWahrscheinlichkeit = 90;
        AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento memento = new AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento(ScoringId.mainScoringIdAusAntragsnummer(antragsnummer), antragstellerID, 1, 5, new BigDecimal(rueckzahlungsWahrscheinlichkeit));
        AuskunfteiErgebnisCluster cluster = AuskunfteiErgebnisCluster.fromMemento(memento);
        assertEquals(ScoringId.mainScoringIdAusAntragsnummer(antragsnummer), cluster.scoringId());
        assertEquals(new AntragstellerID(antragstellerID), cluster.antragstellerID());
        Optional<ClusterGescored> event = cluster.scoren();
        assertTrue(event.isPresent());
        assertEquals(new Punkte(rueckzahlungsWahrscheinlichkeit), event.get().punkte());
        assertEquals(2, event.get().koKriterien().anzahl());
        assertEquals(ScoringId.mainScoringIdAusAntragsnummer(antragsnummer), event.get().scoringId());
    }

    @Test
    void clusterOhneRueckzahlungswahrscheinlichkeitMemento() {
        String antragsnummer = "123";
        String antragstellerID = "23432df312";
        int rueckzahlungsWahrscheinlichkeit = 90;
        AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento memento = new AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento(ScoringId.mainScoringIdAusAntragsnummer(antragsnummer), antragstellerID, 1, 5, null);
        AuskunfteiErgebnisCluster cluster = AuskunfteiErgebnisCluster.fromMemento(memento);
        assertEquals(ScoringId.mainScoringIdAusAntragsnummer(antragsnummer), cluster.scoringId());
        assertEquals(new AntragstellerID(antragstellerID), cluster.antragstellerID());
        Optional<ClusterGescored> event = cluster.scoren();
        assertFalse(event.isPresent());

    }
}
