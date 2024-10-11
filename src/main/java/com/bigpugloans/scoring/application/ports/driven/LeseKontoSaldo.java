package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;

public interface LeseKontoSaldo {
    public Waehrungsbetrag leseKontoSaldo(String kundennummer);
}
