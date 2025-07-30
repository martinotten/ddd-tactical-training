package com.bigpugloans.scoring.testutils;

import com.bigpugloans.scoring.application.ports.driven.MonatlicheFinanzsituationClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;

import java.util.HashMap;
import java.util.Map;

public class InMemoryMonatlicheFinanzsituationClusterRepository implements MonatlicheFinanzsituationClusterRepository {
    private final Map<ScoringId, MonatlicheFinanzsituationCluster> clusters = new HashMap<>();
    private boolean shouldThrowException = false;

    @Override
    public void speichern(MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster) {
        if (shouldThrowException) {
            throw new RuntimeException("Repository error");
        }
        clusters.put(monatlicheFinanzsituationCluster.scoringId(), monatlicheFinanzsituationCluster);
    }

    @Override
    public MonatlicheFinanzsituationCluster lade(ScoringId scoringId) {
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