package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import com.bigpugloans.scoring.application.ports.driven.AntragstellerClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
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
    public AntragstellerCluster lade(ScoringId scoringId) {
        AntragstellerCluster cluster = dao.findByScoringId(scoringId);
        return cluster;
    }
}
