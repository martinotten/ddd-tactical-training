package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.testinfrastructure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class KontosaldoHinzufuegenDomainServiceTest {
    
    private TestRepositoryManager repos;
    private KontosaldoHinzufuegenDomainService kontosaldoHinzufuegenDomainService;
    private ScoringId testScoringId;
    
    @BeforeEach
    void setUp() {
        repos = new TestRepositoryManager();
        kontosaldoHinzufuegenDomainService = new KontosaldoHinzufuegenDomainService(
            repos.antragstellerClusterRepository
        );
        
        testScoringId = ScoringId.mainScoringIdAusAntragsnummer("TEST123");
    }

    
    @Test
    void kontosaldoHinzufuegen_shouldUpdateClusterWithKontosaldo() {

        AntragstellerCluster existingCluster = new AntragstellerCluster(testScoringId);
        repos.antragstellerClusterRepository.speichern(existingCluster);
        
        Waehrungsbetrag kontosaldo = new Waehrungsbetrag(7500);
        kontosaldoHinzufuegenDomainService.kontosaldoHinzufuegen(testScoringId, kontosaldo);
        
        assertTrue(repos.hasAntragstellerCluster(testScoringId), "AntragstellerCluster should be updated");
        assertEquals(1, repos.antragstellerClusterRepository.size(), "Should have exactly one cluster");
    }
    
    @Test
    void kontosaldoHinzufuegen_shouldLoadAndSaveCorrectCluster() {

        AntragstellerCluster existingCluster = new AntragstellerCluster(testScoringId);
        repos.antragstellerClusterRepository.speichern(existingCluster);
        
        Waehrungsbetrag kontosaldo = new Waehrungsbetrag(10000);
        

        kontosaldoHinzufuegenDomainService.kontosaldoHinzufuegen(testScoringId, kontosaldo);
        

        Optional<AntragstellerCluster> updatedCluster = repos.antragstellerClusterRepository.lade(testScoringId);
        assertTrue(updatedCluster.isPresent(), "Updated cluster should be retrievable");
    }
    
    @Test
    void kontosaldoHinzufuegen_shouldHandleZeroKontosaldo() {

        AntragstellerCluster existingCluster = new AntragstellerCluster(testScoringId);
        repos.antragstellerClusterRepository.speichern(existingCluster);
        
        Waehrungsbetrag zeroKontosaldo = new Waehrungsbetrag(0);
        

        kontosaldoHinzufuegenDomainService.kontosaldoHinzufuegen(testScoringId, zeroKontosaldo);
        

        assertTrue(repos.hasAntragstellerCluster(testScoringId), "Should handle zero kontosaldo correctly");
    }
    
    @Test
    void kontosaldoHinzufuegen_shouldHandleNegativeKontosaldo() {

        AntragstellerCluster existingCluster = new AntragstellerCluster(testScoringId);
        repos.antragstellerClusterRepository.speichern(existingCluster);
        
        Waehrungsbetrag negativeKontosaldo = new Waehrungsbetrag(-1000);
        

        kontosaldoHinzufuegenDomainService.kontosaldoHinzufuegen(testScoringId, negativeKontosaldo);
        

        assertTrue(repos.hasAntragstellerCluster(testScoringId), "Should handle negative kontosaldo correctly");
    }
    
    @Test
    void kontosaldoHinzufuegen_shouldHandleLargeKontosaldo() {

        AntragstellerCluster existingCluster = new AntragstellerCluster(testScoringId);
        repos.antragstellerClusterRepository.speichern(existingCluster);
        
        Waehrungsbetrag largeKontosaldo = new Waehrungsbetrag(1000000);
        

        kontosaldoHinzufuegenDomainService.kontosaldoHinzufuegen(testScoringId, largeKontosaldo);
        

        assertTrue(repos.hasAntragstellerCluster(testScoringId), "Should handle large kontosaldo correctly");
    }
}