package com.bigpugloans.scoring.adapter.driven.auskunfteiErgebnisCluster;

import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@InfrastructureRing
public interface AuskunfteiErgebnisClusterSpringDataRepository extends MongoRepository<AuskunfteiErgebnisClusterDocument, Long> {
    public AuskunfteiErgebnisClusterDocument findByAntragsnummer(String antragsnummer);
}
