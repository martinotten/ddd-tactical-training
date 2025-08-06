package com.bigpugloans.scoring.adapter.driven.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@InfrastructureRing
public interface ImmobilienFinanzierungsClusterSpringDataRepository extends MongoRepository<ImmobilienFinanzierungsClusterDocument, Long> {
    ImmobilienFinanzierungsClusterDocument findByScoringId(ScoringId scoringId);
}
