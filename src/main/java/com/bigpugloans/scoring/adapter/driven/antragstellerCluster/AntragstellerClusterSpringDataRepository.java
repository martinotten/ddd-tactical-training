package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AntragstellerClusterSpringDataRepository extends MongoRepository<AntragstellerClusterDocument, String> {
    AntragstellerClusterDocument findByAntragsnummer(String antragsnummer);
}
