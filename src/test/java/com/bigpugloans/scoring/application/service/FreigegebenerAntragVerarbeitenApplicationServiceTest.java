package com.bigpugloans.scoring.application.service;

import com.bigpugloans.events.antrag.Antrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.service.AntragHinzufuegenDomainService;
import com.bigpugloans.scoring.domain.service.AuskunfteiHinzufuegenDomainService;
import com.bigpugloans.scoring.testinfrastructure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FreigegebenerAntragVerarbeitenApplicationServiceTest {
    
    private TestRepositoryManager repos;
    private TestKreditAbfrageService kreditAbfrageService;
    private TestScoringErgebnisVeroeffentlichen scoringVeroeffentlichen;
    private FreigegebenerAntragVerarbeitenApplicationService service;
    
    @BeforeEach
    void setUp() {
        repos = new TestRepositoryManager();
        kreditAbfrageService = new TestKreditAbfrageService();
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
        
        service = new FreigegebenerAntragVerarbeitenApplicationService(
            antragHinzufuegenDomainService,
            auskunfteiHinzufuegenDomainService,
            kreditAbfrageService,
            scoringAusfuehrenUndVeroeffentlichenService
        );
    }
    
    @Test
    void testFreigegebenerAntragVerarbeiten() {

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
        
        AuskunfteiErgebnis auskunfteiErgebnis = new AuskunfteiErgebnis(1, 0, 85);
        kreditAbfrageService.willReturn(antrag, auskunfteiErgebnis);
        
        Antragsnummer antragsnummer = new Antragsnummer(antrag.antragsnummer());
        ScoringId expectedScoringId = new ScoringId(antragsnummer, ScoringArt.MAIN);
        
        service.freigegebenerAntragVerarbeiten(antrag);
        
        assertTrue(repos.hasAntragstellerCluster(expectedScoringId), "AntragstellerCluster should be created");
        assertTrue(repos.hasMonatlicheFinanzsituationCluster(expectedScoringId), "MonatlicheFinanzsituationCluster should be created");
        assertTrue(repos.hasImmobilienFinanzierungCluster(expectedScoringId), "ImmobilienFinanzierungCluster should be created");
        assertTrue(repos.hasAuskunfteiErgebnisCluster(expectedScoringId), "AuskunfteiErgebnisCluster should be created");
    }
}