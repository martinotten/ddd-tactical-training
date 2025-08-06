package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.Antrag;
import com.bigpugloans.scoring.domain.model.AuskunfteiErgebnis;

public interface KreditAbfrageService {
    
    AuskunfteiErgebnis kreditAbfrage(Antrag antrag);
}