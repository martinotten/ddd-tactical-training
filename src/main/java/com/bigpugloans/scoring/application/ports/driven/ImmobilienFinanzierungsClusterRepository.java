package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;

public interface ImmobilienFinanzierungClusterRepository {
    void speichern(ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster);
    ImmobilienFinanzierungsCluster lade(ScoringId scoringId);
}
