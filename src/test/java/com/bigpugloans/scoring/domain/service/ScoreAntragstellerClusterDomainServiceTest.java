package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScoreAntragstellerClusterDomainServiceTest {
    @Test
    void testScoreAntragstellerClusterVollstaendigGruen() {
        AntragstellerCluster antragsterllerCluster = new AntragstellerCluster(new Antragsnummer("123"));
        antragsterllerCluster.guthabenHinzufuegen(new Waehrungsbetrag(11000));
        antragsterllerCluster.wohnortHinzufuegen("München");
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(new Antragsnummer("123"));
        scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(100), new KoKriterien(0)));
        scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(100), new KoKriterien(0)));
        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(100), new KoKriterien(0)));
        ScoreAntragstellerClusterDomainService service = new ScoreAntragstellerClusterDomainService();
        scoringErgebnis = service.scoreAntragstellerCluster(antragsterllerCluster, scoringErgebnis);
        AntragScoringEvent antragScoringEvent = scoringErgebnis.berechneErgebnis();
        assertEquals(AntragErfolgreichGescored.class, antragScoringEvent.getClass());
        assertEquals(ScoringFarbe.GRUEN, ((AntragErfolgreichGescored) antragScoringEvent).farbe());

    }

    @Test
    void testScoreAntragstellerClusterVollstaendigROT() {
        AntragstellerCluster antragsterllerCluster = new AntragstellerCluster(new Antragsnummer("123"));
        antragsterllerCluster.guthabenHinzufuegen(new Waehrungsbetrag(11000));
        antragsterllerCluster.wohnortHinzufuegen("München");
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(new Antragsnummer("123"));
        scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(2), new KoKriterien(0)));
        scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(2), new KoKriterien(0)));
        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(2), new KoKriterien(0)));
        ScoreAntragstellerClusterDomainService service = new ScoreAntragstellerClusterDomainService();
        scoringErgebnis = service.scoreAntragstellerCluster(antragsterllerCluster, scoringErgebnis);
        AntragScoringEvent antragScoringEvent = scoringErgebnis.berechneErgebnis();
        assertEquals(AntragErfolgreichGescored.class, antragScoringEvent.getClass());
        assertEquals(ScoringFarbe.ROT, ((AntragErfolgreichGescored) antragScoringEvent).farbe());
    }

    @Test
    void testScoreAntragstellerClusterNichtVollstaendig() {
        AntragstellerCluster antragsterllerCluster = new AntragstellerCluster(new Antragsnummer("123"));
        antragsterllerCluster.guthabenHinzufuegen(new Waehrungsbetrag(11000));
        antragsterllerCluster.wohnortHinzufuegen("München");
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(new Antragsnummer("123"));
        scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(122), new KoKriterien(0)));
        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(new Antragsnummer("123"), new Punkte(2), new KoKriterien(0)));
        ScoreAntragstellerClusterDomainService service = new ScoreAntragstellerClusterDomainService();
        scoringErgebnis = service.scoreAntragstellerCluster(antragsterllerCluster, scoringErgebnis);
        AntragScoringEvent antragScoringEvent = scoringErgebnis.berechneErgebnis();
        assertEquals(AntragKonnteNichtGescoredWerden.class, antragScoringEvent.getClass());
        assertEquals(new Antragsnummer("123"), ((AntragKonnteNichtGescoredWerden) antragScoringEvent).antragsnummer());
        assertNotNull(((AntragKonnteNichtGescoredWerden) antragScoringEvent).hinweis());
        System.out.println(((AntragKonnteNichtGescoredWerden) antragScoringEvent).hinweis());
    }

}
