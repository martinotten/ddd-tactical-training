package com.bigpugloans.scoring.adapter.driven.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuskunfteiErgebnisClusterSpringDataRepository extends JpaRepository<AuskunfteiErgebnisCluster, Long> {
    AuskunfteiErgebnisCluster findByScoringId(ScoringId scoringId);
}
