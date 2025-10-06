package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.ClusterGescored;
import com.bigpugloans.scoring.domain.model.ClusterScoringEvent;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import org.jmolecules.architecture.onion.classical.DomainServiceRing;

@DomainServiceRing
public class ScoreMonatlicheFinanzsituationClusterDomainService {
    public ScoringErgebnis scoreMonatlicheFinanzsituationCluster(MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster, ScoringErgebnis scoringErgebnis) {
        ClusterScoringEvent monatlicheFinanzsituationErgebnis = monatlicheFinanzsituationCluster.scoren();
        if(ClusterGescored.class.equals(monatlicheFinanzsituationErgebnis.getClass())) {
            scoringErgebnis.monatlicheFinansituationClusterHinzufuegen((ClusterGescored) monatlicheFinanzsituationErgebnis);
        }
        return scoringErgebnis;
    }
}
