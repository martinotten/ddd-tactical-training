package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.ClusterGescored;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;

import java.util.Optional;

public class ScoreAntragstellerClusterDomainService {
    public ScoringErgebnis scoreAntragstellerCluster(AntragstellerCluster antragstellerCluster, ScoringErgebnis scoringErgebnis) {
        Optional<ClusterGescored> antragstellerClusterErgebnis = antragstellerCluster.scoren();
        antragstellerClusterErgebnis.ifPresent(scoringErgebnis::antragstellerClusterHinzufuegen);
        return scoringErgebnis;
    }
}
