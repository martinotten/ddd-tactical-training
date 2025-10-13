package com.bigpugloans.scoring.domain.model.scoringErgebnis;

import com.bigpugloans.scoring.domain.model.ScoringId;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

import java.util.Optional;

@ApplicationServiceRing
@SecondaryPort
public interface ScoringErgebnisRepository {
    void speichern(ScoringErgebnis scoringErgebnis);
    Optional<ScoringErgebnis> lade(ScoringId scoringId);
}
