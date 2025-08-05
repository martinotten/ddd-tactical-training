package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AntragstellerClusterSpringDataRepository extends MongoRepository<AntragstellerClusterDocument, String> {
    AntragstellerClusterDocument findByScoringId(ScoringId scoringId);
}
