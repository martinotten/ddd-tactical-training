package com.bigpugloans.scoring.adapter.driven.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import org.springframework.data.repository.CrudRepository;
public interface ImmobilienFinanzierungsClusterSpringDataRepository extends CrudRepository<ImmobilienFinanzierungsClusterRecord, Long> {
    ImmobilienFinanzierungsClusterRecord findByScoringId(ScoringId scoringId);
}
