package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.domainmodel.ScoringId;

public interface AuskunfteiHinzufuegenDomainService {
    
    void auskunfteiErgebnisHinzufuegen(ScoringId scoringId, AuskunfteiErgebnis auskunfteiErgebnis);
}