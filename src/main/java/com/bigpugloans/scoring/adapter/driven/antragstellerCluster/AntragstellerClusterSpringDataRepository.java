package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AntragstellerClusterSpringDataRepository extends CrudRepository<AntragstellerClusterRecord, Long> {
    AntragstellerClusterRecord findByAntragsnummer(String antragsnummer);
}
