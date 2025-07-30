package com.bigpugloans.scoring.testutils;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;

import java.util.Optional;

public class TestAntragstellerCluster extends AntragstellerCluster {
    private ClusterGescored scoringResult = null;

    public TestAntragstellerCluster(ScoringId scoringId) {
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