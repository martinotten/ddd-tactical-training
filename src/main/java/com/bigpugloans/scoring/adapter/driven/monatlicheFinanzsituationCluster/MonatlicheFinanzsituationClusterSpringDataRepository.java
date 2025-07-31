package com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonatlicheFinanzsituationClusterSpringDataRepository extends MongoRepository<MonatlicheFinanzsituationClusterDocument, Long> {
    public MonatlicheFinanzsituationClusterDocument findByScoringId(ScoringId scoringId);
}
