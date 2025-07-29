package com.bigpugloans.scoring.adapter.driven.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImmobilienFinanzierungsClusterSpringDataRepository extends CrudRepository<ImmobilienFinanzierungsClusterRecord, Long> {
    ImmobilienFinanzierungsClusterRecord findByScoringId(ScoringId scoringId);
}
