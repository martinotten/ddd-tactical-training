package com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@InfrastructureRing
@org.jmolecules.ddd.annotation.Repository
@SecondaryAdapter
public class MonatlicheFinanzsituationClusterMongoDbRepository implements MonatlicheFinanzsituationClusterRepository {
    private final MonatlicheFinanzsituationClusterSpringDataRepository dao;

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
    public Optional<MonatlicheFinanzsituationCluster> lade(ScoringId scoringId) {
        if(scoringId == null) {
            throw new IllegalArgumentException("ScoringId darf nicht null sein");
        }
        MonatlicheFinanzsituationClusterDocument document = dao.findByScoringId(scoringId);
        if(document == null) {
            return Optional.of(new MonatlicheFinanzsituationCluster(scoringId));
        } else {
            return Optional.ofNullable(document.getMonatlicheFinanzsituationCluster());
        }
    }
}
