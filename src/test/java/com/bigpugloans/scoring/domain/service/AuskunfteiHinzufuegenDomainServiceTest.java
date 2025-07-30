package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.AuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AuskunfteiHinzufuegenDomainService.class)
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.show-sql=false"
})
class AuskunfteiHinzufuegenDomainServiceTest {
    
    @Autowired
    private AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository;
    @Autowired
    private AuskunfteiHinzufuegenDomainService auskunfteiHinzufuegenDomainService;
    
    private ScoringId testScoringId;
    private AuskunfteiErgebnis testAuskunfteiErgebnis;
    
    @BeforeEach
    void setUp() {
        testScoringId = ScoringId.mainScoringIdAusAntragsnummer("TEST123");
        testAuskunfteiErgebnis = new AuskunfteiErgebnis(3, 1, 85);
    }

    @Test
    void auskunfteiErgebnisHinzufuegen_shouldThrowException_whenClusterNotFound() {
        // No cluster exists in database for this scoring ID
        
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> auskunfteiHinzufuegenDomainService.auskunfteiErgebnisHinzufuegen(testScoringId, testAuskunfteiErgebnis)
        );
        
        assertTrue(exception.getMessage().contains("AuskunfteiErgebnisCluster für ScoringId"));
        assertTrue(exception.getMessage().contains("nicht gefunden"));
    }
    
    @Test
    void auskunfteiErgebnisHinzufuegen_shouldUpdateClusterWithCorrectValues() {
        // Create and save initial cluster
        AntragstellerID antragstellerID = new AntragstellerID("KUNDE123");
        AuskunfteiErgebnisCluster existingCluster = new AuskunfteiErgebnisCluster(testScoringId, antragstellerID);
        auskunfteiErgebnisClusterRepository.speichern(existingCluster);
        
        // Execute domain service operation
        auskunfteiHinzufuegenDomainService.auskunfteiErgebnisHinzufuegen(testScoringId, testAuskunfteiErgebnis);
        
        // Verify behavior: cluster should now be able to score (i.e., has complete data)
        AuskunfteiErgebnisCluster updatedCluster = auskunfteiErgebnisClusterRepository.lade(testScoringId);
        assertNotNull(updatedCluster, "Cluster should exist after update");
        
        Optional<ClusterGescored> scoringResult = updatedCluster.scoren();
        assertTrue(scoringResult.isPresent(), "Cluster should be scoreable after adding Auskunftei data");
        assertEquals(testScoringId, scoringResult.get().scoringId(), "Scoring result should have correct ScoringId");
    }
    
    @Test
    void auskunfteiErgebnisHinzufuegen_shouldHandleZeroValues() {
        // Create and save initial cluster
        AntragstellerID antragstellerID = new AntragstellerID("KUNDE123");
        AuskunfteiErgebnisCluster existingCluster = new AuskunfteiErgebnisCluster(testScoringId, antragstellerID);
        auskunfteiErgebnisClusterRepository.speichern(existingCluster);
        
        AuskunfteiErgebnis zeroValuesErgebnis = new AuskunfteiErgebnis(0, 0, 100);
        
        // Execute domain service operation
        auskunfteiHinzufuegenDomainService.auskunfteiErgebnisHinzufuegen(testScoringId, zeroValuesErgebnis);
        
        // Verify behavior: cluster should be updated and scoreable
        AuskunfteiErgebnisCluster updatedCluster = auskunfteiErgebnisClusterRepository.lade(testScoringId);
        assertNotNull(updatedCluster, "Cluster should exist after update");
        
        Optional<ClusterGescored> scoringResult = updatedCluster.scoren();
        assertTrue(scoringResult.isPresent(), "Cluster should be scoreable with zero values");
    }
    
    @Test
    void auskunfteiErgebnisHinzufuegen_shouldHandleMaximumValues() {
        // Create and save initial cluster
        AntragstellerID antragstellerID = new AntragstellerID("KUNDE123");
        AuskunfteiErgebnisCluster existingCluster = new AuskunfteiErgebnisCluster(testScoringId, antragstellerID);
        auskunfteiErgebnisClusterRepository.speichern(existingCluster);
        
        AuskunfteiErgebnis maxValuesErgebnis = new AuskunfteiErgebnis(10, 5, 0);
        
        // Execute domain service operation
        auskunfteiHinzufuegenDomainService.auskunfteiErgebnisHinzufuegen(testScoringId, maxValuesErgebnis);
        
        // Verify behavior: cluster should be updated and scoreable
        AuskunfteiErgebnisCluster updatedCluster = auskunfteiErgebnisClusterRepository.lade(testScoringId);
        assertNotNull(updatedCluster, "Cluster should exist after update");
        
        Optional<ClusterGescored> scoringResult = updatedCluster.scoren();
        assertTrue(scoringResult.isPresent(), "Cluster should be scoreable with maximum values");
        
        // With high warnings and negative features, should likely have KO criteria
        assertTrue(scoringResult.get().koKriterien().anzahl() > 0, "High risk values should result in KO criteria");
    }
}