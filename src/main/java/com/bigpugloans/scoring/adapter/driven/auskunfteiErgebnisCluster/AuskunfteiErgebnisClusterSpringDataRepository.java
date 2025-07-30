package com.bigpugloans.scoring.adapter.driven.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import org.springframework.data.repository.CrudRepository;
public interface AuskunfteiErgebnisClusterSpringDataRepository extends CrudRepository<AuskunfteiErgebnisClusterRecord, Long> {
    AuskunfteiErgebnisClusterRecord findByScoringId(ScoringId scoringId);
}
