package com.bigpugloans.scoring.testutils;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;

import java.util.Optional;

public class TestImmobilienFinanzierungsCluster extends ImmobilienFinanzierungsCluster {
    private Optional<ClusterGescored> scoringResult;

    public TestImmobilienFinanzierungsCluster(ScoringId scoringId) {
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