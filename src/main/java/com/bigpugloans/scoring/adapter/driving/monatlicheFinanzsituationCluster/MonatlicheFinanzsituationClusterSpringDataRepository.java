package com.bigpugloans.scoring.adapter.driving.monatlicheFinanzsituationCluster;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonatlicheFinanzsituationClusterSpringDataRepository extends MongoRepository<MonatlicheFinanzsituationClusterDocument, Long> {
    public MonatlicheFinanzsituationClusterDocument findByAntragsnummer(String antragsnummer);
}
