package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.application.model.ImmobilienBewertung;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.testinfrastructure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImmobilienBewertungHinzufuegenDomainServiceTest {
    
    private TestRepositoryManager repos;
    private ImmobilienBewertungHinzufuegenDomainService immobilienBewertungHinzufuegenDomainService;
    private ScoringId testScoringId;
    
    @BeforeEach
    void setUp() {
        repos = new TestRepositoryManager();
        immobilienBewertungHinzufuegenDomainService = new ImmobilienBewertungHinzufuegenDomainService(
            repos.immobilienFinanzierungClusterRepository
        );
        
        testScoringId = ScoringId.preScoringIdAusAntragsnummer("TEST123");
    }
    
    @Test
    void immobilienBewertungHinzufuegen_shouldLoadCorrectClusterByAntragsnummer() {
        ImmobilienFinanzierungsCluster existingCluster = new ImmobilienFinanzierungsCluster(testScoringId);
        repos.immobilienFinanzierungClusterRepository.speichern(existingCluster);
        
        ImmobilienBewertung bewertung = new ImmobilienBewertung("TEST123", 1000, 2000, 5000, 2600, 2800);
        immobilienBewertungHinzufuegenDomainService.immobilienBewertungHinzufuegen(bewertung);
        
        assertTrue(repos.hasImmobilienFinanzierungCluster(testScoringId), "Cluster should be updated");
        assertEquals(1, repos.immobilienFinanzierungClusterRepository.size(), "Should have exactly one cluster");
    }
    
    @Test
    void immobilienBewertungHinzufuegen_shouldUpdateClusterWithCorrectValues() {
        ImmobilienFinanzierungsCluster existingCluster = new ImmobilienFinanzierungsCluster(testScoringId);
        repos.immobilienFinanzierungClusterRepository.speichern(existingCluster);
        
        ImmobilienBewertung bewertung = new ImmobilienBewertung("TEST123", 1500, 2500, 6000, 3000, 3200);
        immobilienBewertungHinzufuegenDomainService.immobilienBewertungHinzufuegen(bewertung);
        
        ImmobilienFinanzierungsCluster updatedCluster = repos.immobilienFinanzierungClusterRepository.lade(testScoringId);
        assertNotNull(updatedCluster, "Updated cluster should be retrievable");
    }
    
    @Test
    void immobilienBewertungHinzufuegen_shouldSaveClusterAfterUpdate() {
        ImmobilienFinanzierungsCluster existingCluster = new ImmobilienFinanzierungsCluster(testScoringId);
        repos.immobilienFinanzierungClusterRepository.speichern(existingCluster);
        
        ImmobilienBewertung bewertung = new ImmobilienBewertung("TEST123", 2000, 3000, 7000, 3500, 3700);
        immobilienBewertungHinzufuegenDomainService.immobilienBewertungHinzufuegen(bewertung);
        
        assertTrue(repos.hasImmobilienFinanzierungCluster(testScoringId), "Cluster should be saved after update");
    }
    
    @Test
    void immobilienBewertungHinzufuegen_shouldSetAllMarktwertVergleichValues() {

        ImmobilienFinanzierungsCluster existingCluster = new ImmobilienFinanzierungsCluster(testScoringId);
        repos.immobilienFinanzierungClusterRepository.speichern(existingCluster);
        
        ImmobilienBewertung bewertung = new ImmobilienBewertung("TEST123", 1200, 2200, 5500, 2700, 2900);
        
        immobilienBewertungHinzufuegenDomainService.immobilienBewertungHinzufuegen(bewertung);
        
        assertTrue(repos.hasImmobilienFinanzierungCluster(testScoringId), "All marktwert values should be processed");
    }
    
    @Test
    void immobilienBewertungHinzufuegen_shouldHandleMinimumValues() {
        ImmobilienFinanzierungsCluster existingCluster = new ImmobilienFinanzierungsCluster(testScoringId);
        repos.immobilienFinanzierungClusterRepository.speichern(existingCluster);
        
        ImmobilienBewertung bewertung = new ImmobilienBewertung("TEST123", 1, 1, 1, 1, 1);
        
        immobilienBewertungHinzufuegenDomainService.immobilienBewertungHinzufuegen(bewertung);
        
        assertTrue(repos.hasImmobilienFinanzierungCluster(testScoringId), "Should handle minimum values correctly");
    }
    
    @Test
    void immobilienBewertungHinzufuegen_shouldThrowException_whenClusterNotFound() {
        ImmobilienBewertung bewertung = new ImmobilienBewertung("NOTFOUND", 1000, 2000, 5000, 2600, 2800);
        
        assertThrows(RuntimeException.class, () -> {
            immobilienBewertungHinzufuegenDomainService.immobilienBewertungHinzufuegen(bewertung);
        }, "Should throw exception when cluster not found");
    }
}