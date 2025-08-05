package com.bigpugloans.scoring.testinfrastructure;

import com.bigpugloans.scoring.application.ports.driven.*;

public class TestRepositoryManager {
    
    public final InMemoryAntragstellerClusterRepository antragstellerClusterRepository;
    public final InMemoryMonatlicheFinanzsituationClusterRepository monatlicheFinanzsituationClusterRepository;
    public final InMemoryImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository;
    public final InMemoryAuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository;
    public final InMemoryScoringErgebnisRepository scoringErgebnisRepository;
    
    public TestRepositoryManager() {
        this.antragstellerClusterRepository = new InMemoryAntragstellerClusterRepository();
        this.monatlicheFinanzsituationClusterRepository = new InMemoryMonatlicheFinanzsituationClusterRepository();
        this.immobilienFinanzierungClusterRepository = new InMemoryImmobilienFinanzierungClusterRepository();
        this.auskunfteiErgebnisClusterRepository = new InMemoryAuskunfteiErgebnisClusterRepository();
        this.scoringErgebnisRepository = new InMemoryScoringErgebnisRepository();
    }
    
    public void clear() {
        antragstellerClusterRepository.clear();
        monatlicheFinanzsituationClusterRepository.clear();
        immobilienFinanzierungClusterRepository.clear();
        auskunfteiErgebnisClusterRepository.clear();
        scoringErgebnisRepository.clear();
    }
    
    public boolean hasAntragstellerCluster(com.bigpugloans.scoring.domain.model.ScoringId scoringId) {
        return antragstellerClusterRepository.contains(scoringId);
    }
    
    public boolean hasMonatlicheFinanzsituationCluster(com.bigpugloans.scoring.domain.model.ScoringId scoringId) {
        return monatlicheFinanzsituationClusterRepository.contains(scoringId);
    }
    
    public boolean hasImmobilienFinanzierungCluster(com.bigpugloans.scoring.domain.model.ScoringId scoringId) {
        return immobilienFinanzierungClusterRepository.contains(scoringId);
    }
    
    public boolean hasAuskunfteiErgebnisCluster(com.bigpugloans.scoring.domain.model.ScoringId scoringId) {
        return auskunfteiErgebnisClusterRepository.contains(scoringId);
    }
    
    public boolean hasScoringErgebnis(com.bigpugloans.scoring.domain.model.ScoringId scoringId) {
        return scoringErgebnisRepository.contains(scoringId);
    }
}