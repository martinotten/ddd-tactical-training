package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.AntragErfolgreichGescored;

public interface ScoringErgebnisVeroeffentlichen {
    void preScoringErgebnisVeroeffentlichen(AntragErfolgreichGescored gesamtScoringErgebnis);

    void mainScoringErgebnisVeroeffentlichen(AntragErfolgreichGescored gesamtScoringErgebnis);
}
