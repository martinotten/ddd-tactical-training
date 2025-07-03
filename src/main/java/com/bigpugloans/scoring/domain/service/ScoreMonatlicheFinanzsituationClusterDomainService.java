package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.ClusterGescored;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;

import java.util.Optional;

public class ScoreMonatlicheFinanzsituationClusterDomainService {
    public ScoringErgebnis scoreMonatlicheFinanzsituationCluster(MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster, ScoringErgebnis scoringErgebnis) {
        Optional<ClusterGescored> monatlicheFinanzsituationErgebnis = monatlicheFinanzsituationCluster.scoren();
        monatlicheFinanzsituationErgebnis.ifPresent(scoringErgebnis::monatlicheFinansituationClusterHinzufuegen);
        return scoringErgebnis;
    }
}
