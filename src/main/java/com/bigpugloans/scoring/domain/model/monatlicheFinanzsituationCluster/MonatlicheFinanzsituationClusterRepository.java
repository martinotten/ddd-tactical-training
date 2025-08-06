package com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

import java.util.Optional;

@ApplicationServiceRing
@SecondaryPort
public interface MonatlicheFinanzsituationClusterRepository {
    void speichern(MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster);
    Optional<MonatlicheFinanzsituationCluster> lade(ScoringId scoringId);
}
