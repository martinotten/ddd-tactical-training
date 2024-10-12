package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScoreAuskunfteiErgebnisClusterDomainServiceTest {
    @Test
    void testScoreAuskunfteiClusterVollstaendigGruen() {
        final AntragstellerID antragstellerID = erzeugeAntragstellerID();
        final Antragsnummer antragsnummer = new Antragsnummer("123");

        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster(antragsnummer, antragstellerID);
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(100));
        auskunfteiErgebnisCluster.negativMerkmaleHinzufuegen(0);
        auskunfteiErgebnisCluster.warnungenHinzufuegen(0);

        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(antragsnummer);
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));
        scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));
        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));
        ScoreAuskunfteiErgebnisClusterDomainService service = new ScoreAuskunfteiErgebnisClusterDomainService();
        scoringErgebnis = service.scoreAuskunfteiErgebnisCluster(auskunfteiErgebnisCluster, scoringErgebnis);
        AntragScoringEvent antragScoringEvent = scoringErgebnis.berechneErgebnis();
        assertEquals(AntragErfolgreichGescored.class, antragScoringEvent.getClass());
        assertEquals(ScoringFarbe.GRUEN, ((AntragErfolgreichGescored) antragScoringEvent).farbe());
    }

    @Test
    void testScoreAntragstellerClusterVollstaendigROT() {
        final AntragstellerID antragstellerID = erzeugeAntragstellerID();
        final Antragsnummer antragsnummer = new Antragsnummer("123");

        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster(antragsnummer, antragstellerID);
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(100));
        auskunfteiErgebnisCluster.negativMerkmaleHinzufuegen(1);
        auskunfteiErgebnisCluster.warnungenHinzufuegen(0);

        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(antragsnummer);
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));
        scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));
        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));
        ScoreAuskunfteiErgebnisClusterDomainService service = new ScoreAuskunfteiErgebnisClusterDomainService();
        scoringErgebnis = service.scoreAuskunfteiErgebnisCluster(auskunfteiErgebnisCluster, scoringErgebnis);
        AntragScoringEvent antragScoringEvent = scoringErgebnis.berechneErgebnis();
        assertEquals(AntragErfolgreichGescored.class, antragScoringEvent.getClass());
        assertEquals(ScoringFarbe.ROT, ((AntragErfolgreichGescored) antragScoringEvent).farbe());
    }

    @Test
    void testScoreAntragstellerClusterNichtVollstaendig() {

        final AntragstellerID antragstellerID = erzeugeAntragstellerID();
        final Antragsnummer antragsnummer = new Antragsnummer("123");

        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster(antragsnummer, antragstellerID);
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(100));
        auskunfteiErgebnisCluster.negativMerkmaleHinzufuegen(0);
        auskunfteiErgebnisCluster.warnungenHinzufuegen(0);

        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(antragsnummer);
        scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));
        scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(100), new KoKriterien(0)));

        ScoreAuskunfteiErgebnisClusterDomainService service = new ScoreAuskunfteiErgebnisClusterDomainService();
        scoringErgebnis = service.scoreAuskunfteiErgebnisCluster(auskunfteiErgebnisCluster, scoringErgebnis);
        AntragScoringEvent antragScoringEvent = scoringErgebnis.berechneErgebnis();

        assertEquals(AntragKonnteNichtGescoredWerden.class, antragScoringEvent.getClass());
        assertEquals(new Antragsnummer("123"), ((AntragKonnteNichtGescoredWerden) antragScoringEvent).antragsnummer());
        assertNotNull(((AntragKonnteNichtGescoredWerden) antragScoringEvent).hinweis());
        System.out.println(((AntragKonnteNichtGescoredWerden) antragScoringEvent).hinweis());
    }

    private AntragstellerID erzeugeAntragstellerID() {
        AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Plöd")
                .geburtsdatum(new Date())
                .postleitzahl("12345")
                .strasse("Musterstr. 23")
                .stadt("München")
                .build();
        return antragstellerID;
    }
}
