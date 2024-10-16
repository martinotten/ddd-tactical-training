package com.bigpugloans.scoring.domain.model.scoringErgebnis;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ScoringErgebnisMementoTest {
    @Test
    void testScoringErgebnisVollstaendigOhneKoZuMemento() {
        ScoringErgebnis ergebnis = new ScoringErgebnis(new Antragsnummer("123"));
        ergebnis.antragstellerClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(10)));
        ergebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(90)));
        ergebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(20)));
        ergebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(30)));
        ScoringErgebnis.ScoringErgebnisMemento memento = ergebnis.memento();
        assertEquals("123", memento.antragsnummer());
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

    @Test
    void testScoringErgebnisVollstaendigMitKoZuMemento() {
        ScoringErgebnis ergebnis = new ScoringErgebnis(new Antragsnummer("123"));
        ergebnis.antragstellerClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(10), 1));
        ergebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(90), 2));
        ergebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(20),3));
        ergebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(30),4 ));
        ScoringErgebnis.ScoringErgebnisMemento memento = ergebnis.memento();
        assertEquals("123", memento.antragsnummer());
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

    @Test
    void testScoringErgebnisTeilweiseMitKoZuMemento() {
        ScoringErgebnis ergebnis = new ScoringErgebnis(new Antragsnummer("123"));
        ergebnis.antragstellerClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(10), 1));
        ergebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(90), 2));

        ScoringErgebnis.ScoringErgebnisMemento memento = ergebnis.memento();
        assertEquals("123", memento.antragsnummer());
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
        ClusterGescored antragstellerCluster = new ClusterGescored(new Antragsnummer("123"), new Punkte(10), 1);
        ClusterGescored auskunfteiErgebnisCluster = new ClusterGescored(new Antragsnummer("123"), new Punkte(20), 2);
        ClusterGescored monatlicheFinanzsituationCluster = new ClusterGescored(new Antragsnummer("123"), new Punkte(30), 3);
        ClusterGescored immobilienFinanzierungsCluster = new ClusterGescored(new Antragsnummer("123"), new Punkte(40), 4);
        ScoringErgebnis.ScoringErgebnisMemento memento = new ScoringErgebnis.ScoringErgebnisMemento("123", antragstellerCluster, auskunfteiErgebnisCluster, monatlicheFinanzsituationCluster, immobilienFinanzierungsCluster, 10, 100);

        ScoringErgebnis ergebnis = ScoringErgebnis.fromMemento(memento);
        assertEquals(new Antragsnummer("123"), ergebnis.antragsnummer());
        assertEquals(AntragErfolgreichGescored.class, ergebnis.berechneErgebnis().getClass());
        assertEquals(ScoringFarbe.ROT, ((AntragErfolgreichGescored)ergebnis.berechneErgebnis()).farbe());

    }
}
