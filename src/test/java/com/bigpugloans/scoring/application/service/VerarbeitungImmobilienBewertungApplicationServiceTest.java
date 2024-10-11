package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.ImmobilienBewertung;
import com.bigpugloans.scoring.application.ports.driven.ImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.application.ports.driven.MonatlicheFinanzsituationClusterRepository;
import com.bigpugloans.scoring.application.ports.driven.ScoringErgebnisRepository;
import com.bigpugloans.scoring.application.ports.driven.ScoringErgebnisVeroeffentlichen;
import com.bigpugloans.scoring.domainmodel.*;
import com.bigpugloans.scoring.domainmodel.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.domainmodel.scoringErgebnis.ScoringErgebnis;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VerarbeitungImmobilienBewertungApplicationServiceTest {
    @Test
    void testVerarbeiteImmobilienBewertung() {
        ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepositoryMock = mock();
        when(immobilienFinanzierungClusterRepositoryMock.lade(any()))
                .thenReturn(new ImmobilienFinanzierungsCluster(new Antragsnummer("123")));
        ScoringErgebnisRepository scoringErgebnisRepositoryMock = mock();
        ScoringErgebnisVeroeffentlichen scoringErgebnisVeroeffentlichenMock = mock();
        doAnswer(invocation -> {
            AntragErfolgreichGescored arg = invocation.getArgument(0);
            System.out.println(arg);
            return null;
        }).when(scoringErgebnisVeroeffentlichenMock).preScoringErgebnisVeroeffentlichen(any(AntragErfolgreichGescored.class));

        VerarbeitungImmobilienBewertungApplicationService verarbeitungImmobilienBewertungApplicationService = new VerarbeitungImmobilienBewertungApplicationService(immobilienFinanzierungClusterRepositoryMock, scoringErgebnisRepositoryMock, scoringErgebnisVeroeffentlichenMock);
        ImmobilienBewertung immobilienBewertung = new ImmobilienBewertung("123", 1000, 2000, 5000, 2600, 2800);
        verarbeitungImmobilienBewertungApplicationService.verarbeiteImmobilienBewertung(immobilienBewertung);
    }

    @Test
    void testVerarbeiteImmobilienBewertungMitFertigemScoring() {
        ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepositoryMock = mock();
        doAnswer(invocation -> {
            Antragsnummer antragsnummer = invocation.getArgument(0);
            System.out.println("Lade ImmobilienFinanzierungsCluster für Antragsnummer " + antragsnummer);
            ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster(antragsnummer);
            immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(10000));
            immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(100000));
            immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(5000));
            immobilienFinanzierungsCluster.marktwertHinzufuegen(new Waehrungsbetrag(200000));
            return immobilienFinanzierungsCluster;
        }).when(immobilienFinanzierungClusterRepositoryMock).lade(any());


        ScoringErgebnisRepository scoringErgebnisRepositoryMock = mock();
        doAnswer(invocationOnMock -> {
            Antragsnummer antragsnummer = invocationOnMock.getArgument(0);
            System.out.println("Lade ScoringErgebnis für Antragsnummer " + antragsnummer);
            ScoringErgebnis scoringErgebnis = new ScoringErgebnis(antragsnummer);
            scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(98), new KoKriterien(0)));
            scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(10), new KoKriterien(0)));
            scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(antragsnummer, new Punkte(10), new KoKriterien(0)));
            return scoringErgebnis;
        }).when(scoringErgebnisRepositoryMock).lade(any());

        ScoringErgebnisVeroeffentlichen scoringErgebnisVeroeffentlichenMock = mock();
        doAnswer(invocation -> {
            AntragErfolgreichGescored arg = invocation.getArgument(0);
            System.out.println(arg);
            return null;
        }).when(scoringErgebnisVeroeffentlichenMock).preScoringErgebnisVeroeffentlichen(any(AntragErfolgreichGescored.class));

        VerarbeitungImmobilienBewertungApplicationService verarbeitungImmobilienBewertungApplicationService = new VerarbeitungImmobilienBewertungApplicationService(immobilienFinanzierungClusterRepositoryMock, scoringErgebnisRepositoryMock, scoringErgebnisVeroeffentlichenMock);
        ImmobilienBewertung immobilienBewertung = new ImmobilienBewertung("123", 1000, 2000, 5000, 2600, 2800);
        verarbeitungImmobilienBewertungApplicationService.verarbeiteImmobilienBewertung(immobilienBewertung);
    }
}
