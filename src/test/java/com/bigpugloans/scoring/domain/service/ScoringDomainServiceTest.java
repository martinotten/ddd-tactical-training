package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import com.bigpugloans.scoring.testutils.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ScoringDomainServiceTest {
    
    private InMemoryAntragstellerClusterRepository antragstellerClusterRepository;
    private InMemoryMonatlicheFinanzsituationClusterRepository monatlicheFinanzsituationClusterRepository;
    private InMemoryImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository;
    private InMemoryAuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository;
    
    private ScoringDomainService scoringDomainService;
    
    private ScoringId testScoringId;
    private AntragstellerID testAntragstellerID;

    @BeforeEach
    void setUp() {
        antragstellerClusterRepository = new InMemoryAntragstellerClusterRepository();
        monatlicheFinanzsituationClusterRepository = new InMemoryMonatlicheFinanzsituationClusterRepository();
        immobilienFinanzierungClusterRepository = new InMemoryImmobilienFinanzierungClusterRepository();
        auskunfteiErgebnisClusterRepository = new InMemoryAuskunfteiErgebnisClusterRepository();
        
        scoringDomainService = new ScoringDomainService(
            antragstellerClusterRepository,
            monatlicheFinanzsituationClusterRepository,
            immobilienFinanzierungClusterRepository,
            auskunfteiErgebnisClusterRepository
        );

        Antragsnummer testAntragsnummer = new Antragsnummer("TEST123");
        testScoringId = ScoringId.mainScoringIdAusAntragsnummer(testAntragsnummer.nummer());
        testAntragstellerID = new AntragstellerID("ANTR456");
    }
    
    @Test
    void scoring_shouldReturnEmptyOptional_whenAllRepositoriesThrowException() {
        antragstellerClusterRepository.setShouldThrowException(true);
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isEmpty());
    }
    
    @Test
    void scoring_shouldReturnEmptyOptional_whenAnyClusterScoringReturnsEmpty() {
        // Create test clusters with specific scoring results
        TestAntragstellerCluster antragstellerCluster = new TestAntragstellerCluster(testScoringId);
        TestMonatlicheFinanzsituationCluster monatlicheCluster = new TestMonatlicheFinanzsituationCluster(testScoringId);
        TestImmobilienFinanzierungsCluster immobilienCluster = new TestImmobilienFinanzierungsCluster(testScoringId);
        TestAuskunfteiErgebnisCluster auskunfteiCluster = new TestAuskunfteiErgebnisCluster(testScoringId, testAntragstellerID);

        // Store clusters in repositories
        antragstellerClusterRepository.speichern(antragstellerCluster);
        monatlicheFinanzsituationClusterRepository.speichern(monatlicheCluster);
        immobilienFinanzierungClusterRepository.speichern(immobilienCluster);
        auskunfteiErgebnisClusterRepository.speichern(auskunfteiCluster);
        
        // Configure scoring results - one returns empty
        ClusterGescored antragstellerResult = new ClusterGescored(testScoringId, new Punkte(100), new KoKriterien(0));
        ClusterGescored monatlicheResult = new ClusterGescored(testScoringId, new Punkte(80), new KoKriterien(0));
        ClusterGescored immobilienResult = new ClusterGescored(testScoringId, new Punkte(90), new KoKriterien(0));
        
        antragstellerCluster.setScoringResult(antragstellerResult);
        monatlicheCluster.setScoringResult(monatlicheResult);
        immobilienCluster.setScoringResult(immobilienResult);
        // auskunfteiCluster intentionally left without result to return empty
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isEmpty());
    }
    
    @Test
    void scoring_shouldReturnScoringErgebnis_whenAllClustersScoreSuccessfully() {
        // Create test clusters
        TestAntragstellerCluster antragstellerCluster = new TestAntragstellerCluster(testScoringId);
        TestMonatlicheFinanzsituationCluster monatlicheCluster = new TestMonatlicheFinanzsituationCluster(testScoringId);
        TestImmobilienFinanzierungsCluster immobilienCluster = new TestImmobilienFinanzierungsCluster(testScoringId);
        TestAuskunfteiErgebnisCluster auskunfteiCluster = new TestAuskunfteiErgebnisCluster(testScoringId, testAntragstellerID);
        
        // Store clusters in repositories
        antragstellerClusterRepository.speichern(antragstellerCluster);
        monatlicheFinanzsituationClusterRepository.speichern(monatlicheCluster);
        immobilienFinanzierungClusterRepository.speichern(immobilienCluster);
        auskunfteiErgebnisClusterRepository.speichern(auskunfteiCluster);
        
        // Configure all clusters to return successful scoring results
        ClusterGescored antragstellerResult = new ClusterGescored(testScoringId, new Punkte(100), new KoKriterien(0));
        ClusterGescored monatlicheResult = new ClusterGescored(testScoringId, new Punkte(80), new KoKriterien(1));
        ClusterGescored immobilienResult = new ClusterGescored(testScoringId, new Punkte(90), new KoKriterien(0));
        ClusterGescored auskunfteiResult = new ClusterGescored(testScoringId, new Punkte(70), new KoKriterien(2));
        
        antragstellerCluster.setScoringResult(antragstellerResult);
        monatlicheCluster.setScoringResult(monatlicheResult);
        immobilienCluster.setScoringResult(immobilienResult);
        auskunfteiCluster.setScoringResult(auskunfteiResult);
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isPresent());
        ScoringErgebnis scoringErgebnis = result.get();
        assertNotNull(scoringErgebnis);
        assertEquals(testScoringId, scoringErgebnis.scoringId());
    }
    
    @Test
    void scoring_shouldReturnEmptyOptional_whenSomeRepositoriesThrowException() {
        // Setup first two repositories with clusters
        TestAntragstellerCluster antragstellerCluster = new TestAntragstellerCluster(testScoringId);
        TestMonatlicheFinanzsituationCluster monatlicheCluster = new TestMonatlicheFinanzsituationCluster(testScoringId);
        
        antragstellerClusterRepository.speichern(antragstellerCluster);
        monatlicheFinanzsituationClusterRepository.speichern(monatlicheCluster);
        
        // Make the third repository throw an exception
        immobilienFinanzierungClusterRepository.setShouldThrowException(true);
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isEmpty());
    }
    
    @Test
    void scoring_shouldHandleAllClusterTypes_whenScoring() {
        // Create test clusters
        TestAntragstellerCluster antragstellerCluster = new TestAntragstellerCluster(testScoringId);
        TestMonatlicheFinanzsituationCluster monatlicheCluster = new TestMonatlicheFinanzsituationCluster(testScoringId);
        TestImmobilienFinanzierungsCluster immobilienCluster = new TestImmobilienFinanzierungsCluster(testScoringId);
        TestAuskunfteiErgebnisCluster auskunfteiCluster = new TestAuskunfteiErgebnisCluster(testScoringId, testAntragstellerID);
        
        // Store clusters in repositories
        antragstellerClusterRepository.speichern(antragstellerCluster);
        monatlicheFinanzsituationClusterRepository.speichern(monatlicheCluster);
        immobilienFinanzierungClusterRepository.speichern(immobilienCluster);
        auskunfteiErgebnisClusterRepository.speichern(auskunfteiCluster);
        
        // Configure scoring results
        ClusterGescored antragstellerResult = new ClusterGescored(testScoringId, new Punkte(100));
        ClusterGescored monatlicheResult = new ClusterGescored(testScoringId, new Punkte(80));
        ClusterGescored immobilienResult = new ClusterGescored(testScoringId, new Punkte(90));
        ClusterGescored auskunfteiResult = new ClusterGescored(testScoringId, new Punkte(70));
        
        antragstellerCluster.setScoringResult(antragstellerResult);
        monatlicheCluster.setScoringResult(monatlicheResult);
        immobilienCluster.setScoringResult(immobilienResult);
        auskunfteiCluster.setScoringResult(auskunfteiResult);
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isPresent());
    }
    
    @Test
    void scoring_shouldReturnEmptyOptional_whenNoDataExists() {
        // Don't store any clusters in repositories
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isEmpty());
    }
}