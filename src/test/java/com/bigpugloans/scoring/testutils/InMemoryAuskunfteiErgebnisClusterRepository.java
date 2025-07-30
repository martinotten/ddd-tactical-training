package com.bigpugloans.scoring.testutils;

import com.bigpugloans.scoring.application.ports.driven.AuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAuskunfteiErgebnisClusterRepository implements AuskunfteiErgebnisClusterRepository {
    private final Map<ScoringId, AuskunfteiErgebnisCluster> clusters = new HashMap<>();
    private boolean shouldThrowException = false;

    @Override
    public void speichern(AuskunfteiErgebnisCluster auskunfteiErgebnisCluster) {
        if (shouldThrowException) {
            throw new RuntimeException("Repository error");
        }
        clusters.put(auskunfteiErgebnisCluster.scoringId(), auskunfteiErgebnisCluster);
    }

    @Override
    public AuskunfteiErgebnisCluster lade(ScoringId scoringId) {
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