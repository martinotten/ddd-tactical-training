package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.AntragErfolgreichGescored;

public interface ScoringErgebnisVeroeffentlichen {
    public void preScoringErgebnisVeroeffentlichen(AntragErfolgreichGescored gesamtScoringErgebnis);
}
