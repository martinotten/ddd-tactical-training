package com.bigpugloans.scoring.testinfrastructure;

import com.bigpugloans.scoring.application.ports.driven.LeseKontoSaldo;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;

import java.util.HashMap;
import java.util.Map;

public class TestLeseKontoSaldo implements LeseKontoSaldo {
    
    private final Map<String, Waehrungsbetrag> kontosaldi = new HashMap<>();
    private Waehrungsbetrag defaultSaldo = new Waehrungsbetrag(0);
    
    public void willReturn(String kundennummer, Waehrungsbetrag saldo) {
        kontosaldi.put(kundennummer, saldo);
    }
    
    public void willReturnDefault(Waehrungsbetrag saldo) {
        this.defaultSaldo = saldo;
    }
    
    @Override
    public Waehrungsbetrag leseKontoSaldo(String kundennummer) {
        return kontosaldi.getOrDefault(kundennummer, defaultSaldo);
    }
    
    public void clear() {
        kontosaldi.clear();
        defaultSaldo = new Waehrungsbetrag(0);
    }
}