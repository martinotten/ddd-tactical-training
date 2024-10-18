package com.bigpugloans.scoring.adapter.driving.auskunfteiErgebnisCluster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuskunfteiErgebnisClusterSpringDataRepository extends JpaRepository<AuskunfteiErgebnisClusterEntity, Long> {
    public AuskunfteiErgebnisClusterEntity findByAntragsnummer(String antragsnummer);
}
