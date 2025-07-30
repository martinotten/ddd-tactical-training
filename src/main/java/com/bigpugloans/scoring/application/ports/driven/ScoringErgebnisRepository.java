package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;

public interface ScoringErgebnisRepository {
    void speichern(ScoringErgebnis scoringErgebnis);
    ScoringErgebnis lade(ScoringId scoringId);
}
