package com.bigpugloans.scoring.adapter.driving.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.application.ports.driven.AuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuskunfteiErgebnisClusterJPARepository implements AuskunfteiErgebnisClusterRepository {
    private AuskunfteiErgebnisClusterSpringDataRepository dao;

    @Autowired
    public AuskunfteiErgebnisClusterJPARepository(AuskunfteiErgebnisClusterSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(AuskunfteiErgebnisCluster auskunfteiErgebnisCluster) {

    }

    @Override
    public AuskunfteiErgebnisCluster lade(Antragsnummer antragsnummer) {
        if(antragsnummer == null) {
            throw new IllegalArgumentException("Antragsnummer darf nicht null sein");
        }
        AuskunfteiErgebnisClusterEntity clusterEntity = dao.findByAntragsnummer(antragsnummer.nummer());
        return clusterEntity.getAuskunfteiErgebnisCluster();
    }
}
