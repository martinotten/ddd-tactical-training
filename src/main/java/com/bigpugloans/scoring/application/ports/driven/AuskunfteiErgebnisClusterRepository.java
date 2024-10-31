package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

@ApplicationServiceRing
@SecondaryPort
public interface AuskunfteiErgebnisClusterRepository {
    public void speichern(AuskunfteiErgebnisCluster auskunfteiErgebnisCluster);
    public AuskunfteiErgebnisCluster lade(Antragsnummer antragsnummer);
}
