package com.bigpugloans.scoring.testinfrastructure;

import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryMonatlicheFinanzsituationClusterRepository implements MonatlicheFinanzsituationClusterRepository {
    
    private final Map<ScoringId, MonatlicheFinanzsituationCluster> clusters = new HashMap<>();
    
    @Override
    public Optional<MonatlicheFinanzsituationCluster> lade(ScoringId scoringId) {
        if (!clusters.containsKey(scoringId)) {
            return Optional.empty();
        }
        return Optional.ofNullable(clusters.get(scoringId));
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