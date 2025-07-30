package com.bigpugloans.scoring.testutils;

import com.bigpugloans.scoring.application.ports.driven.AntragstellerClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAntragstellerClusterRepository implements AntragstellerClusterRepository {
    private final Map<ScoringId, AntragstellerCluster> clusters = new HashMap<>();
    private boolean shouldThrowException = false;

    @Override
    public void speichern(AntragstellerCluster antragstellerCluster) {
        if (shouldThrowException) {
            throw new RuntimeException("Repository error");
        }
        clusters.put(antragstellerCluster.scoringId(), antragstellerCluster);
    }

    @Override
    public AntragstellerCluster lade(ScoringId scoringId) {
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