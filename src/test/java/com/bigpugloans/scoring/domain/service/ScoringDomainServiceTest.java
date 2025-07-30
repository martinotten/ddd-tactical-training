package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.application.ports.driven.*;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import com.bigpugloans.scoring.adapter.driven.antragstellerCluster.AntragstellerClusterSpringDataRepository;
import com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterSpringDataRepository;
import com.bigpugloans.scoring.adapter.driven.immobilienFinanzierungsCluster.ImmobilienFinanzierungsClusterSpringDataRepository;
import com.bigpugloans.scoring.adapter.driven.auskunfteiErgebnisCluster.AuskunfteiErgebnisClusterSpringDataRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ScoringDomainService.class)
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.show-sql=false"
})
class ScoringDomainServiceTest {
    
    @Autowired
    private AntragstellerClusterRepository antragstellerClusterRepository;
    @Autowired
    private MonatlicheFinanzsituationClusterRepository monatlicheFinanzsituationClusterRepository;
    @Autowired
    private ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository;
    @Autowired
    private AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository;
    
    // Spring Data repositories for cleanup
    @Autowired
    private AntragstellerClusterSpringDataRepository antragstellerClusterSpringDataRepository;
    @Autowired
    private MonatlicheFinanzsituationClusterSpringDataRepository monatlicheFinanzsituationClusterSpringDataRepository;
    @Autowired
    private ImmobilienFinanzierungsClusterSpringDataRepository immobilienFinanzierungsClusterSpringDataRepository;
    @Autowired
    private AuskunfteiErgebnisClusterSpringDataRepository auskunfteiErgebnisClusterSpringDataRepository;
    
    @Autowired
    private ScoringDomainService scoringDomainService;
    
    private ScoringId testScoringId;

    @BeforeEach
    void setUp() {
        Antragsnummer testAntragsnummer = new Antragsnummer("TEST123");
        testScoringId = ScoringId.mainScoringIdAusAntragsnummer(testAntragsnummer.nummer());
        
        // Clean database before each test
        cleanDatabase();
    }
    
    private void cleanDatabase() {
        antragstellerClusterSpringDataRepository.deleteAll();
        monatlicheFinanzsituationClusterSpringDataRepository.deleteAll();
        immobilienFinanzierungsClusterSpringDataRepository.deleteAll();
        auskunfteiErgebnisClusterSpringDataRepository.deleteAll();
    }
    
    private void createCompleteTestClusters(ScoringId scoringId) {
        // Create Antragsteller cluster with test data
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(scoringId);
        antragstellerCluster.wohnortHinzufuegen("München");
        antragstellerCluster.guthabenHinzufuegen(new Waehrungsbetrag(5000));
        antragstellerClusterRepository.speichern(antragstellerCluster);
        
        // Create MonatlicheFinanzsituation cluster with test data
        MonatlicheFinanzsituationCluster monatlicheCluster = new MonatlicheFinanzsituationCluster(scoringId);
        monatlicheCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(4000));
        monatlicheCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(2500));
        monatlicheCluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(800));
        monatlicheFinanzsituationClusterRepository.speichern(monatlicheCluster);
        
        // Create ImmobilienFinanzierung cluster with test data
        ImmobilienFinanzierungsCluster immobilienCluster = new ImmobilienFinanzierungsCluster(scoringId);
        immobilienCluster.beleihungswertHinzufuegen(new Waehrungsbetrag(400000));
        immobilienCluster.marktwertHinzufuegen(new Waehrungsbetrag(500000));
        immobilienCluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(50000));
        immobilienCluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(350000));
        immobilienCluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(200000));
        immobilienCluster.marktwertVerlgeichHinzufuegen(
            new Waehrungsbetrag(450000),
            new Waehrungsbetrag(520000),
            new Waehrungsbetrag(485000),
            new Waehrungsbetrag(515000)
        );
        immobilienFinanzierungClusterRepository.speichern(immobilienCluster);
        
        // Create AuskunfteiErgebnis cluster with test data
        AntragstellerID antragstellerID = new AntragstellerID("KUNDE123");
        AuskunfteiErgebnisCluster auskunfteiCluster = new AuskunfteiErgebnisCluster(scoringId, antragstellerID);
        auskunfteiCluster.warnungenHinzufuegen(1);
        auskunfteiCluster.negativMerkmaleHinzufuegen(2);
        auskunfteiCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(85));
        auskunfteiErgebnisClusterRepository.speichern(auskunfteiCluster);
    }
    
    @Test
    void scoring_shouldReturnEmptyOptional_whenNoClustersExist() {
        // No clusters created in database - should return empty
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isEmpty(), "Scoring should return empty when no clusters exist");
    }
    
    @Test
    void scoring_shouldReturnEmptyOptional_whenAnyClusterScoringReturnsEmpty() {
        // Create incomplete test data - missing wohnort in AntragstellerCluster causes scoren() to return empty
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(testScoringId);
        // Note: not adding wohnort, so scoren() will return empty
        antragstellerClusterRepository.speichern(antragstellerCluster);
        
        // Create complete other clusters
        MonatlicheFinanzsituationCluster monatlicheCluster = new MonatlicheFinanzsituationCluster(testScoringId);
        monatlicheCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(4000));
        monatlicheCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(2500));
        monatlicheCluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(800));
        monatlicheFinanzsituationClusterRepository.speichern(monatlicheCluster);
        
        ImmobilienFinanzierungsCluster immobilienCluster = new ImmobilienFinanzierungsCluster(testScoringId);
        immobilienCluster.beleihungswertHinzufuegen(new Waehrungsbetrag(400000));
        immobilienCluster.marktwertHinzufuegen(new Waehrungsbetrag(500000));
        immobilienCluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(50000));
        immobilienCluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(350000));
        immobilienCluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(200000));
        immobilienCluster.marktwertVerlgeichHinzufuegen(
            new Waehrungsbetrag(450000),
            new Waehrungsbetrag(520000),
            new Waehrungsbetrag(485000),
            new Waehrungsbetrag(515000)
        );
        immobilienFinanzierungClusterRepository.speichern(immobilienCluster);
        
        AntragstellerID antragstellerID = new AntragstellerID("KUNDE123");
        AuskunfteiErgebnisCluster auskunfteiCluster = new AuskunfteiErgebnisCluster(testScoringId, antragstellerID);
        auskunfteiCluster.warnungenHinzufuegen(1);
        auskunfteiCluster.negativMerkmaleHinzufuegen(2);
        auskunfteiCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(85));
        auskunfteiErgebnisClusterRepository.speichern(auskunfteiCluster);
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isEmpty(), "Scoring should return empty when any cluster cannot be scored");
    }
    
    @Test
    void scoring_shouldReturnScoringErgebnis_whenAllClustersScoreSuccessfully() {
        // Create complete test clusters with all required data
        createCompleteTestClusters(testScoringId);
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isPresent(), "Scoring should return result when all clusters are complete");
        ScoringErgebnis scoringErgebnis = result.get();
        assertNotNull(scoringErgebnis, "ScoringErgebnis should not be null");
        assertEquals(testScoringId, scoringErgebnis.scoringId(), "ScoringId should match");
        
        // Verify that we get a meaningful scoring result - test the actual business outcome
        Optional<AntragErfolgreichGescored> antragErgebnis = scoringErgebnis.berechneErgebnis();
        assertTrue(antragErgebnis.isPresent(), "Should get a final scoring result");
        assertNotNull(antragErgebnis.get().farbe(), "Should have a color classification");
    }
    
    @Test
    void scoring_shouldReturnEmptyOptional_whenOnlyPartialClustersExist() {
        // Create only some clusters - missing others should result in empty result
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(testScoringId);
        antragstellerCluster.wohnortHinzufuegen("München");
        antragstellerCluster.guthabenHinzufuegen(new Waehrungsbetrag(5000));
        antragstellerClusterRepository.speichern(antragstellerCluster);
        
        MonatlicheFinanzsituationCluster monatlicheCluster = new MonatlicheFinanzsituationCluster(testScoringId);
        monatlicheCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(4000));
        monatlicheCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(2500));
        monatlicheCluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(800));
        monatlicheFinanzsituationClusterRepository.speichern(monatlicheCluster);
        
        // Missing ImmobilienFinanzierung and AuskunfteiErgebnis clusters
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isEmpty(), "Scoring should return empty when not all clusters exist");
    }
    
    @Test
    void scoring_shouldHandleAllClusterTypes_whenScoring() {
        // Create test data with different scoring characteristics for each cluster type
        createCompleteTestClusters(testScoringId);
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isPresent(), "Should successfully score when all cluster types are present");
        ScoringErgebnis scoringErgebnis = result.get();
        
        // Verify that the result incorporates data from all cluster types
        assertNotNull(scoringErgebnis, "ScoringErgebnis should not be null");
        assertEquals(testScoringId, scoringErgebnis.scoringId(), "ScoringId should match");
        
        // The result should incorporate data from all clusters - test by checking final result
        Optional<AntragErfolgreichGescored> antragErgebnis = scoringErgebnis.berechneErgebnis();
        assertTrue(antragErgebnis.isPresent(), "Should calculate final result when all clusters are present");
    }
    
    @Test
    void scoring_shouldProduceConsistentResults_whenCalledMultipleTimes() {
        // Create test data
        createCompleteTestClusters(testScoringId);
        
        // Call scoring multiple times
        Optional<ScoringErgebnis> result1 = scoringDomainService.scoring(testScoringId);
        Optional<ScoringErgebnis> result2 = scoringDomainService.scoring(testScoringId);
        
        // Results should be consistent
        assertTrue(result1.isPresent(), "First result should be present");
        assertTrue(result2.isPresent(), "Second result should be present");
        
        // Test behavior consistency - both results should have same final outcome
        Optional<AntragErfolgreichGescored> antragErgebnis1 = result1.get().berechneErgebnis();
        Optional<AntragErfolgreichGescored> antragErgebnis2 = result2.get().berechneErgebnis();
        
        assertEquals(antragErgebnis1.isPresent(), antragErgebnis2.isPresent(), 
                    "Result presence should be consistent across calls");
        if (antragErgebnis1.isPresent() && antragErgebnis2.isPresent()) {
            assertEquals(antragErgebnis1.get().farbe(), antragErgebnis2.get().farbe(), 
                        "Final scoring color should be consistent across calls");
        }
    }
    
    @Test
    void scoring_shouldUseDifferentScoringIds_independently() {
        // Create test data for first scoring ID
        createCompleteTestClusters(testScoringId);
        
        // Create different scoring ID with different test data
        ScoringId differentScoringId = ScoringId.mainScoringIdAusAntragsnummer("DIFFERENT456");
        
        // Create incomplete clusters for different ID (should result in empty)
        AntragstellerCluster incompleteCluster = new AntragstellerCluster(differentScoringId);
        // Missing required data
        antragstellerClusterRepository.speichern(incompleteCluster);
        
        // Test both scoring IDs
        Optional<ScoringErgebnis> result1 = scoringDomainService.scoring(testScoringId);
        Optional<ScoringErgebnis> result2 = scoringDomainService.scoring(differentScoringId);
        
        assertTrue(result1.isPresent(), "Complete scoring should succeed");
        assertTrue(result2.isEmpty(), "Incomplete scoring should fail");
    }
}