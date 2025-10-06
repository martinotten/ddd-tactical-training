package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

@ApplicationServiceRing
@SecondaryPort
public interface AntragstellerClusterRepository {
    public void speichern(AntragstellerCluster antragstellerCluster);
    public AntragstellerCluster lade(Antragsnummer antragsnummer);
}
