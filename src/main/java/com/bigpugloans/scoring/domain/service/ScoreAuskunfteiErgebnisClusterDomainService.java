package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.ClusterGescored;
import com.bigpugloans.scoring.domain.model.ClusterScoringEvent;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;

public class ScoreAuskunfteiErgebnisClusterDomainService {
    public ScoringErgebnis scoreAuskunfteiErgebnisCluster(AuskunfteiErgebnisCluster auskunfteiErgebnisCluster, ScoringErgebnis scoringErgebnis) {
        ClusterScoringEvent auskunfteiErgebnisClusterErgebnis = auskunfteiErgebnisCluster.scoren();
        if(ClusterGescored.class.equals(auskunfteiErgebnisClusterErgebnis.getClass())) {
            scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen((ClusterGescored) auskunfteiErgebnisClusterErgebnis);
        }
        return scoringErgebnis;
    }
}
