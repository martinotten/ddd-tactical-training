package com.bigpugloans.scoring.testutils;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;

import java.util.Optional;

public class TestMonatlicheFinanzsituationCluster extends MonatlicheFinanzsituationCluster {
    private ClusterGescored scoringResult = null;

    public TestMonatlicheFinanzsituationCluster(ScoringId scoringId) {
        super(scoringId);
    }

    public void setScoringResult(ClusterGescored result) {
        this.scoringResult = result;
    }

    @Override
    public Optional<ClusterGescored> scoren() {
        return Optional.ofNullable(scoringResult);
    }
}