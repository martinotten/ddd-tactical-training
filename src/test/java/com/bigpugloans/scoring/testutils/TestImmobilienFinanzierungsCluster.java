package com.bigpugloans.scoring.testutils;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;

import java.util.Optional;

public class TestImmobilienFinanzierungsCluster extends ImmobilienFinanzierungsCluster {
    private ClusterGescored scoringResult = null;

    public TestImmobilienFinanzierungsCluster(ScoringId scoringId) {
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