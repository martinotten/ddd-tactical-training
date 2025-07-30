package com.bigpugloans.scoring.adapter.driven.scoringErgebnis;

import com.bigpugloans.scoring.application.ports.driven.ScoringErgebnisRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ScoringErgebnisJpaRepository implements ScoringErgebnisRepository {
    private final ScoringErgebnisSpringDataRepository dao;

    @Autowired
    public ScoringErgebnisJpaRepository(ScoringErgebnisSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(ScoringErgebnis scoringErgebnis) {
        dao.save(scoringErgebnis);
    }

    @Override
    public ScoringErgebnis lade(ScoringId scoringId) {
        return dao.findByScoringId(scoringId);
    }
}
