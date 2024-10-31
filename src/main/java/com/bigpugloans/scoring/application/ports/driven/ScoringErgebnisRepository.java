package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

@ApplicationServiceRing
@SecondaryPort
public interface ScoringErgebnisRepository {
    public void speichern(ScoringErgebnis scoringErgebnis);
    public ScoringErgebnis lade(Antragsnummer antragsnummer);
}
