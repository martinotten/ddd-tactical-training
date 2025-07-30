package com.bigpugloans.scoring.adapter.driven.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.application.ports.driven.AuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuskunfteiErgebnisClusterJpaRepository implements AuskunfteiErgebnisClusterRepository {
    private AuskunfteiErgebnisClusterSpringDataRepository dao;

    @Autowired
    public AuskunfteiErgebnisClusterJpaRepository(AuskunfteiErgebnisClusterSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(AuskunfteiErgebnisCluster auskunfteiErgebnisCluster) {
        dao.save(auskunfteiErgebnisCluster);
    }

    @Override
    public AuskunfteiErgebnisCluster lade(ScoringId scoringId) {
        return dao.findByScoringId(scoringId);
    }
}
