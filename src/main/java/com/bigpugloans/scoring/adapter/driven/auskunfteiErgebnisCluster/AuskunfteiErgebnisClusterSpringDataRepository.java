package com.bigpugloans.scoring.adapter.driven.auskunfteiErgebnisCluster;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuskunfteiErgebnisClusterSpringDataRepository extends CrudRepository<AuskunfteiErgebnisClusterRecord, Long> {
    AuskunfteiErgebnisClusterRecord findByAntragsnummer(String antragsnummer);
}
