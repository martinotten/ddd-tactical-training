package com.bigpugloans.scoring.testinfrastructure;

import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryAuskunfteiErgebnisClusterRepository implements AuskunfteiErgebnisClusterRepository {
    
    private final Map<ScoringId, AuskunfteiErgebnisCluster> clusters = new HashMap<>();
    
    @Override
    public Optional<AuskunfteiErgebnisCluster> lade(ScoringId scoringId) {
        if (!clusters.containsKey(scoringId)) {
            return Optional.empty();
        }
        return Optional.ofNullable(clusters.get(scoringId));
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