package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.ClusterGescored;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;

import java.util.Optional;

public class ScoreImmobilienFinanzierungsClusterDomainService {
    public ScoringErgebnis scoreImmobilienFinanzierungsCluster(ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster, ScoringErgebnis scoringErgebnis) {
        Optional<ClusterGescored> immobilienFinanzierungsClusterErgebnis = immobilienFinanzierungsCluster.scoren();
        immobilienFinanzierungsClusterErgebnis.ifPresent(scoringErgebnis::immobilienFinanzierungClusterHinzufuegen);
        return scoringErgebnis;
    }
}
