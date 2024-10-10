package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domainmodel.AntragErfolgreichGescored;

public interface ScoringErgebnisVeroeffentlichen {
    public void preScoringErgebnisVeroeffentlichen(AntragErfolgreichGescored gesamtScoringErgebnis);
}
