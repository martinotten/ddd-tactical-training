package com.bigpugloans.scoring.adapter.driven.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuskunfteiErgebnisClusterSpringDataRepository extends MongoRepository<AuskunfteiErgebnisClusterDocument, Long> {
    AuskunfteiErgebnisClusterDocument findByScoringId(ScoringId scoringId);
}
