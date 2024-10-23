package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;

public interface ImmobilienFinanzierungsClusterRepository {
    public void speichern(ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster);
    public ImmobilienFinanzierungsCluster lade(Antragsnummer antragsnummer);
}
