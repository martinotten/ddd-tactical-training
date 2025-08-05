package com.bigpugloans.scoring.adapter.driven.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.application.ports.driven.AuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuskunfteiErgebnisClusterMongoDbRepository implements AuskunfteiErgebnisClusterRepository {
    private final AuskunfteiErgebnisClusterSpringDataRepository dao;

    @Autowired
    public AuskunfteiErgebnisClusterMongoDbRepository(AuskunfteiErgebnisClusterSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(AuskunfteiErgebnisCluster auskunfteiErgebnisCluster) {
        if(auskunfteiErgebnisCluster == null) {
            throw new IllegalArgumentException("AuskunfteiErgebnisCluster darf nicht null sein");
        }
        AuskunfteiErgebnisClusterDocument document = dao.findByScoringId(auskunfteiErgebnisCluster.scoringId());
        if(document == null) {
            document = new AuskunfteiErgebnisClusterDocument();
            document.setScoringId(auskunfteiErgebnisCluster.scoringId());
        }

        document.setAuskunfteiErgebnisCluster(auskunfteiErgebnisCluster);
        dao.save(document);

    }

    @Override
    public AuskunfteiErgebnisCluster lade(ScoringId scoringId) {
        if(scoringId == null) {
            throw new IllegalArgumentException("ScoringId darf nicht null sein");
        }
        AuskunfteiErgebnisClusterDocument clusterEntity = dao.findByScoringId(scoringId);
        if(clusterEntity == null) {
            return null;
        }
        return clusterEntity.getAuskunfteiErgebnisCluster();
    }
}
