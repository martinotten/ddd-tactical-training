package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

@ApplicationServiceRing
@SecondaryPort
public interface LeseKontoSaldo {
    public Waehrungsbetrag leseKontoSaldo(String kundennummer);
}
