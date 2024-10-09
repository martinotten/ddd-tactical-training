package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domainmodel.Antragsnummer;
import com.bigpugloans.scoring.domainmodel.antragstellerCluster.AntragstellerCluster;

public interface AntragstellerClusterRepository {
    public void speichern(AntragstellerCluster antragstellerCluster);
    public AntragstellerCluster lade(Antragsnummer antragsnummer);
}
