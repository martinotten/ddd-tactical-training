package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.application.ports.driven.*;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;

import java.util.Optional;
import java.util.Set;
import java.util.List;

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
        // Load all clusters into a set
        Set<ClusterScoring> clusters = loadAllClusters(scoringId);
        
        if (clusters.isEmpty()) {
            return Optional.empty();
        }
        
        // Score all clusters using Stream API
        List<Optional<ClusterGescored>> scoringResults = clusters.stream()
                .map(ClusterScoring::scoren)
                .toList();
        
        // Check if any cluster could not be scored (contains empty Optional)
        boolean anyClusterNotScored = scoringResults.stream()
                .anyMatch(Optional::isEmpty);
        
        if (anyClusterNotScored) {
            return Optional.empty();
        }
        
        // Create combined scoring result
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(scoringId);
        
        // Add cluster results by pairing them with their original clusters
        List<ClusterScoring> clusterList = clusters.stream().toList();
        for (int i = 0; i < clusterList.size(); i++) {
            Optional<ClusterGescored> result = scoringResults.get(i);
            if (result.isPresent()) {
                ClusterScoring cluster = clusterList.get(i);
                addClusterResult(scoringErgebnis, result.get(), cluster);
            }
        }
        
        return Optional.of(scoringErgebnis);
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
    
    private void addClusterResult(ScoringErgebnis scoringErgebnis, ClusterGescored clusterResult, ClusterScoring cluster) {
        switch (cluster) {
            case AntragstellerCluster ignored -> scoringErgebnis.antragstellerClusterHinzufuegen(clusterResult);
            case MonatlicheFinanzsituationCluster ignored -> scoringErgebnis.monatlicheFinansituationClusterHinzufuegen(clusterResult);
            case ImmobilienFinanzierungsCluster ignored -> scoringErgebnis.immobilienFinanzierungClusterHinzufuegen(clusterResult);
            case AuskunfteiErgebnisCluster ignored -> scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(clusterResult);
            default -> throw new IllegalArgumentException("Unknown cluster type: " + cluster.getClass().getSimpleName());
        }
    }
}
