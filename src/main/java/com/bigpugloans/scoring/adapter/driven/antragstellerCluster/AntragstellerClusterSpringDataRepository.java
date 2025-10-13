package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@InfrastructureRing
public interface AntragstellerClusterSpringDataRepository extends MongoRepository<AntragstellerClusterDocument, String> {
    AntragstellerClusterDocument findByScoringId(ScoringId scoringId);
}
