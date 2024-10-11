package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;

public interface ScoringErgebnisRepository {
    public void speichern(ScoringErgebnis scoringErgebnis);
    public ScoringErgebnis lade(Antragsnummer antragsnummer);
}
