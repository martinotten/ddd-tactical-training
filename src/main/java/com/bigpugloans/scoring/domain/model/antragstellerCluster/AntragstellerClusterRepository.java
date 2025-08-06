package com.bigpugloans.scoring.domain.model.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

import java.util.Optional;

@ApplicationServiceRing
@SecondaryPort
public interface AntragstellerClusterRepository {
    void speichern(AntragstellerCluster antragstellerCluster);
    Optional<AntragstellerCluster> lade(ScoringId scoringId);
}
