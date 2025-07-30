package com.bigpugloans.scoring.testutils;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;

import java.util.Optional;

public class TestMonatlicheFinanzsituationCluster extends MonatlicheFinanzsituationCluster {
    private Optional<ClusterGescored> scoringResult;

    public TestMonatlicheFinanzsituationCluster(ScoringId scoringId) {
        super(scoringId);
        this.scoringResult = Optional.empty();
    }

    public void setScoringResult(Optional<ClusterGescored> scoringResult) {
        this.scoringResult = scoringResult;
    }

    public void setScoringResult(ClusterGescored result) {
        this.scoringResult = Optional.of(result);
    }

    @Override
    public Optional<ClusterGescored> scoren() {
        return scoringResult;
    }
}