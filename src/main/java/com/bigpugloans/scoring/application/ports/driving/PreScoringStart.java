package com.bigpugloans.scoring.application.ports.driving;

import com.bigpugloans.scoring.application.model.ScoringDatenAusAntrag;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

@ApplicationServiceRing
@PrimaryPort
public interface PreScoringStart {
    void startePreScoring(ScoringDatenAusAntrag antrag);
}
