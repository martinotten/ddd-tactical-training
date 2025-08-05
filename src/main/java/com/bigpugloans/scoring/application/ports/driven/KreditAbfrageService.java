package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.events.antrag.Antrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;

public interface KreditAbfrageService {
    
    AuskunfteiErgebnis kreditAbfrage(Antrag antrag);
}