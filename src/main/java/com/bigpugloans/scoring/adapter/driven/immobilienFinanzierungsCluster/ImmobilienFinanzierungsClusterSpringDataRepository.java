package com.bigpugloans.scoring.adapter.driven.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImmobilienFinanzierungsClusterSpringDataRepository extends MongoRepository<ImmobilienFinanzierungsClusterDocument, Long> {
    ImmobilienFinanzierungsClusterDocument findByScoringId(ScoringId scoringId);
}
