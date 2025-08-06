package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerClusterRepository;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterRepository;

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
                antragstellerClusterRepository.lade(scoringId).get(),
                monatlicheFinanzsituationClusterRepository.lade(scoringId).get(),
                immobilienFinanzierungClusterRepository.lade(scoringId).get(),
                auskunfteiErgebnisClusterRepository.lade(scoringId).get()
            );
        } catch (Exception e) {
            return Set.of(); // Return empty set if any cluster fails to load
        }
    }
}
