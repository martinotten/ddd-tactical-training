package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.ImmobilienBewertung;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.domain.service.ImmobilienBewertungHinzufuegenDomainService;
import com.bigpugloans.scoring.testinfrastructure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VerarbeitungImmobilienBewertungApplicationServiceTest {
    
    private TestRepositoryManager repos;
    private TestScoringErgebnisVeroeffentlichen scoringVeroeffentlichen;
    private VerarbeitungImmobilienBewertungApplicationService service;
    
    @BeforeEach
    void setUp() {
        repos = new TestRepositoryManager();
        scoringVeroeffentlichen = new TestScoringErgebnisVeroeffentlichen();
        
        // Create domain services with test repositories
        ImmobilienBewertungHinzufuegenDomainService immobilienBewertungHinzufuegenDomainService = 
            new ImmobilienBewertungHinzufuegenDomainService(repos.immobilienFinanzierungClusterRepository);
        
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
        
        service = new VerarbeitungImmobilienBewertungApplicationService(
            immobilienBewertungHinzufuegenDomainService,
            scoringAusfuehrenUndVeroeffentlichenService
        );
    }

    @Test
    void testVerarbeiteImmobilienBewertung() {
        ImmobilienBewertung immobilienBewertung = new ImmobilienBewertung("123", 1000, 2000, 5000, 2600, 2800);
        ScoringId expectedScoringId = ScoringId.preScoringIdAusAntragsnummer(immobilienBewertung.antragsnummer());
        
        repos.immobilienFinanzierungClusterRepository.speichern(
            new ImmobilienFinanzierungsCluster(expectedScoringId)
        );
        
        service.verarbeiteImmobilienBewertung(immobilienBewertung);
        
        assertTrue(repos.hasImmobilienFinanzierungCluster(expectedScoringId), 
            "ImmobilienFinanzierungsCluster should be updated with new bewertung data");
    }

    @Test
    void testVerarbeiteImmobilienBewertungMitFertigemScoring() {
        ImmobilienBewertung immobilienBewertung = new ImmobilienBewertung("456", 1500, 2500, 6000, 3000, 3200);
        ScoringId expectedScoringId = ScoringId.preScoringIdAusAntragsnummer(immobilienBewertung.antragsnummer());
        
        repos.immobilienFinanzierungClusterRepository.speichern(
            new com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster(expectedScoringId)
        );
        
        service.verarbeiteImmobilienBewertung(immobilienBewertung);
        
        assertTrue(repos.hasImmobilienFinanzierungCluster(expectedScoringId), 
            "ImmobilienFinanzierungsCluster should be updated");
    }
}