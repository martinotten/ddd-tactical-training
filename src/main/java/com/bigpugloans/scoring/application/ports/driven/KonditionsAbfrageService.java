package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.Antrag;
import com.bigpugloans.scoring.domain.model.AuskunfteiErgebnis;

public interface KonditionsAbfrageService {
    
    AuskunfteiErgebnis konditionsAbfrage(Antrag antrag);
}