package com.bigpugloans.scoring.testutils;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;

import java.util.Optional;

public class TestAuskunfteiErgebnisCluster extends AuskunfteiErgebnisCluster {
    private Optional<ClusterGescored> scoringResult;

    public TestAuskunfteiErgebnisCluster(ScoringId scoringId, AntragstellerID antragstellerID) {
        super(scoringId, antragstellerID);
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