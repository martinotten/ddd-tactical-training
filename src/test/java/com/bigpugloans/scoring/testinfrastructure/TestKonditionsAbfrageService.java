package com.bigpugloans.scoring.testinfrastructure;

import com.bigpugloans.events.antrag.Antrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.KonditionsAbfrageService;

import java.util.HashMap;
import java.util.Map;

public class TestKonditionsAbfrageService implements KonditionsAbfrageService {
    
    private final Map<String, AuskunfteiErgebnis> responses = new HashMap<>();
    private final AuskunfteiErgebnis defaultResponse = new AuskunfteiErgebnis(0, 0, 50);
    
    public void willReturn(Antrag antrag, AuskunfteiErgebnis result) {
        responses.put(antrag.antragsnummer(), result);
    }

    @Override
    public AuskunfteiErgebnis konditionsAbfrage(Antrag antrag) {
        return responses.getOrDefault(antrag.antragsnummer(), defaultResponse);
    }

}