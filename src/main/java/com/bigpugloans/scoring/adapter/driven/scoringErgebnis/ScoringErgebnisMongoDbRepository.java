package com.bigpugloans.scoring.adapter.driven.scoringErgebnis;

import com.bigpugloans.scoring.application.ports.driven.ScoringErgebnisRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ScoringErgebnisMongoDbRepository implements ScoringErgebnisRepository {
    private final ScoringErgebnisSpringDataRepository dao;

    @Autowired
    public ScoringErgebnisMongoDbRepository(ScoringErgebnisSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(ScoringErgebnis scoringErgebnis) {
        if(scoringErgebnis == null) {
            throw new IllegalArgumentException("ScoringErgebnis darf nicht null sein");
        }
        ScoringErgebnisDocument document = dao.findByScoringId(scoringErgebnis.scoringId());
        if(document == null) {
            document = new ScoringErgebnisDocument();
            document.setScoringId(scoringErgebnis.scoringId());
        }
        document.setScoringErgebnis(scoringErgebnis);
        dao.save(document);
    }

    @Override
    public ScoringErgebnis lade(ScoringId scoringId) {
        if(scoringId == null) {
            throw new IllegalArgumentException("ScoringId darf nicht null sein");
        }
        ScoringErgebnisDocument document = dao.findByScoringId(scoringId);
        if(document == null) {
            return new ScoringErgebnis(scoringId);
        } else {
            return document.getScoringErgebnis();
        }
    }
}
