package com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.application.ports.driven.MonatlicheFinanzsituationClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MonatlicheFinanzsituationClusterMongoDbRepository implements MonatlicheFinanzsituationClusterRepository {
    private MonatlicheFinanzsituationClusterSpringDataRepository dao;

    @Autowired
    public MonatlicheFinanzsituationClusterMongoDbRepository(MonatlicheFinanzsituationClusterSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster) {
        if(monatlicheFinanzsituationCluster == null) {
            throw new IllegalArgumentException("MonatlicheFinanzsituationCluster darf nicht null sein");
        }
        MonatlicheFinanzsituationClusterDocument document = dao.findByScoringId(monatlicheFinanzsituationCluster.scoringId());
        if(document == null) {
            document = new MonatlicheFinanzsituationClusterDocument();
            document.setScoringId(monatlicheFinanzsituationCluster.scoringId());
        }
        document.setMonatlicheFinanzsituationCluster(monatlicheFinanzsituationCluster);
        dao.save(document);
    }

    @Override
    public MonatlicheFinanzsituationCluster lade(ScoringId scoringId) {
        if(scoringId == null) {
            throw new IllegalArgumentException("ScoringId darf nicht null sein");
        }
        MonatlicheFinanzsituationClusterDocument document = dao.findByScoringId(scoringId);
        if(document == null) {
            return new MonatlicheFinanzsituationCluster(scoringId);
        } else {
            return document.getMonatlicheFinanzsituationCluster();
        }
    }
}
