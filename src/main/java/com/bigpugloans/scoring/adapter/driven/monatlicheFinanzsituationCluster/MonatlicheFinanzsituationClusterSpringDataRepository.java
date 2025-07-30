package com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import org.springframework.data.repository.CrudRepository;
public interface MonatlicheFinanzsituationClusterSpringDataRepository extends CrudRepository<MonatlicheFinanzsituationClusterRecord, Long> {
    MonatlicheFinanzsituationClusterRecord findByScoringId(ScoringId scoringId);
}
