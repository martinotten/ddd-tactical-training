package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;

public interface AntragstellerClusterRepository {
    public void speichern(AntragstellerCluster antragstellerCluster);
    public AntragstellerCluster lade(Antragsnummer antragsnummer);
}
