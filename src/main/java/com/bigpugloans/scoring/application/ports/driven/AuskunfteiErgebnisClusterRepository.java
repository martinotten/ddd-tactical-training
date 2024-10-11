package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;

public interface AuskunfteiErgebnisClusterRepository {
    public void speichern(AuskunfteiErgebnisCluster auskunfteiErgebnisCluster);
    public AuskunfteiErgebnisCluster lade(Antragsnummer antragsnummer);
}
