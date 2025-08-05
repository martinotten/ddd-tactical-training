package com.bigpugloans.scoring.testinfrastructure;

import com.bigpugloans.scoring.application.ports.driven.AuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAuskunfteiErgebnisClusterRepository implements AuskunfteiErgebnisClusterRepository {
    
    private final Map<ScoringId, AuskunfteiErgebnisCluster> clusters = new HashMap<>();
    
    @Override
    public AuskunfteiErgebnisCluster lade(ScoringId scoringId) {
        if (!clusters.containsKey(scoringId)) {
            throw new RuntimeException("AuskunfteiErgebnisCluster not found for ScoringId: " + scoringId);
        }
        return clusters.get(scoringId);
    }
    
    @Override
    public void speichern(AuskunfteiErgebnisCluster cluster) {
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