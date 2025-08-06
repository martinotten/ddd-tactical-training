package com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

import java.util.Optional;

@ApplicationServiceRing
@SecondaryPort
public interface ImmobilienFinanzierungClusterRepository {
    void speichern(ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster);
    Optional<ImmobilienFinanzierungsCluster> lade(ScoringId scoringId);
}
