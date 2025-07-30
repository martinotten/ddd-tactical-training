package com.bigpugloans.scoring.testutils;

import com.bigpugloans.scoring.application.ports.driven.ImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;

import java.util.HashMap;
import java.util.Map;

public class InMemoryImmobilienFinanzierungClusterRepository implements ImmobilienFinanzierungClusterRepository {
    private final Map<ScoringId, ImmobilienFinanzierungsCluster> clusters = new HashMap<>();
    private boolean shouldThrowException = false;

    @Override
    public void speichern(ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster) {
        if (shouldThrowException) {
            throw new RuntimeException("Repository error");
        }
        clusters.put(immobilienFinanzierungsCluster.scoringId(), immobilienFinanzierungsCluster);
    }

    @Override
    public ImmobilienFinanzierungsCluster lade(ScoringId scoringId) {
        if (shouldThrowException) {
            throw new RuntimeException("Repository error");
        }
        return clusters.get(scoringId);
    }

    public void setShouldThrowException(boolean shouldThrowException) {
        this.shouldThrowException = shouldThrowException;
    }

    public void clear() {
        clusters.clear();
    }
}