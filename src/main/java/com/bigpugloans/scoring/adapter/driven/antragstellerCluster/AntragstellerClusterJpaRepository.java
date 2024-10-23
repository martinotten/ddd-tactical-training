package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import com.bigpugloans.scoring.application.ports.driven.AntragstellerClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AntragstellerClusterJpaRepository implements AntragstellerClusterRepository {
    private AntragstellerClusterSpringDataRepository dao;

    @Autowired
    public AntragstellerClusterJpaRepository(AntragstellerClusterSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(AntragstellerCluster antragstellerCluster) {
        dao.save(antragstellerCluster);
    }

    @Override
    public AntragstellerCluster lade(Antragsnummer antragsnummer) {
        AntragstellerCluster cluster = dao.findByAntragsnummer(antragsnummer);
        return cluster;
    }
}
