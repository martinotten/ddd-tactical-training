package com.bigpugloans.scoring.adapter.driving.immobilienFinanzierungsCluster;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImmobilienFinanzierungsClusterSpringDataRepository extends MongoRepository<ImmobilienFinanzierungsClusterDocument, Long> {
    public ImmobilienFinanzierungsClusterDocument findByAntragsnummer(String antragsnummer);
}
