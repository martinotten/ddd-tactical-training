package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScoreImmobilienFinanzierungsClusterDomainServiceTest {
    @Test
    void testScoreImmobilienFinanzierungsClusterVollstaendigGruen() {
        final Antragsnummer antragsnummer = new Antragsnummer("123");

        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster(antragsnummer);
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(new Waehrungsbetrag(280000));
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(110000));
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(10000));
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(200000));
        immobilienFinanzierungsCluster.marktwertHinzufuegen(new Waehrungsbetrag(300000));
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(new Waehrungsbetrag(220000),
                new Waehrungsbetrag(400000),
                new Waehrungsbetrag(270000),
                new Waehrungsbetrag(320000)
        );

        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(antragsnummer);
        scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));
        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));

        ScoreImmobilienFinanzierungsClusterDomainService service = new ScoreImmobilienFinanzierungsClusterDomainService();
        scoringErgebnis = service.scoreImmobilienFinanzierungsCluster(immobilienFinanzierungsCluster, scoringErgebnis);

        AntragScoringEvent antragScoringEvent = scoringErgebnis.berechneErgebnis();
        assertEquals(AntragErfolgreichGescored.class, antragScoringEvent.getClass());
        assertEquals(ScoringFarbe.GRUEN, ((AntragErfolgreichGescored) antragScoringEvent).farbe());

    }

    @Test
    void testScoreImmobilienFinanzierungsClusterVollstaendigROT() {
        final Antragsnummer antragsnummer = new Antragsnummer("123");

        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster(antragsnummer);
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(new Waehrungsbetrag(280000));
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(110000));
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(10000));
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(400000));
        immobilienFinanzierungsCluster.marktwertHinzufuegen(new Waehrungsbetrag(300000));
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(new Waehrungsbetrag(220000),
                new Waehrungsbetrag(400000),
                new Waehrungsbetrag(270000),
                new Waehrungsbetrag(320000)
        );

        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(antragsnummer);
        scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));
        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));

        ScoreImmobilienFinanzierungsClusterDomainService service = new ScoreImmobilienFinanzierungsClusterDomainService();
        scoringErgebnis = service.scoreImmobilienFinanzierungsCluster(immobilienFinanzierungsCluster, scoringErgebnis);

        AntragScoringEvent antragScoringEvent = scoringErgebnis.berechneErgebnis();
        assertEquals(AntragErfolgreichGescored.class, antragScoringEvent.getClass());
        assertEquals(ScoringFarbe.ROT, ((AntragErfolgreichGescored) antragScoringEvent).farbe());
    }

    @Test
    void testScoreImmobilienFinanzierungsClusterNichtVollstaendig() {
        final Antragsnummer antragsnummer = new Antragsnummer("123");

        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster(antragsnummer);
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(new Waehrungsbetrag(280000));
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(110000));
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(10000));
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(200000));
        immobilienFinanzierungsCluster.marktwertHinzufuegen(new Waehrungsbetrag(300000));
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(new Waehrungsbetrag(220000),
                new Waehrungsbetrag(400000),
                new Waehrungsbetrag(270000),
                new Waehrungsbetrag(320000)
        );

        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(antragsnummer);
        scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));
        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));

        ScoreImmobilienFinanzierungsClusterDomainService service = new ScoreImmobilienFinanzierungsClusterDomainService();
        scoringErgebnis = service.scoreImmobilienFinanzierungsCluster(immobilienFinanzierungsCluster, scoringErgebnis);

        AntragScoringEvent antragScoringEvent = scoringErgebnis.berechneErgebnis();
        assertEquals(AntragKonnteNichtGescoredWerden.class, antragScoringEvent.getClass());
        assertEquals(new Antragsnummer("123"), ((AntragKonnteNichtGescoredWerden) antragScoringEvent).antragsnummer());
        assertNotNull(((AntragKonnteNichtGescoredWerden) antragScoringEvent).hinweis());
        System.out.println(((AntragKonnteNichtGescoredWerden) antragScoringEvent).hinweis());
    }
}
