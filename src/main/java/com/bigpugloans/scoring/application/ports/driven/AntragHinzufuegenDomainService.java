package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.application.model.Antrag;
import com.bigpugloans.scoring.domainmodel.ScoringId;

public interface AntragHinzufuegenDomainService {
    
    void antragHinzufuegen(ScoringId scoringId, Antrag antrag);
}