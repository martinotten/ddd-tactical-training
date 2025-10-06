package com.bigpugloans.scoring.adapter.driven.scoringErgebnis;

import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@InfrastructureRing
public interface ScoringErgebnisSpringDataRepository extends MongoRepository<ScoringErgebnisDocument, String> {
    public ScoringErgebnisDocument findByAntragsnummer(String antragsnummer);
}
