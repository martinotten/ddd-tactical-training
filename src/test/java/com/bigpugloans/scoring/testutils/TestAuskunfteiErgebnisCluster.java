package com.bigpugloans.scoring.testutils;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;

import java.util.Optional;

public class TestAuskunfteiErgebnisCluster extends AuskunfteiErgebnisCluster {
    private ClusterGescored scoringResult = null;

    public TestAuskunfteiErgebnisCluster(ScoringId scoringId, AntragstellerID antragstellerID) {
        super(scoringId, antragstellerID);
    }

    public void setScoringResult(ClusterGescored result) {
        this.scoringResult = result;
    }

    @Override
    public Optional<ClusterGescored> scoren() {
        return Optional.ofNullable(scoringResult);
    }
}