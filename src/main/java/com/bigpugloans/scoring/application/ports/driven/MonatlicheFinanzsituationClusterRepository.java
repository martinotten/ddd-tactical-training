package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

@ApplicationServiceRing
@SecondaryPort
public interface MonatlicheFinanzsituationClusterRepository {
    public void speichern(MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster);
    public MonatlicheFinanzsituationCluster lade(Antragsnummer antragsnummer);
}
