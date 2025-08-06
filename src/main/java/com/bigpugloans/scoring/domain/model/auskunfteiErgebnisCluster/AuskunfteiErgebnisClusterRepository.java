package com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

import java.util.Optional;

@ApplicationServiceRing
@SecondaryPort
public interface AuskunfteiErgebnisClusterRepository {
    void speichern(AuskunfteiErgebnisCluster auskunfteiErgebnisCluster);
    Optional<AuskunfteiErgebnisCluster> lade(ScoringId scoringId);
}
