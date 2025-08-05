package com.bigpugloans.scoring.testinfrastructure;

import com.bigpugloans.scoring.application.ports.driven.MonatlicheFinanzsituationClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;

import java.util.HashMap;
import java.util.Map;

public class InMemoryMonatlicheFinanzsituationClusterRepository implements MonatlicheFinanzsituationClusterRepository {
    
    private final Map<ScoringId, MonatlicheFinanzsituationCluster> clusters = new HashMap<>();
    
    @Override
    public MonatlicheFinanzsituationCluster lade(ScoringId scoringId) {
        if (!clusters.containsKey(scoringId)) {
            throw new RuntimeException("MonatlicheFinanzsituationCluster not found for ScoringId: " + scoringId);
        }
        return clusters.get(scoringId);
    }
    
    @Override
    public void speichern(MonatlicheFinanzsituationCluster cluster) {
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