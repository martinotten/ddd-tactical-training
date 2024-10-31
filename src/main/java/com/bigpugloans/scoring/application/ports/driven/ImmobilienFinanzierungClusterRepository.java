package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

@ApplicationServiceRing
@SecondaryPort
public interface ImmobilienFinanzierungClusterRepository {
    public void speichern(ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster);
    public ImmobilienFinanzierungsCluster lade(Antragsnummer antragsnummer);
}
