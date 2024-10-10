package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domainmodel.AntragErfolgreichGescored;
import com.bigpugloans.scoring.domainmodel.Antragsnummer;
import com.bigpugloans.scoring.domainmodel.ScoringFarbe;

public interface ScoringErgebnisVeroeffentlichen {
    public void preScoringErgebnisVeroeffentlichen(AntragErfolgreichGescored gesamtScoringErgebnis);
}
