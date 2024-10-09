package com.bigpugloans.scoring.application.ports.driving;

import com.bigpugloans.scoring.application.model.Antrag;

public interface PreScoringStart {
    public void startePreScoring(Antrag antrag);
}
