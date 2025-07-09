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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScoringDomainServiceTest {
    
    private AntragstellerClusterRepository antragstellerClusterRepository;
    private MonatlicheFinanzsituationClusterRepository monatlicheFinanzsituationClusterRepository;
    private ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository;
    private AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository;
    
    private ScoringDomainService scoringDomainService;
    
    private ScoringId testScoringId;
    private Antragsnummer testAntragsnummer;
    
    @BeforeEach
    void setUp() {
        antragstellerClusterRepository = mock(AntragstellerClusterRepository.class);
        monatlicheFinanzsituationClusterRepository = mock(MonatlicheFinanzsituationClusterRepository.class);
        immobilienFinanzierungClusterRepository = mock(ImmobilienFinanzierungClusterRepository.class);
        auskunfteiErgebnisClusterRepository = mock(AuskunfteiErgebnisClusterRepository.class);
        
        scoringDomainService = new ScoringDomainService(
            antragstellerClusterRepository,
            monatlicheFinanzsituationClusterRepository,
            immobilienFinanzierungClusterRepository,
            auskunfteiErgebnisClusterRepository
        );
        
        testAntragsnummer = new Antragsnummer("TEST123");
        testScoringId = new ScoringId(testAntragsnummer, ScoringArt.MAIN);
    }
    
    @Test
    void scoring_shouldReturnEmptyOptional_whenAllRepositoriesThrowException() {
        when(antragstellerClusterRepository.lade(testAntragsnummer))
            .thenThrow(new RuntimeException("Repository error"));
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isEmpty());
        verify(antragstellerClusterRepository).lade(testAntragsnummer);
    }
    
    @Test
    void scoring_shouldReturnEmptyOptional_whenAnyClusterScoringReturnsEmpty() {
        AntragstellerCluster antragstellerCluster = mock(AntragstellerCluster.class);
        MonatlicheFinanzsituationCluster monatlicheCluster = mock(MonatlicheFinanzsituationCluster.class);
        ImmobilienFinanzierungsCluster immobilienCluster = mock(ImmobilienFinanzierungsCluster.class);
        AuskunfteiErgebnisCluster auskunfteiCluster = mock(AuskunfteiErgebnisCluster.class);
        
        when(antragstellerClusterRepository.lade(testAntragsnummer)).thenReturn(antragstellerCluster);
        when(monatlicheFinanzsituationClusterRepository.lade(testAntragsnummer)).thenReturn(monatlicheCluster);
        when(immobilienFinanzierungClusterRepository.lade(testAntragsnummer)).thenReturn(immobilienCluster);
        when(auskunfteiErgebnisClusterRepository.lade(testAntragsnummer)).thenReturn(auskunfteiCluster);
        
        ClusterGescored antragstellerResult = new ClusterGescored(testScoringId, new Punkte(100), new KoKriterien(0));
        ClusterGescored monatlicheResult = new ClusterGescored(testScoringId, new Punkte(80), new KoKriterien(0));
        ClusterGescored immobilienResult = new ClusterGescored(testScoringId, new Punkte(90), new KoKriterien(0));
        
        when(antragstellerCluster.scoren()).thenReturn(Optional.of(antragstellerResult));
        when(monatlicheCluster.scoren()).thenReturn(Optional.of(monatlicheResult));
        when(immobilienCluster.scoren()).thenReturn(Optional.of(immobilienResult));
        when(auskunfteiCluster.scoren()).thenReturn(Optional.empty());
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isEmpty());
        verify(antragstellerCluster).scoren();
        verify(monatlicheCluster).scoren();
        verify(immobilienCluster).scoren();
        verify(auskunfteiCluster).scoren();
    }
    
    @Test
    void scoring_shouldReturnScoringErgebnis_whenAllClustersScoreSuccessfully() {
        AntragstellerCluster antragstellerCluster = mock(AntragstellerCluster.class);
        MonatlicheFinanzsituationCluster monatlicheCluster = mock(MonatlicheFinanzsituationCluster.class);
        ImmobilienFinanzierungsCluster immobilienCluster = mock(ImmobilienFinanzierungsCluster.class);
        AuskunfteiErgebnisCluster auskunfteiCluster = mock(AuskunfteiErgebnisCluster.class);
        
        when(antragstellerClusterRepository.lade(testAntragsnummer)).thenReturn(antragstellerCluster);
        when(monatlicheFinanzsituationClusterRepository.lade(testAntragsnummer)).thenReturn(monatlicheCluster);
        when(immobilienFinanzierungClusterRepository.lade(testAntragsnummer)).thenReturn(immobilienCluster);
        when(auskunfteiErgebnisClusterRepository.lade(testAntragsnummer)).thenReturn(auskunfteiCluster);
        
        ClusterGescored antragstellerResult = new ClusterGescored(testScoringId, new Punkte(100), new KoKriterien(0));
        ClusterGescored monatlicheResult = new ClusterGescored(testScoringId, new Punkte(80), new KoKriterien(1));
        ClusterGescored immobilienResult = new ClusterGescored(testScoringId, new Punkte(90), new KoKriterien(0));
        ClusterGescored auskunfteiResult = new ClusterGescored(testScoringId, new Punkte(70), new KoKriterien(2));
        
        when(antragstellerCluster.scoren()).thenReturn(Optional.of(antragstellerResult));
        when(monatlicheCluster.scoren()).thenReturn(Optional.of(monatlicheResult));
        when(immobilienCluster.scoren()).thenReturn(Optional.of(immobilienResult));
        when(auskunfteiCluster.scoren()).thenReturn(Optional.of(auskunfteiResult));
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isPresent());
        ScoringErgebnis scoringErgebnis = result.get();
        assertNotNull(scoringErgebnis);
        assertEquals(testScoringId, scoringErgebnis.scoringId());
        
        verify(antragstellerCluster).scoren();
        verify(monatlicheCluster).scoren();
        verify(immobilienCluster).scoren();
        verify(auskunfteiCluster).scoren();
    }
    
    @Test
    void scoring_shouldReturnEmptyOptional_whenSomeRepositoriesThrowException() {
        AntragstellerCluster antragstellerCluster = mock(AntragstellerCluster.class);
        MonatlicheFinanzsituationCluster monatlicheCluster = mock(MonatlicheFinanzsituationCluster.class);
        
        when(antragstellerClusterRepository.lade(testAntragsnummer)).thenReturn(antragstellerCluster);
        when(monatlicheFinanzsituationClusterRepository.lade(testAntragsnummer)).thenReturn(monatlicheCluster);
        when(immobilienFinanzierungClusterRepository.lade(testAntragsnummer))
            .thenThrow(new RuntimeException("Repository error"));
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isEmpty());
        verify(antragstellerClusterRepository).lade(testAntragsnummer);
        verify(monatlicheFinanzsituationClusterRepository).lade(testAntragsnummer);
        verify(immobilienFinanzierungClusterRepository).lade(testAntragsnummer);
    }
    
    @Test
    void scoring_shouldHandleAllClusterTypes_whenScoring() {
        AntragstellerCluster antragstellerCluster = mock(AntragstellerCluster.class);
        MonatlicheFinanzsituationCluster monatlicheCluster = mock(MonatlicheFinanzsituationCluster.class);
        ImmobilienFinanzierungsCluster immobilienCluster = mock(ImmobilienFinanzierungsCluster.class);
        AuskunfteiErgebnisCluster auskunfteiCluster = mock(AuskunfteiErgebnisCluster.class);
        
        when(antragstellerClusterRepository.lade(testAntragsnummer)).thenReturn(antragstellerCluster);
        when(monatlicheFinanzsituationClusterRepository.lade(testAntragsnummer)).thenReturn(monatlicheCluster);
        when(immobilienFinanzierungClusterRepository.lade(testAntragsnummer)).thenReturn(immobilienCluster);
        when(auskunfteiErgebnisClusterRepository.lade(testAntragsnummer)).thenReturn(auskunfteiCluster);
        
        ClusterGescored antragstellerResult = new ClusterGescored(testScoringId, new Punkte(100));
        ClusterGescored monatlicheResult = new ClusterGescored(testScoringId, new Punkte(80));
        ClusterGescored immobilienResult = new ClusterGescored(testScoringId, new Punkte(90));
        ClusterGescored auskunfteiResult = new ClusterGescored(testScoringId, new Punkte(70));
        
        when(antragstellerCluster.scoren()).thenReturn(Optional.of(antragstellerResult));
        when(monatlicheCluster.scoren()).thenReturn(Optional.of(monatlicheResult));
        when(immobilienCluster.scoren()).thenReturn(Optional.of(immobilienResult));
        when(auskunfteiCluster.scoren()).thenReturn(Optional.of(auskunfteiResult));
        
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        
        assertTrue(result.isPresent());
        
        verify(antragstellerClusterRepository).lade(testAntragsnummer);
        verify(monatlicheFinanzsituationClusterRepository).lade(testAntragsnummer);
        verify(immobilienFinanzierungClusterRepository).lade(testAntragsnummer);
        verify(auskunfteiErgebnisClusterRepository).lade(testAntragsnummer);
    }
    
    @Test
    void scoring_shouldCallScoreOnEachCluster() {
        AntragstellerCluster antragstellerCluster = mock(AntragstellerCluster.class);
        MonatlicheFinanzsituationCluster monatlicheCluster = mock(MonatlicheFinanzsituationCluster.class);
        ImmobilienFinanzierungsCluster immobilienCluster = mock(ImmobilienFinanzierungsCluster.class);
        AuskunfteiErgebnisCluster auskunfteiCluster = mock(AuskunfteiErgebnisCluster.class);
        
        when(antragstellerClusterRepository.lade(testAntragsnummer)).thenReturn(antragstellerCluster);
        when(monatlicheFinanzsituationClusterRepository.lade(testAntragsnummer)).thenReturn(monatlicheCluster);
        when(immobilienFinanzierungClusterRepository.lade(testAntragsnummer)).thenReturn(immobilienCluster);
        when(auskunfteiErgebnisClusterRepository.lade(testAntragsnummer)).thenReturn(auskunfteiCluster);
        
        when(antragstellerCluster.scoren()).thenReturn(Optional.empty());
        when(monatlicheCluster.scoren()).thenReturn(Optional.empty());
        when(immobilienCluster.scoren()).thenReturn(Optional.empty());
        when(auskunfteiCluster.scoren()).thenReturn(Optional.empty());
        
        scoringDomainService.scoring(testScoringId);
        
        verify(antragstellerCluster, times(1)).scoren();
        verify(monatlicheCluster, times(1)).scoren();
        verify(immobilienCluster, times(1)).scoren();
        verify(auskunfteiCluster, times(1)).scoren();
    }
    
    @Test
    void scoring_shouldPassCorrectAntragsnummerToAllRepositories() {
        when(antragstellerClusterRepository.lade(testAntragsnummer))
            .thenThrow(new RuntimeException("Test exception"));
        
        scoringDomainService.scoring(testScoringId);
        
        verify(antragstellerClusterRepository).lade(testAntragsnummer);
        verifyNoInteractions(monatlicheFinanzsituationClusterRepository);
        verifyNoInteractions(immobilienFinanzierungClusterRepository);
        verifyNoInteractions(auskunfteiErgebnisClusterRepository);
    }
}