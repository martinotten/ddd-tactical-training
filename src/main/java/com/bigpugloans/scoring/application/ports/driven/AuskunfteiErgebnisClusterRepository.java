package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;

public interface AuskunfteiErgebnisClusterRepository {
    void speichern(AuskunfteiErgebnisCluster auskunfteiErgebnisCluster);
    AuskunfteiErgebnisCluster lade(ScoringId scoringId);
}
