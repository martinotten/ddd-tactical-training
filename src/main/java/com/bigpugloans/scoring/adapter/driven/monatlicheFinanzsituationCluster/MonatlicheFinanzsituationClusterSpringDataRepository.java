package com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonatlicheFinanzsituationClusterSpringDataRepository extends CrudRepository<MonatlicheFinanzsituationClusterRecord, Long> {
    MonatlicheFinanzsituationClusterRecord findByAntragsnummer(String antragsnummer);
}
