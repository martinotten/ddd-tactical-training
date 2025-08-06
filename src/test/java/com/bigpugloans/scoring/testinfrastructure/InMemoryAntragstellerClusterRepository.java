package com.bigpugloans.scoring.testinfrastructure;

import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryAntragstellerClusterRepository implements AntragstellerClusterRepository {
    
    private final Map<ScoringId, AntragstellerCluster> clusters = new HashMap<>();
    
    @Override
    public Optional<AntragstellerCluster> lade(ScoringId scoringId) {
        if (!clusters.containsKey(scoringId)) {
            return Optional.empty();
        }
        return Optional.ofNullable(clusters.get(scoringId));
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