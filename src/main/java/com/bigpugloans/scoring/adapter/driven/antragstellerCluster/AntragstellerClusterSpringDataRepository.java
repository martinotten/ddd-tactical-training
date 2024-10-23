package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AntragstellerClusterSpringDataRepository extends JpaRepository<AntragstellerCluster, Long> {
    AntragstellerCluster findByAntragsnummer(Antragsnummer antragsnummer);
}
