package com.bigpugloans.scoring.testinfrastructure;

import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryImmobilienFinanzierungClusterRepository implements ImmobilienFinanzierungClusterRepository {
    
    private final Map<ScoringId, ImmobilienFinanzierungsCluster> clusters = new HashMap<>();
    
    @Override
    public Optional<ImmobilienFinanzierungsCluster> lade(ScoringId scoringId) {
        if (!clusters.containsKey(scoringId)) {
            return Optional.empty();
        }
        return Optional.ofNullable(clusters.get(scoringId));
    }
    
    @Override
    public void speichern(ImmobilienFinanzierungsCluster cluster) {
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