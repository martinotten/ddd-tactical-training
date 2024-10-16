package com.bigpugloans.scoring.adapter.driven.scoringErgebnis;

import org.springframework.data.repository.CrudRepository;

public interface ScoringErgebnisSpringDataRespository extends CrudRepository<ScoringErgebnisRecord, Long> {
    ScoringErgebnisRecord findByAntragsnummer(String antragsnummer);
}
