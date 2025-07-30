package com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonatlicheFinanzsituationClusterSpringDataRepository extends JpaRepository<MonatlicheFinanzsituationCluster, Long> {
    MonatlicheFinanzsituationCluster findByScoringId(ScoringId scoringId);
}
