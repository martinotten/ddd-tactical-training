package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domainmodel.Antragsnummer;
import com.bigpugloans.scoring.domainmodel.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;

public interface ImmobilienFinanzierungClusterRepository {
    public void speichern(ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster);
    public ImmobilienFinanzierungsCluster lade(Antragsnummer antragsnummer);
}
