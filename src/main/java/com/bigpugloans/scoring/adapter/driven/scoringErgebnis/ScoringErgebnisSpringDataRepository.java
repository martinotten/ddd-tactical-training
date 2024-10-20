package com.bigpugloans.scoring.adapter.driven.scoringErgebnis;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoringErgebnisSpringDataRepository extends MongoRepository<ScoringErgebnisDocument, String> {
    public ScoringErgebnisDocument findByAntragsnummer(String antragsnummer);
}
