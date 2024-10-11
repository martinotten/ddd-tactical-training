package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;

public interface MonatlicheFinanzsituationClusterRepository {
    public void speichern(MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster);
    public MonatlicheFinanzsituationCluster lade(Antragsnummer antragsnummer);
}
