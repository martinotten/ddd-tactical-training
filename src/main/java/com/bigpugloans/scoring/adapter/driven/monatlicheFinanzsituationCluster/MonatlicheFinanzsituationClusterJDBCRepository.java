package com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.application.ports.driven.MonatlicheFinanzsituationClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class MonatlicheFinanzsituationClusterJDBCRepository implements MonatlicheFinanzsituationClusterRepository {
    private MonatlicheFinanzsituationClusterSpringDataRepository dao;

    @Autowired
    public MonatlicheFinanzsituationClusterJDBCRepository(MonatlicheFinanzsituationClusterSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster) {
        MonatlicheFinanzsituationClusterRecord record = dao.findByScoringId(monatlicheFinanzsituationCluster.scoringId());
        if(record == null) {
            record = new MonatlicheFinanzsituationClusterRecord();
            record.setScoringId(monatlicheFinanzsituationCluster.scoringId());
        }
        record.setMemento(monatlicheFinanzsituationCluster.memento());
        dao.save(record);
    }

    @Override
    public MonatlicheFinanzsituationCluster lade(ScoringId scoringId) {
        if(scoringId == null) {
            throw new IllegalArgumentException("ScoringId darf nicht null sein");
        }
        MonatlicheFinanzsituationClusterRecord record = dao.findByScoringId(scoringId);
        if(record == null) {
            return null;
        }
        return MonatlicheFinanzsituationCluster.fromMemento(record.getMemento());
    }
}
