package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domainmodel.Antragsnummer;
import com.bigpugloans.scoring.domainmodel.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;

public interface AuskunfteiErgebnisClusterRepository {
    public void speichern(AuskunfteiErgebnisCluster auskunfteiErgebnisCluster);
    public AuskunfteiErgebnisCluster lade(Antragsnummer antragsnummer);
}
