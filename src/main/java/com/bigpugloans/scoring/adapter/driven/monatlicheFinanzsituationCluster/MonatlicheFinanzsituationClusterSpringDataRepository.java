package com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster;

import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@InfrastructureRing
public interface MonatlicheFinanzsituationClusterSpringDataRepository extends MongoRepository<MonatlicheFinanzsituationClusterDocument, Long> {
    public MonatlicheFinanzsituationClusterDocument findByAntragsnummer(String antragsnummer);
}
