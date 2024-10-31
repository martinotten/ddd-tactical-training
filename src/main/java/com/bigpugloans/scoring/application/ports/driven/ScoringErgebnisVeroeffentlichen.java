package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.AntragErfolgreichGescored;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

@ApplicationServiceRing
@SecondaryPort
public interface ScoringErgebnisVeroeffentlichen {
    public void preScoringErgebnisVeroeffentlichen(AntragErfolgreichGescored gesamtScoringErgebnis);
}
