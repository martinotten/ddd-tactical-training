package com.bigpugloans.scoring.testinfrastructure;

import com.bigpugloans.events.antrag.Antrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.KreditAbfrageService;

import java.util.HashMap;
import java.util.Map;

public class TestKreditAbfrageService implements KreditAbfrageService {
    
    private final Map<String, AuskunfteiErgebnis> responses = new HashMap<>();
    private AuskunfteiErgebnis defaultResponse = new AuskunfteiErgebnis(1, 0, 85);
    
    public void willReturn(Antrag antrag, AuskunfteiErgebnis result) {
        responses.put(antrag.antragsnummer(), result);
    }

    @Override
    public AuskunfteiErgebnis kreditAbfrage(Antrag antrag) {
        return responses.getOrDefault(antrag.antragsnummer(), defaultResponse);
    }

}