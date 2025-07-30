package com.bigpugloans.scoring.adapter.driven.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImmobilienFinanzierungsClusterSpringDataRepository extends JpaRepository<ImmobilienFinanzierungsCluster, Long> {
    ImmobilienFinanzierungsCluster findByScoringId(ScoringId scoringId);
}
