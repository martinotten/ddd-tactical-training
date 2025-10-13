package com.bigpugloans.scoring.testinfrastructure;

import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnisRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryScoringErgebnisRepository implements ScoringErgebnisRepository {
    
    private final Map<ScoringId, ScoringErgebnis> ergebnisse = new HashMap<>();
    
    @Override
    public Optional<ScoringErgebnis> lade(ScoringId scoringId) {
        return Optional.ofNullable(ergebnisse.get(scoringId));
    }
    
    @Override
    public void speichern(ScoringErgebnis ergebnis) {
        ergebnisse.put(ergebnis.scoringId(), ergebnis);
    }
    
    public void clear() {
        ergebnisse.clear();
    }
    
    public boolean contains(ScoringId scoringId) {
        return ergebnisse.containsKey(scoringId);
    }
    
    public int size() {
        return ergebnisse.size();
    }
}