package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScoreMonatlicheFinanzsituationClusterDomainServiceTest {
    @Test
    void testScoreMonatlicheFinanzsituationClusterVollstaendigGruen() {
        final Antragsnummer antragsnummer = new Antragsnummer("123");

        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster = new MonatlicheFinanzsituationCluster(antragsnummer);
        monatlicheFinanzsituationCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(1000));
        monatlicheFinanzsituationCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(2500));
        monatlicheFinanzsituationCluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(800));
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(antragsnummer);
        scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));
        scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));

        ScoreMonatlicheFinanzsituationClusterDomainService service = new ScoreMonatlicheFinanzsituationClusterDomainService();
        scoringErgebnis = service.scoreMonatlicheFinanzsituationCluster(monatlicheFinanzsituationCluster, scoringErgebnis);
        AntragScoringEvent antragScoringEvent = scoringErgebnis.berechneErgebnis();
        assertEquals(AntragErfolgreichGescored.class, antragScoringEvent.getClass());
        assertEquals(ScoringFarbe.GRUEN, ((AntragErfolgreichGescored) antragScoringEvent).farbe());

    }

    @Test
    void testScoreMonatlicheFinanzsituationClusterVollstaendigROT() {
        final Antragsnummer antragsnummer = new Antragsnummer("123");

        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster = new MonatlicheFinanzsituationCluster(antragsnummer);
        monatlicheFinanzsituationCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(1000));
        monatlicheFinanzsituationCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(1500));
        monatlicheFinanzsituationCluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(800));
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(antragsnummer);
        scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));
        scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));

        ScoreMonatlicheFinanzsituationClusterDomainService service = new ScoreMonatlicheFinanzsituationClusterDomainService();
        scoringErgebnis = service.scoreMonatlicheFinanzsituationCluster(monatlicheFinanzsituationCluster, scoringErgebnis);

        AntragScoringEvent antragScoringEvent = scoringErgebnis.berechneErgebnis();
        assertEquals(AntragErfolgreichGescored.class, antragScoringEvent.getClass());
        assertEquals(ScoringFarbe.ROT, ((AntragErfolgreichGescored) antragScoringEvent).farbe());
    }

    @Test
    void testScoreMonatlicheFinanzsituationClusterNichtVollstaendig() {
        final Antragsnummer antragsnummer = new Antragsnummer("123");

        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster = new MonatlicheFinanzsituationCluster(antragsnummer);
        monatlicheFinanzsituationCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(1000));
        monatlicheFinanzsituationCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(2500));
        monatlicheFinanzsituationCluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(800));
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(antragsnummer);
        scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));

        ScoreMonatlicheFinanzsituationClusterDomainService service = new ScoreMonatlicheFinanzsituationClusterDomainService();
        scoringErgebnis = service.scoreMonatlicheFinanzsituationCluster(monatlicheFinanzsituationCluster, scoringErgebnis);

        AntragScoringEvent antragScoringEvent = scoringErgebnis.berechneErgebnis();
        assertEquals(AntragKonnteNichtGescoredWerden.class, antragScoringEvent.getClass());
        assertEquals(antragsnummer, ((AntragKonnteNichtGescoredWerden) antragScoringEvent).antragsnummer());
        assertNotNull(((AntragKonnteNichtGescoredWerden) antragScoringEvent).hinweis());
        System.out.println(((AntragKonnteNichtGescoredWerden) antragScoringEvent).hinweis());
    }
}
