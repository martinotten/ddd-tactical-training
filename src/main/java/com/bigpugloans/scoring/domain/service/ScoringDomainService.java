package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.application.ports.driven.*;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;

import java.util.Optional;
import java.util.Set;

public class ScoringDomainService {
    
    private final AntragstellerClusterRepository antragstellerClusterRepository;
    private final MonatlicheFinanzsituationClusterRepository monatlicheFinanzsituationClusterRepository;
    private final ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository;
    private final AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository;
    
    public ScoringDomainService(
            AntragstellerClusterRepository antragstellerClusterRepository,
            MonatlicheFinanzsituationClusterRepository monatlicheFinanzsituationClusterRepository,
            ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository,
            AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository) {
        this.antragstellerClusterRepository = antragstellerClusterRepository;
        this.monatlicheFinanzsituationClusterRepository = monatlicheFinanzsituationClusterRepository;
        this.immobilienFinanzierungClusterRepository = immobilienFinanzierungClusterRepository;
        this.auskunfteiErgebnisClusterRepository = auskunfteiErgebnisClusterRepository;
    }
    
    public Optional<ScoringErgebnis> scoring(ScoringId scoringId) {
        Set<ClusterScoring> clusters = loadAllClusters(scoringId);
        return ScoringErgebnis.ausClusterErgebnissen(scoringId, clusters);
    }


    private Set<ClusterScoring> loadAllClusters(ScoringId scoringId) {
        try {
            return Set.of(
                antragstellerClusterRepository.lade(scoringId.antragsnummer()),
                monatlicheFinanzsituationClusterRepository.lade(scoringId.antragsnummer()),
                immobilienFinanzierungClusterRepository.lade(scoringId.antragsnummer()),
                auskunfteiErgebnisClusterRepository.lade(scoringId.antragsnummer())
            );
        } catch (Exception e) {
            return Set.of(); // Return empty set if any cluster fails to load
        }
    }
}
