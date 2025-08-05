package com.bigpugloans.scoring.testinfrastructure;

import com.bigpugloans.scoring.application.ports.driven.ScoringErgebnisVeroeffentlichen;
import com.bigpugloans.scoring.domain.model.AntragErfolgreichGescored;

import java.util.ArrayList;
import java.util.List;

public class TestScoringErgebnisVeroeffentlichen implements ScoringErgebnisVeroeffentlichen {
    private List<AntragErfolgreichGescored> veroeffentlichteErgebnisse = new ArrayList<>();
    
    @Override
    public void preScoringErgebnisVeroeffentlichen(AntragErfolgreichGescored gesamtScoringErgebnis) {
        veroeffentlichteErgebnisse.add(gesamtScoringErgebnis);
    }
    
    @Override
    public void mainScoringErgebnisVeroeffentlichen(AntragErfolgreichGescored gesamtScoringErgebnis) {
        veroeffentlichteErgebnisse.add(gesamtScoringErgebnis);
    }
    
    public List<AntragErfolgreichGescored> getVeroeffentlichteErgebnisse() {
        return new ArrayList<>(veroeffentlichteErgebnisse);
    }
    
    public void clear() {
        veroeffentlichteErgebnisse.clear();
    }
}