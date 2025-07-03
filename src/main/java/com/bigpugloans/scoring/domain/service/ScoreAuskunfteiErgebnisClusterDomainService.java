package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.ClusterGescored;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;

import java.util.Optional;

public class ScoreAuskunfteiErgebnisClusterDomainService {
    public ScoringErgebnis scoreAuskunfteiErgebnisCluster(AuskunfteiErgebnisCluster auskunfteiErgebnisCluster, ScoringErgebnis scoringErgebnis) {
        Optional<ClusterGescored> auskunfteiErgebnisClusterErgebnis = auskunfteiErgebnisCluster.scoren();
        auskunfteiErgebnisClusterErgebnis.ifPresent(scoringErgebnis::auskunfteiErgebnisClusterHinzufuegen);
        return scoringErgebnis;
    }
}
