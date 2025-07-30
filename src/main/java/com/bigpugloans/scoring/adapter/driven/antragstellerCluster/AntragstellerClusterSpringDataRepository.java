package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import org.springframework.data.repository.CrudRepository;
public interface AntragstellerClusterSpringDataRepository extends CrudRepository<AntragstellerClusterRecord, Long> {
    AntragstellerClusterRecord findByScoringId(ScoringId scoringId);
}
