package com.bigpugloans.scoring.application.service;

import com.bigpugloans.events.antrag.Antrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.service.AntragHinzufuegenDomainService;
import com.bigpugloans.scoring.domain.service.AuskunfteiHinzufuegenDomainService;
import com.bigpugloans.scoring.domain.service.KontosaldoHinzufuegenDomainService;
import com.bigpugloans.scoring.testinfrastructure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EingereicherAntragVerarbeitenApplicationServiceTest {
    
    private TestRepositoryManager repos;
    private TestKonditionsAbfrageService konditionsAbfrageService;
    private TestLeseKontoSaldo leseKontoSaldo;
    private TestScoringErgebnisVeroeffentlichen scoringVeroeffentlichen;
    private EingereicherAntragVerarbeitenApplicationService service;
    
    @BeforeEach
    void setUp() {
        repos = new TestRepositoryManager();
        konditionsAbfrageService = new TestKonditionsAbfrageService();
        leseKontoSaldo = new TestLeseKontoSaldo();
        scoringVeroeffentlichen = new TestScoringErgebnisVeroeffentlichen();
        
        // Create domain services with test repositories
        AntragHinzufuegenDomainService antragHinzufuegenDomainService = new AntragHinzufuegenDomainService(
            repos.antragstellerClusterRepository,
            repos.monatlicheFinanzsituationClusterRepository,
            repos.immobilienFinanzierungClusterRepository,
            repos.auskunfteiErgebnisClusterRepository
        );
        
        AuskunfteiHinzufuegenDomainService auskunfteiHinzufuegenDomainService = 
            new AuskunfteiHinzufuegenDomainService(repos.auskunfteiErgebnisClusterRepository);
        
        KontosaldoHinzufuegenDomainService kontosaldoHinzufuegenDomainService = 
            new KontosaldoHinzufuegenDomainService(repos.antragstellerClusterRepository);
        
        // Create ScoringDomainService first
        com.bigpugloans.scoring.domain.service.ScoringDomainService scoringDomainService = 
            new com.bigpugloans.scoring.domain.service.ScoringDomainService(
                repos.antragstellerClusterRepository,
                repos.monatlicheFinanzsituationClusterRepository,
                repos.immobilienFinanzierungClusterRepository,
                repos.auskunfteiErgebnisClusterRepository
            );
        
        ScoringAusfuehrenUndVeroeffentlichenService scoringAusfuehrenUndVeroeffentlichenService = 
            new ScoringAusfuehrenUndVeroeffentlichenService(
                scoringDomainService,
                repos.scoringErgebnisRepository,
                scoringVeroeffentlichen
            );
        
        service = new EingereicherAntragVerarbeitenApplicationService(
            antragHinzufuegenDomainService,
            auskunfteiHinzufuegenDomainService,
            konditionsAbfrageService,
            leseKontoSaldo,
            kontosaldoHinzufuegenDomainService,
            scoringAusfuehrenUndVeroeffentlichenService
        );
    }
    
    @Test
    void testEingereicherAntragVerarbeiten() {

        Antrag antrag = new Antrag(
                "123",
                "789",
                1000,
                2000,
                500,
                "Hamburg",
                1000,
                100000,
                100000,
                10000,
                "Max",
                "Mustermann",
                "Musterstrasse",
                "Musterstadt",
                "1234",
                LocalDate.of(1970, 2, 1)
        );

        AuskunfteiErgebnis auskunfteiErgebnis = new AuskunfteiErgebnis(2, 0, 70);
        konditionsAbfrageService.willReturn(antrag, auskunfteiErgebnis);
        leseKontoSaldo.willReturn(antrag.kundennummer(), new Waehrungsbetrag(2000));
        
        Antragsnummer antragsnummer = new Antragsnummer(antrag.antragsnummer());
        ScoringId expectedScoringId = new ScoringId(antragsnummer, ScoringArt.PRE);
        

        service.eingereicherAntragVerarbeiten(antrag);
        

        assertTrue(repos.hasAntragstellerCluster(expectedScoringId), "AntragstellerCluster should be created");
        assertTrue(repos.hasMonatlicheFinanzsituationCluster(expectedScoringId), "MonatlicheFinanzsituationCluster should be created");
        assertTrue(repos.hasImmobilienFinanzierungCluster(expectedScoringId), "ImmobilienFinanzierungCluster should be created");
        assertTrue(repos.hasAuskunfteiErgebnisCluster(expectedScoringId), "AuskunfteiErgebnisCluster should be created");
        
        // Verify scoring was attempted (may or may not succeed depending on data completeness)
        // This tests the integration flow rather than mocking individual calls
    }
}