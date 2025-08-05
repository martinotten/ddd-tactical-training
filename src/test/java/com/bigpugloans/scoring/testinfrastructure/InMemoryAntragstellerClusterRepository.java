package com.bigpugloans.scoring.testinfrastructure;

import com.bigpugloans.scoring.application.ports.driven.AntragstellerClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAntragstellerClusterRepository implements AntragstellerClusterRepository {
    
    private final Map<ScoringId, AntragstellerCluster> clusters = new HashMap<>();
    
    @Override
    public AntragstellerCluster lade(ScoringId scoringId) {
        if (!clusters.containsKey(scoringId)) {
            throw new RuntimeException("AntragstellerCluster not found for ScoringId: " + scoringId);
        }
        return clusters.get(scoringId);
    }
    
    @Override
    public void speichern(AntragstellerCluster cluster) {
        clusters.put(cluster.scoringId(), cluster);
    }
    
    public void clear() {
        clusters.clear();
    }
    
    public boolean contains(ScoringId scoringId) {
        return clusters.containsKey(scoringId);
    }
    
    public int size() {
        return clusters.size();
    }
}