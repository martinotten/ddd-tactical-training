package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.ClusterGescored;
import com.bigpugloans.scoring.domain.model.ClusterScoringEvent;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import org.jmolecules.architecture.onion.classical.DomainServiceRing;

@DomainServiceRing
public class ScoreImmobilienFinanzierungsClusterDomainService {
    public ScoringErgebnis scoreImmobilienFinanzierungsCluster(ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster, ScoringErgebnis scoringErgebnis) {
        ClusterScoringEvent immobilienFinanzierungsClusterErgebnis = immobilienFinanzierungsCluster.scoren();
        if(ClusterGescored.class.equals(immobilienFinanzierungsClusterErgebnis.getClass())) {
            scoringErgebnis.immobilienFinanzierungClusterHinzufuegen((ClusterGescored) immobilienFinanzierungsClusterErgebnis);
        }
        return scoringErgebnis;
    }
}
