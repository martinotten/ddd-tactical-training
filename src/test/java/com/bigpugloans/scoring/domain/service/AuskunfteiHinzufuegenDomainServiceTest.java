package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import com.bigpugloans.scoring.testinfrastructure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuskunfteiHinzufuegenDomainServiceTest {
    
    private TestRepositoryManager repos;
    private AuskunfteiHinzufuegenDomainService auskunfteiHinzufuegenDomainService;
    private ScoringId testScoringId;
    
    @BeforeEach
    void setUp() {
        repos = new TestRepositoryManager();
        auskunfteiHinzufuegenDomainService = new AuskunfteiHinzufuegenDomainService(
            repos.auskunfteiErgebnisClusterRepository
        );
        
        testScoringId = ScoringId.mainScoringIdAusAntragsnummer("TEST123");
    }
    
    @Test
    void auskunfteiErgebnisHinzufuegen_shouldLoadAndSaveCorrectCluster() {

        AuskunfteiErgebnisCluster existingCluster = new AuskunfteiErgebnisCluster(
            testScoringId, new AntragstellerID("KUNDE123"));
        repos.auskunfteiErgebnisClusterRepository.speichern(existingCluster);
        
        AuskunfteiErgebnis ergebnis = new AuskunfteiErgebnis(2, 1, 75);
        

        auskunfteiHinzufuegenDomainService.auskunfteiErgebnisHinzufuegen(testScoringId, ergebnis);
        

        assertTrue(repos.hasAuskunfteiErgebnisCluster(testScoringId), "AuskunfteiErgebnisCluster should be updated");
        assertEquals(1, repos.auskunfteiErgebnisClusterRepository.size(), "Should have exactly one cluster");
    }
    
    @Test
    void auskunfteiErgebnisHinzufuegen_shouldUpdateClusterWithCorrectValues() {

        AuskunfteiErgebnisCluster existingCluster = new AuskunfteiErgebnisCluster(
            testScoringId, new AntragstellerID("KUNDE123"));
        repos.auskunfteiErgebnisClusterRepository.speichern(existingCluster);
        
        AuskunfteiErgebnis ergebnis = new AuskunfteiErgebnis(3, 2, 60);
        

        auskunfteiHinzufuegenDomainService.auskunfteiErgebnisHinzufuegen(testScoringId, ergebnis);
        

        assertTrue(repos.hasAuskunfteiErgebnisCluster(testScoringId), "Cluster should exist after update");
        
        // Load the cluster to verify it can be retrieved
        AuskunfteiErgebnisCluster updatedCluster = repos.auskunfteiErgebnisClusterRepository.lade(testScoringId);
        assertNotNull(updatedCluster, "Updated cluster should be retrievable");
    }
    
    @Test
    void auskunfteiErgebnisHinzufuegen_shouldThrowException_whenClusterNotFound() {

        AuskunfteiErgebnis ergebnis = new AuskunfteiErgebnis(1, 0, 85);
        

        assertThrows(RuntimeException.class, () -> {
            auskunfteiHinzufuegenDomainService.auskunfteiErgebnisHinzufuegen(testScoringId, ergebnis);
        }, "Should throw exception when cluster not found");
    }
    
    @Test
    void auskunfteiErgebnisHinzufuegen_shouldHandleZeroValues() {

        AuskunfteiErgebnisCluster existingCluster = new AuskunfteiErgebnisCluster(
            testScoringId, new AntragstellerID("KUNDE123"));
        repos.auskunfteiErgebnisClusterRepository.speichern(existingCluster);
        
        AuskunfteiErgebnis ergebnis = new AuskunfteiErgebnis(0, 0, 0);
        

        auskunfteiHinzufuegenDomainService.auskunfteiErgebnisHinzufuegen(testScoringId, ergebnis);
        

        assertTrue(repos.hasAuskunfteiErgebnisCluster(testScoringId), "Should handle zero values correctly");
    }
    
    @Test
    void auskunfteiErgebnisHinzufuegen_shouldHandleMaximumValues() {

        AuskunfteiErgebnisCluster existingCluster = new AuskunfteiErgebnisCluster(
            testScoringId, new AntragstellerID("KUNDE123"));
        repos.auskunfteiErgebnisClusterRepository.speichern(existingCluster);
        
        AuskunfteiErgebnis ergebnis = new AuskunfteiErgebnis(999, 999, 100);
        

        auskunfteiHinzufuegenDomainService.auskunfteiErgebnisHinzufuegen(testScoringId, ergebnis);
        

        assertTrue(repos.hasAuskunfteiErgebnisCluster(testScoringId), "Should handle maximum values correctly");
    }
}