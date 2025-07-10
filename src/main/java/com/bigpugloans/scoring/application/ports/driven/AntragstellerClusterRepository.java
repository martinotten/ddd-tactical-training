package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;

public interface AntragstellerClusterRepository {
    void speichern(AntragstellerCluster antragstellerCluster);
    AntragstellerCluster lade(ScoringId scoringId);
}
