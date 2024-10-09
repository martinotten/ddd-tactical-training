package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domainmodel.Antragsnummer;
import com.bigpugloans.scoring.domainmodel.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;

public interface MonatlicheFinanzsituationClusterRepository {
    public void speichern(MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster);
    public MonatlicheFinanzsituationCluster lade(Antragsnummer antragsnummer);
}
