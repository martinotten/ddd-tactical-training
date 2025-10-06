package com.bigpugloans.scoring.adapter.driven.immobilienFinanzierungsCluster;

import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@InfrastructureRing
public interface ImmobilienFinanzierungsClusterSpringDataRepository extends MongoRepository<ImmobilienFinanzierungsClusterDocument, Long> {
    public ImmobilienFinanzierungsClusterDocument findByAntragsnummer(String antragsnummer);
}
