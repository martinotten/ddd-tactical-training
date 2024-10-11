package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.ClusterGescored;
import com.bigpugloans.scoring.domain.model.ClusterScoringEvent;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;

public class ScoreAntragstellerClusterDomainService {
    public ScoringErgebnis scoreAntragstellerCluster(AntragstellerCluster antragstellerCluster, ScoringErgebnis scoringErgebnis) {
        ClusterScoringEvent antragstellerClusterErgebnis = antragstellerCluster.scoren();
        if(ClusterGescored.class.equals(antragstellerClusterErgebnis.getClass())) {
            scoringErgebnis.antragstellerClusterHinzufuegen((ClusterGescored) antragstellerClusterErgebnis);
        }
        return scoringErgebnis;
    }
}
