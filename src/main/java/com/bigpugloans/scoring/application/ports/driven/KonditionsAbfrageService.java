package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.events.antrag.Antrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;

public interface KonditionsAbfrageService {
    
    AuskunfteiErgebnis konditionsAbfrage(Antrag antrag);
}