package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domainmodel.Antragsnummer;
import com.bigpugloans.scoring.domainmodel.scoringErgebnis.ScoringErgebnis;

public interface ScoringErgebnisRepository {
    public void speichern(ScoringErgebnis scoringErgebnis);
    public ScoringErgebnis lade(Antragsnummer antragsnummer);
}
