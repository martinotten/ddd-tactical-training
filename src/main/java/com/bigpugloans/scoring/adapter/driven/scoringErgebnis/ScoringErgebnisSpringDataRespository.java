package com.bigpugloans.scoring.adapter.driven.scoringErgebnis;

import com.bigpugloans.scoring.domain.model.ScoringId;
import org.springframework.data.repository.CrudRepository;

public interface ScoringErgebnisSpringDataRespository extends CrudRepository<ScoringErgebnisRecord, Long> {
    ScoringErgebnisRecord findByScoringId(ScoringId scoringId);
}
