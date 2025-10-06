package com.bigpugloans.scoring.application.ports.driving;

import com.bigpugloans.scoring.application.model.ImmobilienBewertung;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

@ApplicationServiceRing
@PrimaryPort
public interface VerarbeitungImmobilienBewertung {
    public void verarbeiteImmobilienBewertung(ImmobilienBewertung immobilienBewertung);
}
