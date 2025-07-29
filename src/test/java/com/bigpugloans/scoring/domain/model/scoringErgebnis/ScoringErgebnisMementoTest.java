package com.bigpugloans.scoring.domain.model.scoringErgebnis;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ScoringErgebnisMementoTest {

    final ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("152");

    @Test
    void testScoringErgebnisVollstaendigOhneKoZuMemento() {
        ScoringErgebnis.ScoringErgebnisMemento memento = getScoringErgebnisMemento();
        assertEquals(scoringId, memento.scoringId());
        assertEquals(10, memento.antragstellerClusterErgebnis().punkte().getPunkte());
        assertEquals(0, memento.antragstellerClusterErgebnis().koKriterien().anzahl());

        assertEquals(90, memento.auskunfteiClusterErgebnis().punkte().getPunkte());
        assertEquals(0, memento.auskunfteiClusterErgebnis().koKriterien().anzahl());

        assertEquals(20, memento.monatlicherHaushaltsueberschussClusterErgebnis().punkte().getPunkte());
        assertEquals(0, memento.monatlicherHaushaltsueberschussClusterErgebnis().koKriterien().anzahl());

        assertEquals(30, memento.immobilienFinanzierungsClusterErgebnis().punkte().getPunkte());
        assertEquals(0, memento.antragstellerClusterErgebnis().koKriterien().anzahl());
        assertEquals(150, memento.gesamtPunkte());
        assertEquals(0, memento.koKriterien());
    }

    private ScoringErgebnis.ScoringErgebnisMemento getScoringErgebnisMemento() {
        ScoringErgebnis ergebnis = new ScoringErgebnis(scoringId);
        ergebnis.antragstellerClusterHinzufuegen(new ClusterGescored(scoringId, new Punkte(10)));
        ergebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(scoringId, new Punkte(90)));
        ergebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(scoringId, new Punkte(20)));
        ergebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(scoringId, new Punkte(30)));
        return ergebnis.memento();
    }

    @Test
    void testScoringErgebnisVollstaendigMitKoZuMemento() {
        ScoringErgebnis.ScoringErgebnisMemento memento = getErgebnisMemento();
        assertEquals(scoringId, memento.scoringId());
        assertEquals(10, memento.antragstellerClusterErgebnis().punkte().getPunkte());
        assertEquals(1, memento.antragstellerClusterErgebnis().koKriterien().anzahl());

        assertEquals(90, memento.auskunfteiClusterErgebnis().punkte().getPunkte());
        assertEquals(2, memento.auskunfteiClusterErgebnis().koKriterien().anzahl());

        assertEquals(20, memento.monatlicherHaushaltsueberschussClusterErgebnis().punkte().getPunkte());
        assertEquals(3, memento.monatlicherHaushaltsueberschussClusterErgebnis().koKriterien().anzahl());

        assertEquals(30, memento.immobilienFinanzierungsClusterErgebnis().punkte().getPunkte());
        assertEquals(1, memento.antragstellerClusterErgebnis().koKriterien().anzahl());
        assertEquals(150, memento.gesamtPunkte());
        assertEquals(10, memento.koKriterien());
    }

    private ScoringErgebnis.ScoringErgebnisMemento getErgebnisMemento() {
        ScoringErgebnis ergebnis = new ScoringErgebnis(scoringId);
        ergebnis.antragstellerClusterHinzufuegen(new ClusterGescored(scoringId, new Punkte(10), 1));
        ergebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(scoringId, new Punkte(90), 2));
        ergebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(scoringId, new Punkte(20),3));
        ergebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(scoringId, new Punkte(30),4 ));
        ScoringErgebnis.ScoringErgebnisMemento memento = ergebnis.memento();
        return memento;
    }

    @Test
    void testScoringErgebnisTeilweiseMitKoZuMemento() {
        ScoringErgebnis ergebnis = new ScoringErgebnis(scoringId);
        ergebnis.antragstellerClusterHinzufuegen(new ClusterGescored(scoringId, new Punkte(10), 1));
        ergebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(scoringId, new Punkte(90), 2));

        ScoringErgebnis.ScoringErgebnisMemento memento = ergebnis.memento();
        assertEquals(scoringId, memento.scoringId());
        assertEquals(10, memento.antragstellerClusterErgebnis().punkte().getPunkte());
        assertEquals(1, memento.antragstellerClusterErgebnis().koKriterien().anzahl());

        assertEquals(90, memento.auskunfteiClusterErgebnis().punkte().getPunkte());
        assertEquals(2, memento.auskunfteiClusterErgebnis().koKriterien().anzahl());

        assertNull(memento.monatlicherHaushaltsueberschussClusterErgebnis());
        assertNull(memento.immobilienFinanzierungsClusterErgebnis());
        assertEquals(100, memento.gesamtPunkte());
        assertEquals(3, memento.koKriterien());
    }

    @Test
    void testMementoZuScoringErgebnisVollstaendig() {
        ClusterGescored antragstellerCluster = new ClusterGescored(scoringId, new Punkte(10), 1);
        ClusterGescored auskunfteiErgebnisCluster = new ClusterGescored(scoringId, new Punkte(20), 2);
        ClusterGescored monatlicheFinanzsituationCluster = new ClusterGescored(scoringId, new Punkte(30), 3);
        ClusterGescored immobilienFinanzierungsCluster = new ClusterGescored(scoringId, new Punkte(40), 4);
        ScoringErgebnis.ScoringErgebnisMemento memento = new ScoringErgebnis.ScoringErgebnisMemento(scoringId, antragstellerCluster, auskunfteiErgebnisCluster, monatlicheFinanzsituationCluster, immobilienFinanzierungsCluster, 10, 100);

        ScoringErgebnis ergebnis = ScoringErgebnis.fromMemento(memento);
        assertEquals(scoringId, ergebnis.scoringId());
        Optional<AntragErfolgreichGescored> antragErfolgreichGescored = ergebnis.berechneErgebnis();
        assertTrue(antragErfolgreichGescored.isPresent());
        assertEquals(ScoringFarbe.ROT, antragErfolgreichGescored.get().farbe());

    }
}
