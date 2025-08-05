package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import com.bigpugloans.scoring.application.ports.driven.AntragstellerClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AntragstellerClusterMongoDbRepository implements AntragstellerClusterRepository {
    private final AntragstellerClusterSpringDataRepository dao;

    @Autowired
    public AntragstellerClusterMongoDbRepository(AntragstellerClusterSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(AntragstellerCluster antragstellerCluster) {
        if(antragstellerCluster == null) {
            throw new IllegalArgumentException("AntragstellerCluster darf nicht null sein");
        }
        AntragstellerClusterDocument document = dao.findByScoringId(antragstellerCluster.scoringId());
        if(document == null) {
            document = new AntragstellerClusterDocument();
            document.setScoringId(antragstellerCluster.scoringId());
        }
        document.setAntragstellerCluster(antragstellerCluster);
        dao.save(document);
    }

    @Override
    public AntragstellerCluster lade(ScoringId scoringId) {
        if(scoringId == null) {
            throw new IllegalArgumentException("ScoringId darf nicht null sein");
        }
        AntragstellerClusterDocument document = dao.findByScoringId(scoringId);
        if(document == null) {
            return new AntragstellerCluster(scoringId);
        } else {
            return document.getAntragstellerCluster();
        }
    }
}
