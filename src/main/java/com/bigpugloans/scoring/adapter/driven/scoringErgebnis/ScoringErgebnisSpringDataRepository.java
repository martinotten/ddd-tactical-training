package com.bigpugloans.scoring.adapter.driven.scoringErgebnis;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoringErgebnisSpringDataRepository extends JpaRepository<ScoringErgebnis, Long> {
    ScoringErgebnis findByScoringId(ScoringId scoringId);
}
