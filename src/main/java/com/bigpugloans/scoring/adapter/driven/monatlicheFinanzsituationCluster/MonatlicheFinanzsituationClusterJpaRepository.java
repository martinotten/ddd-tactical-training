package com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.application.ports.driven.MonatlicheFinanzsituationClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MonatlicheFinanzsituationClusterJpaRepository implements MonatlicheFinanzsituationClusterRepository {
    private final MonatlicheFinanzsituationClusterSpringDataRepository dao;

    @Autowired
    public MonatlicheFinanzsituationClusterJpaRepository(MonatlicheFinanzsituationClusterSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster) {
        dao.save(monatlicheFinanzsituationCluster);
    }

    @Override
    public MonatlicheFinanzsituationCluster lade(ScoringId scoringId) {
        return dao.findByScoringId(scoringId);
    }
}
