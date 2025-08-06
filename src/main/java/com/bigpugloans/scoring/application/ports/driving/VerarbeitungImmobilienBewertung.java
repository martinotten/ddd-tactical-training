package com.bigpugloans.scoring.application.ports.driving;

import com.bigpugloans.scoring.domain.model.ImmobilienBewertung;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

@ApplicationServiceRing
@PrimaryPort
public interface VerarbeitungImmobilienBewertung {
    void verarbeiteImmobilienBewertung(ImmobilienBewertung immobilienBewertung);
}
