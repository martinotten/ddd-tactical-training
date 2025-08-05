package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.application.model.Antrag;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import com.bigpugloans.scoring.testinfrastructure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AntragHinzufuegenDomainServiceTest {
    
    private TestRepositoryManager repos;
    private AntragHinzufuegenDomainService antragHinzufuegenDomainService;
    private ScoringId testScoringId;
    private Antrag testAntrag;
    
    @BeforeEach
    void setUp() {
        repos = new TestRepositoryManager();
        
        antragHinzufuegenDomainService = new AntragHinzufuegenDomainService(
            repos.antragstellerClusterRepository,
            repos.monatlicheFinanzsituationClusterRepository,
            repos.immobilienFinanzierungClusterRepository,
            repos.auskunfteiErgebnisClusterRepository
        );

        Antragsnummer testAntragsnummer = new Antragsnummer("TEST123");
        testScoringId = ScoringId.mainScoringIdAusAntragsnummer(testAntragsnummer.nummer());
        testAntrag = new Antrag(
            "TEST123",
            "KUNDE123",
            1000,
            500,
            100,
            "Berlin",
            500000,
            450000,
            10000,
            40000,
            "Max",
            "Mustermann",
            "Musterstraße 1",
            "Berlin",
            "12345",
            LocalDate.of(1970, 2, 1)
        );
    }
    
    @Test
    void antragHinzufuegen_shouldCreateAndSaveAllClusters_whenRepositoriesAreEmpty() {
        antragHinzufuegenDomainService.antragHinzufuegen(testScoringId, testAntrag);
        
        assertTrue(repos.hasAntragstellerCluster(testScoringId), "AntragstellerCluster should be created");
        assertTrue(repos.hasMonatlicheFinanzsituationCluster(testScoringId), "MonatlicheFinanzsituationCluster should be created");
        assertTrue(repos.hasImmobilienFinanzierungCluster(testScoringId), "ImmobilienFinanzierungsCluster should be created");
        assertTrue(repos.hasAuskunfteiErgebnisCluster(testScoringId), "AuskunfteiErgebnisCluster should be created");
    }
    
    @Test
    void antragHinzufuegen_shouldUpdateExistingClusters_whenRepositoriesReturnClusters() {
        AntragstellerCluster existingAntragstellerCluster = new AntragstellerCluster(testScoringId);
        MonatlicheFinanzsituationCluster existingMonatlicheCluster = new MonatlicheFinanzsituationCluster(testScoringId);
        ImmobilienFinanzierungsCluster existingImmobilienCluster = new ImmobilienFinanzierungsCluster(testScoringId);
        AuskunfteiErgebnisCluster existingAuskunfteiCluster = new AuskunfteiErgebnisCluster(
            testScoringId, new AntragstellerID("KUNDE123"));
        
        repos.antragstellerClusterRepository.speichern(existingAntragstellerCluster);
        repos.monatlicheFinanzsituationClusterRepository.speichern(existingMonatlicheCluster);
        repos.immobilienFinanzierungClusterRepository.speichern(existingImmobilienCluster);
        repos.auskunfteiErgebnisClusterRepository.speichern(existingAuskunfteiCluster);
        
        antragHinzufuegenDomainService.antragHinzufuegen(testScoringId, testAntrag);
        
        assertTrue(repos.hasAntragstellerCluster(testScoringId), "AntragstellerCluster should still exist");
        assertTrue(repos.hasMonatlicheFinanzsituationCluster(testScoringId), "MonatlicheFinanzsituationCluster should still exist");
        assertTrue(repos.hasImmobilienFinanzierungCluster(testScoringId), "ImmobilienFinanzierungsCluster should still exist");
        assertTrue(repos.hasAuskunfteiErgebnisCluster(testScoringId), "AuskunfteiErgebnisCluster should still exist");
    }
    
    @Test
    void antragHinzufuegen_shouldCreateNewClustersForMissingOnes() {
        AntragstellerCluster existingAntragstellerCluster = new AntragstellerCluster(testScoringId);
        ImmobilienFinanzierungsCluster existingImmobilienCluster = new ImmobilienFinanzierungsCluster(testScoringId);
        
        repos.antragstellerClusterRepository.speichern(existingAntragstellerCluster);
        repos.immobilienFinanzierungClusterRepository.speichern(existingImmobilienCluster);
        
        antragHinzufuegenDomainService.antragHinzufuegen(testScoringId, testAntrag);
        
        assertTrue(repos.hasAntragstellerCluster(testScoringId), "AntragstellerCluster should exist");
        assertTrue(repos.hasMonatlicheFinanzsituationCluster(testScoringId), "MonatlicheFinanzsituationCluster should be created");
        assertTrue(repos.hasImmobilienFinanzierungCluster(testScoringId), "ImmobilienFinanzierungsCluster should exist");
        assertTrue(repos.hasAuskunfteiErgebnisCluster(testScoringId), "AuskunfteiErgebnisCluster should be created");
    }
    
    @Test
    void antragHinzufuegen_shouldHandleAllRepositoryOperations() {
        antragHinzufuegenDomainService.antragHinzufuegen(testScoringId, testAntrag);
        
        assertEquals(1, repos.antragstellerClusterRepository.size(), "Should have exactly one AntragstellerCluster");
        assertEquals(1, repos.monatlicheFinanzsituationClusterRepository.size(), "Should have exactly one MonatlicheFinanzsituationCluster");
        assertEquals(1, repos.immobilienFinanzierungClusterRepository.size(), "Should have exactly one ImmobilienFinanzierungsCluster");
        assertEquals(1, repos.auskunfteiErgebnisClusterRepository.size(), "Should have exactly one AuskunfteiErgebnisCluster");
        
        assertDoesNotThrow(() -> repos.antragstellerClusterRepository.lade(testScoringId));
        assertDoesNotThrow(() -> repos.monatlicheFinanzsituationClusterRepository.lade(testScoringId));
        assertDoesNotThrow(() -> repos.immobilienFinanzierungClusterRepository.lade(testScoringId));
        assertDoesNotThrow(() -> repos.auskunfteiErgebnisClusterRepository.lade(testScoringId));
    }
}