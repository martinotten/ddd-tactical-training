package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;

public interface MonatlicheFinanzsituationClusterRepository {
    void speichern(MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster);
    MonatlicheFinanzsituationCluster lade(ScoringId scoringId);
}
