package com.bigpugloans.scoring.application.ports.driving;

import com.bigpugloans.scoring.application.model.ScoringDatenAusAntrag;

public interface PreScoringStart {
    void startePreScoring(ScoringDatenAusAntrag antrag);
}
