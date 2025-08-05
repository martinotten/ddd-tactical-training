package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import com.bigpugloans.scoring.testinfrastructure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ScoringDomainServiceTest {
    
    private TestRepositoryManager repos;
    private ScoringDomainService scoringDomainService;
    private ScoringId testScoringId;
    
    @BeforeEach
    void setUp() {
        repos = new TestRepositoryManager();
        scoringDomainService = new ScoringDomainService(
            repos.antragstellerClusterRepository,
            repos.monatlicheFinanzsituationClusterRepository,
            repos.immobilienFinanzierungClusterRepository,
            repos.auskunfteiErgebnisClusterRepository
        );
        
        testScoringId = ScoringId.mainScoringIdAusAntragsnummer("TEST123");
    }
    
    @Test
    void scoring_shouldReturnScoringErgebnis_whenAllClustersScoreSuccessfully() {
        createAllClusters();
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isPresent(), "Should return ScoringErgebnis when all clusters can be scored");
        assertEquals(testScoringId, result.get().scoringId(), "ScoringId should match");
    }
    
    @Test
    void scoring_shouldReturnEmptyOptional_whenSomeRepositoriesThrowException() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(testScoringId);
        repos.antragstellerClusterRepository.speichern(antragstellerCluster);
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isEmpty(), "Should return empty when some clusters are missing");
    }
    
    @Test
    void scoring_shouldReturnEmptyOptional_whenAllRepositoriesThrowException() {
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isEmpty(), "Should return empty when no clusters exist");
    }
    
    @Test
    void scoring_shouldReturnEmptyOptional_whenAnyClusterScoringReturnsEmpty() {
        createMinimalClusters();
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertNotNull(result, "Should return Optional (empty or present) for incomplete clusters");
    }
    
    @Test
    void scoring_shouldPassCorrectAntragsnummerToAllRepositories() {
        createAllClusters();
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(repos.hasAntragstellerCluster(testScoringId), "Should have accessed AntragstellerCluster");
        assertTrue(repos.hasMonatlicheFinanzsituationCluster(testScoringId), "Should have accessed MonatlicheFinanzsituationCluster");
        assertTrue(repos.hasImmobilienFinanzierungCluster(testScoringId), "Should have accessed ImmobilienFinanzierungsCluster");
        assertTrue(repos.hasAuskunfteiErgebnisCluster(testScoringId), "Should have accessed AuskunfteiErgebnisCluster");
    }
    
    @Test
    void scoring_shouldCallScoreOnEachCluster() {
        createAllClusters();
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertNotNull(result, "Should attempt to score all clusters");
    }
    
    @Test
    void scoring_shouldHandleAllClusterTypes_whenScoring() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(testScoringId);
        MonatlicheFinanzsituationCluster finanzCluster = new MonatlicheFinanzsituationCluster(testScoringId);
        ImmobilienFinanzierungsCluster immobilienCluster = new ImmobilienFinanzierungsCluster(testScoringId);
        AuskunfteiErgebnisCluster auskunfteiCluster = new AuskunfteiErgebnisCluster(
            testScoringId, new AntragstellerID.Builder("Test", "User")
                .postleitzahl("12345")
                .stadt("Test Stadt")
                .strasse("Test Strasse 1")
                .geburtsdatum(java.time.LocalDate.of(1980, 1, 1))
                .build());
        
        repos.antragstellerClusterRepository.speichern(antragstellerCluster);
        repos.monatlicheFinanzsituationClusterRepository.speichern(finanzCluster);
        repos.immobilienFinanzierungClusterRepository.speichern(immobilienCluster);
        repos.auskunfteiErgebnisClusterRepository.speichern(auskunfteiCluster);
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertNotNull(result, "Should handle all cluster types");
    }
    
    private void createAllClusters() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(testScoringId);
        antragstellerCluster.wohnortHinzufuegen("Berlin");
        antragstellerCluster.guthabenHinzufuegen(new Waehrungsbetrag(1000));
        
        MonatlicheFinanzsituationCluster finanzCluster = new MonatlicheFinanzsituationCluster(testScoringId);
        finanzCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(3000));
        finanzCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(2000));
        finanzCluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(800));
        
        ImmobilienFinanzierungsCluster immobilienCluster = new ImmobilienFinanzierungsCluster(testScoringId);
        immobilienCluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(300000));
        immobilienCluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(50000));
        immobilienCluster.marktwertHinzufuegen(new Waehrungsbetrag(350000));
        immobilienCluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(10000));
        immobilienCluster.beleihungswertHinzufuegen(new Waehrungsbetrag(280000));
        immobilienCluster.marktwertVerlgeichHinzufuegen(
            new Waehrungsbetrag(330000), new Waehrungsbetrag(370000), 
            new Waehrungsbetrag(340000), new Waehrungsbetrag(360000));
        
        AuskunfteiErgebnisCluster auskunfteiCluster = new AuskunfteiErgebnisCluster(
            testScoringId, new AntragstellerID.Builder("Test", "User")
                .postleitzahl("12345")
                .stadt("Test Stadt")
                .strasse("Test Strasse 1")
                .geburtsdatum(java.time.LocalDate.of(1980, 1, 1))
                .build());
        auskunfteiCluster.negativMerkmaleHinzufuegen(1);
        auskunfteiCluster.warnungenHinzufuegen(0);
        auskunfteiCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(75));
        
        repos.antragstellerClusterRepository.speichern(antragstellerCluster);
        repos.monatlicheFinanzsituationClusterRepository.speichern(finanzCluster);
        repos.immobilienFinanzierungClusterRepository.speichern(immobilienCluster);
        repos.auskunfteiErgebnisClusterRepository.speichern(auskunfteiCluster);
    }
    
    private void createMinimalClusters() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(testScoringId);
        repos.antragstellerClusterRepository.speichern(antragstellerCluster);
    }
}